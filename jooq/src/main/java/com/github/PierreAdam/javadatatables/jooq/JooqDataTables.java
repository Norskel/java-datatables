package com.github.PierreAdam.javadatatables.jooq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import org.jooq.DSLContext;

/**
 * JooqDataTables.
 *
 * @param <E> the type parameter
 * @author Pierre Adam
 * @since 25.03.10
 */
public class JooqDataTables<E> extends AdvancedJooqDataTables<E, JooqDataTablesContext> {

    /**
     * Instantiates a new Jooq data tables.
     *
     * @param entityClass  the entity class
     * @param objectMapper the object mapper
     * @param dslContext   the dsl context
     */
    public JooqDataTables(final Class<E> entityClass, final ObjectMapper objectMapper, final DSLContext dslContext) {
        super(entityClass, objectMapper, dslContext);
    }

    @Override
    public JsonNode getAjaxResult(final Parameters parameters) {
        return super.getAjaxResult(parameters, new JooqDataTablesContext(this.dslContext));
    }
}
