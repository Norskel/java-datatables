package com.github.PierreAdam.javadatatables.jooq;

/**
 * ComputableEntity.
 *
 * @author Pierre Adam
 * @since 25.03.10
 */
public interface ComputableEntity {

    /**
     * Compute.
     *
     * @param context the context
     */
    void compute(final JooqDataTablesContext context);
}
