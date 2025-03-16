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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.jooq.JooqDataTables;
import com.github.PierreAdam.javadatatables.testdata.PersonEntity;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * PersonDataTable.
 *
 * @author Pierre Adam
 * @since 25.03.11
 */
public class JooqPersonDataTable extends JooqDataTables<PersonEntity> {

    /**
     * Instantiates a new Jooq data tables.
     *
     * @param objectMapper the object mapper
     * @param dslContext   the dsl context
     */
    public JooqPersonDataTable(final ObjectMapper objectMapper, final DSLContext dslContext) {
        super(PersonEntity.class, objectMapper, dslContext);

        this.setInitProviderConsumer(jooqProvider -> jooqProvider
                        .setInitialSelect(context -> context.select(
                                        DSL.field("createdAt", OffsetDateTime.class),
                                        DSL.field("uid", UUID.class),
                                        DSL.field("firstName", String.class),
                                        DSL.field("lastName", String.class),
                                        DSL.field("title", String.class),
                                        DSL.field("bloodGroup", String.class),
                                        DSL.field("active", Boolean.class)
                                )
                        )
                        .setInitialFrom(query -> query.from(DSL.table("person")))
                )
                .setGlobalSearchHandler((jooqProvider, s) ->
                        jooqProvider.addCondition(
                                DSL.or(
                                        DSL.field("firstName").containsIgnoreCase(s),
                                        DSL.field("lastName").containsIgnoreCase(s),
                                        DSL.field("title").containsIgnoreCase(s),
                                        DSL.field("bloodGroup").containsIgnoreCase(s),
                                        // Search on the "full name"
                                        DSL.concat(DSL.field("firstName"), DSL.inline(" "), DSL.field("lastName")).containsIgnoreCase(s)
                                )
                        )
                )
                .setRowExtraData(extraData -> {
                        }//extraData
//                                .setRowId(personEntity -> personEntity.getUid().toString())
//                        .setRowData(personEntity -> new HashMap<String, String>() {{
//                            this.put("uid", personEntity.getUid().toString());
//                            this.put("fullName", String.format("%s %s", personEntity.getFirstName(), personEntity.getLastName()));
//                        }})
                )
                .field("fullName", field -> field
                        .setDisplaySupplier((entity, context) ->
                                String.format("%s %s", entity.getFirstName(), entity.getLastName()))
                        .setSearchHandler((jooqProvider, s) ->
                                jooqProvider.addCondition(
                                        DSL.concat(DSL.field("firstName"), DSL.inline(" "), DSL.field("lastName")).containsIgnoreCase(s)
                                ))
                        .setOrderHandler((jooqProvider, orderEnum) -> {
                            jooqProvider.addSort(DSL.field("firstName"), orderEnum);
                            jooqProvider.addSort(DSL.field("lastName"), orderEnum);
                        })
                );
    }
}
