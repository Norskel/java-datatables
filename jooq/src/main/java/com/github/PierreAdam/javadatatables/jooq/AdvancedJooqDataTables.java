package com.github.PierreAdam.javadatatables.jooq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.internal.DataSource;
import com.github.PierreAdam.javadatatables.core.implementations.SimpleDataTables;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.util.List;

/**
 * JooqDataTables.
 *
 * @param <E> the type parameter
 * @author Pierre Adam
 * @since 25.03.10
 */
public class AdvancedJooqDataTables<E, C extends JooqDataTablesContext> extends SimpleDataTables<E, JooqProvider, C, AdvancedJooqDataTables<E, C>> {

    /**
     * The Dsl context.
     */
    protected DSLContext dslContext;

    /**
     * Instantiates a new Jooq data tables.
     *
     * @param entityClass  the entity class
     * @param objectMapper the object mapper
     * @param dslContext   the dsl context
     */
    public AdvancedJooqDataTables(final Class<E> entityClass, final ObjectMapper objectMapper, final DSLContext dslContext) {
        super(entityClass, objectMapper, () -> new JooqProvider(dslContext));
        this.dslContext = dslContext;
    }

    /**
     * Sets pagination.
     *
     * @param jooqProvider    the jooq provider
     * @param startElement    the start element
     * @param numberOfElement the number of element
     * @return the pagination
     */
    @Override
    protected AdvancedJooqDataTables<E, C> setPagination(final JooqProvider jooqProvider, final int startElement, final int numberOfElement) {
        jooqProvider.setStartElement(startElement).setNumberOfElement(numberOfElement);

        return this;
    }

    /**
     * Data source from provider data source.
     *
     * @param jooqProvider the jooq provider
     * @param context      the context
     * @return the data source
     */
    @Override
    protected DataSource<E> dataSourceFromProvider(final JooqProvider jooqProvider, final C context) {
        final Result<? extends Record> fetch = jooqProvider.getAsForgedQuery().fetch();
        final List<E> entities = fetch.into(this.entityClass);

        if (ComputableEntity.class.isAssignableFrom(this.entityClass)) {
            entities.forEach(e -> ((ComputableEntity) e).compute(context));
        }

        return new DataSource<>(jooqProvider.getTotalUnfilteredResultCount(), jooqProvider.getTotalFilteredResultCount(), entities);
    }
}
