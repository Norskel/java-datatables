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

package com.github.PierreAdam.javadatatables.sample;

import com.github.PierreAdam.javadatatables.testdata.PersonEntity;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * com.github.PierreAdam.javadatatables.sample.JooqContext.
 *
 * @author Pierre Adam
 * @since 25.03.14
 */
@Slf4j
@Getter
public class JooqContext {

    /**
     * The Connection.
     */
    private final Connection connection;

    /**
     * The Dsl context.
     */
    private final DSLContext dslContext;

    /**
     * Instantiates a new Jooq context.
     *
     * @throws SQLException the sql exception
     */
    public JooqContext() throws SQLException {
        System.setProperty("org.jooq.no-logo", "true");

        this.connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        this.dslContext = DSL.using(this.connection, SQLDialect.H2);

        this.initializeTable();
        this.createFakeData(1000);
    }

    /**
     * Initializes the database table for storing person data.
     * <p>
     * This method establishes a connection to the database and executes a SQL statement to create
     * a table named "person" with the following columns:
     * - createdAt: A timestamp indicating when the record was created.
     * - uid: A unique identifier (UUID) serving as the primary key.
     * - firstName: A string (max 100 characters) representing the first name of the person.
     * - lastName: A string (max 100 characters) representing the last name of the person.
     * - title: A string (max 50 characters) representing the person's title.
     * - bloodGroup: A string (max 10 characters) representing the person's blood group.
     * - active: A boolean indicating whether this person record is active or not.
     * <p>
     * If the table creation is successful, a success message is logged. If an error occurs
     * during the table creation process, an error message along with the exception is logged.
     * <p>
     * This method uses resources enclosed in a try-with-resources block to ensure that the
     * Statement object is properly closed after the operation.
     */
    private void initializeTable() {
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

            JooqContext.logger.info("Table created successfully.");
        } catch (final Exception e) {
            JooqContext.logger.error("Error while creating the table: ", e);
        }
    }

    /**
     * Generates and inserts fake data records into the "person" table.
     *
     * @param count the number of fake data records to be created and inserted
     */
    private void createFakeData(final int count) {
        final Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            final PersonEntity personEntity = new PersonEntity(faker);
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

        JooqContext.logger.info("{} fake data created.", count);
    }
}
