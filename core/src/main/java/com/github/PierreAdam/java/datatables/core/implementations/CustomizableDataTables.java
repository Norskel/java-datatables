/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.implementations;

import com.github.PierreAdam.java.datatables.core.entities.Parameters;
import com.github.PierreAdam.java.datatables.core.entities.internal.DataSource;
import com.github.PierreAdam.java.datatables.core.enumerations.OrderEnum;

/**
 * DataTablesHooks.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <C> the Context type
 * @param <U> the type of the implementation
 * @author Pierre Adam
 * @since 21.03.18
 */
public abstract class CustomizableDataTables<E, S, C, U extends CustomizableDataTables<E, S, C, U>> {

    /**
     * Sets pagination.
     *
     * @param provider        the provider
     * @param startElement    the start element
     * @param numberOfElement the number of element
     * @return the pagination
     */
    protected abstract U setPagination(final S provider, int startElement, int numberOfElement);

    /**
     * Data source from provider data source.
     *
     * @param provider the provider
     * @param context  the context
     * @return the data source
     */
    protected abstract DataSource<E> dataSourceFromProvider(final S provider, final C context);

    /**
     * Fallback order handler.
     *
     * @param provider   the provider
     * @param columnName the column name
     * @param order      the order
     * @return the u
     */
    protected U fallbackOrderHandler(final S provider, final String columnName, final OrderEnum order) {
        // Default behavior does nothing

        return this.asSelf();
    }

    /**
     * Fallback search handler.
     *
     * @param provider   the provider
     * @param columnName the column name
     * @param value      the value
     * @return the u
     */
    protected U fallbackSearchHandler(final S provider, final String columnName, final String value) {
        // Default behavior does nothing

        return this.asSelf();
    }

    /**
     * Pre search hook.
     *
     * @param provider   the provider
     * @param context    the context
     * @param parameters the parameters
     * @return the u
     */
    protected U preSearchHook(final S provider, final C context, final Parameters parameters) {
        // Default behavior does nothing

        return this.asSelf();
    }

    /**
     * Post search hook.
     *
     * @param provider   the provider
     * @param context    the context
     * @param parameters the parameters
     * @return the u
     */
    protected U postSearchHook(final S provider, final C context, final Parameters parameters) {
        // Default behavior does nothing

        return this.asSelf();
    }

    /**
     * Pre order hook.
     *
     * @param provider   the provider
     * @param context    the context
     * @param parameters the parameters
     * @return the u
     */
    protected U preOrderHook(final S provider, final C context, final Parameters parameters) {
        // Default behavior does nothing

        return this.asSelf();
    }

    /**
     * Post order hook.
     *
     * @param provider   the provider
     * @param context    the context
     * @param parameters the parameters
     * @return the u
     */
    protected U postOrderHook(final S provider, final C context, final Parameters parameters) {
        // Default behavior does nothing

        return this.asSelf();
    }

    /**
     * Returns itself
     *
     * @return itself u
     */
    public abstract U asSelf();
}
