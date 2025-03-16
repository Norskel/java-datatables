/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025 Pierre Adam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.PierreAdam.javadatatables.jooq.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import com.github.PierreAdam.javadatatables.core.entities.Search;
import com.github.PierreAdam.javadatatables.testdata.PersonEntity;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JooqTest.
 *
 * @author Pierre
 * @since 25.03.11
 */
@Slf4j
public class JooqTest {

    /**
     * The Data.
     */
    private List<PersonEntity> data;

    /**
     * The Person data table.
     */
    private JooqPersonDataTable personDataTable;

    /**
     * The Connection.
     */
    private Connection connection;

    /**
     * The Dsl context.
     */
    private DSLContext dslContext;

    /**
     * Sets up.
     *
     * @throws SQLException the sql exception
     */
    @BeforeEach
    void setUp() throws SQLException {
        System.setProperty("org.jooq.no-logo", "true");

        this.connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        this.dslContext = DSL.using(this.connection, SQLDialect.H2);

        try (final Statement statement = this.connection.createStatement()) {
            // For JooQ DSLContext initialization

            // Execute table creation statement
            statement.executeUpdate("CREATE TABLE person (" +
                    "    createdAt TIMESTAMP," +
                    "    uid UUID PRIMARY KEY," +
                    "    firstName VARCHAR(100)," +
                    "    lastName VARCHAR(100)," +
                    "    title VARCHAR(50)," +
                    "    bloodGroup VARCHAR(10)," +
                    "    active BOOLEAN" +
                    ");"
            );

            JooqTest.logger.info("Table created successfully.");
        } catch (final Exception e) {
            JooqTest.logger.error("Error while creating the table: ", e);
        }

        this.data = new ArrayList<>();
        final Faker faker = new Faker();

        for (int i = 0; i < 100; i++) {
            final PersonEntity personEntity = new PersonEntity(faker);
            this.data.add(personEntity);
            this.dslContext.insertInto(DSL.table("person"),
                            DSL.field("createdAt"),
                            DSL.field("uid"),
                            DSL.field("firstName"),
                            DSL.field("lastName"),
                            DSL.field("title"),
                            DSL.field("bloodGroup"),
                            DSL.field("active")
                    )
                    .values(
                            personEntity.getCreatedAt(),
                            personEntity.getUid(),
                            personEntity.getFirstName(),
                            personEntity.getLastName(),
                            personEntity.getTitle(),
                            personEntity.getBloodGroup(),
                            personEntity.getActive()
                    ).execute();
        }

        this.personDataTable = new JooqPersonDataTable(PersonEntity.class, new ObjectMapper(), this.dslContext);

        JooqTest.logger.info("Data inserted successfully.");
    }

    /**
     * Tear down.
     *
     * @throws SQLException the sql exception
     */
    @AfterEach
    void tearDown() throws SQLException {
        this.connection.close();
        this.connection = null;
        this.dslContext = null;
    }

    /**
     * Query data.
     */
    @Test
    void queryData() {
        final List<PersonEntity> result = this.dslContext.select(
                        DSL.field("createdAt", OffsetDateTime.class),
                        DSL.field("uid", UUID.class),
                        DSL.field("firstName", String.class),
                        DSL.field("lastName", String.class),
                        DSL.field("title", String.class),
                        DSL.field("bloodGroup", String.class),
                        DSL.field("active", Boolean.class))
                .from(DSL.table("person"))
                .fetchInto(PersonEntity.class);

        Assertions.assertEquals(100, result.size(), "Expected 100 rows in the result set.");
    }

    @Test
    void datatableSimpleQuery() {
        final JsonNode ajaxResult = Assertions.assertDoesNotThrow(() -> this.personDataTable.getAjaxResult(ParametersHelper.createForNameEntity()));

        JooqTest.logger.info("ajax result : {}", ajaxResult);

        Assertions.assertEquals(100, ajaxResult.get("recordsTotal").asInt(), "Expected 100 rows total.");
        Assertions.assertEquals(100, ajaxResult.get("recordsFiltered").asInt(), "Expected 100 rows total filtered.");
        Assertions.assertEquals(10, ajaxResult.get("data").size(), "Expected 10 rows in the result set.");

        ajaxResult.get("data").forEach(node -> {
            Assertions.assertNotNull(node, "Expected non null node in the result set.");
            Assertions.assertEquals(8, node.size(), "Expected 8 values in the node.");
            node.forEach(value -> Assertions.assertNotNull(value, "Expected non null value in the node."));
        });
    }

    @Test
    void datatableSearchQuery() {
        final PersonEntity personEntity = this.data.get(0);
        final Parameters parameters = ParametersHelper.createForNameEntity()
                .setSearch(new Search().setValue(String.format("%s %s", personEntity.getFirstName(), personEntity.getLastName())));

        final JsonNode ajaxResult = Assertions.assertDoesNotThrow(() -> this.personDataTable.getAjaxResult(parameters));

        JooqTest.logger.info("ajax result : {}", ajaxResult);

        Assertions.assertEquals(100, ajaxResult.get("recordsTotal").asInt(), "Expected 100 rows total.");
        Assertions.assertTrue(ajaxResult.get("recordsFiltered").asInt() >= 1, "Expected at least 1 rows total filtered.");
        Assertions.assertFalse(ajaxResult.get("data").isEmpty(), "Expected at least 1 rows in the result set.");

        ajaxResult.get("data").forEach(node -> {
            Assertions.assertNotNull(node, "Expected non null node in the result set.");
            Assertions.assertEquals(8, node.size(), "Expected 8 values in the node.");
            node.forEach(value -> Assertions.assertNotNull(value, "Expected non null value in the node."));
        });
    }
}
