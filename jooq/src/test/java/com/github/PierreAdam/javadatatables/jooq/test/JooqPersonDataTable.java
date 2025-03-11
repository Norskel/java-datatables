package com.github.PierreAdam.javadatatables.jooq.test;

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
     * @param entityClass  the entity class
     * @param objectMapper the object mapper
     * @param dslContext   the dsl context
     */
    public JooqPersonDataTable(final Class<PersonEntity> entityClass, final ObjectMapper objectMapper, final DSLContext dslContext) {
        super(entityClass, objectMapper, dslContext);

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
