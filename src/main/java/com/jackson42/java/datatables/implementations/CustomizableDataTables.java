/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.implementations;

import com.jackson42.java.datatables.entities.Parameters;
import com.jackson42.java.datatables.entities.internal.DataSource;
import com.jackson42.java.datatables.enumerations.OrderEnum;
import com.jackson42.java.datatables.interfaces.Payload;

/**
 * DataTablesHooks.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <P> the Payload type
 * @author Pierre Adam
 * @since 21.03.18
 */
public abstract class CustomizableDataTables<E, S, P extends Payload> {

    /**
     * Gets default payload.
     *
     * @return the default payload
     */
    protected abstract P getDefaultPayload();

    /**
     * Sets pagination.
     *
     * @param provider        the provider
     * @param startElement    the start element
     * @param numberOfElement the number of element
     */
    protected abstract void setPagination(final S provider, int startElement, int numberOfElement);

    /**
     * Data source from provider data source.
     *
     * @param provider the provider
     * @param payload  the payload
     * @return the data source
     */
    protected abstract DataSource<E> dataSourceFromProvider(final S provider, final P payload);

    /**
     * Fallback order handler.
     *
     * @param provider   the provider
     * @param columnName the column name
     * @param order      the order
     */
    protected void fallbackOrderHandler(final S provider, final String columnName, final OrderEnum order) {
        // Default behavior does nothing
    }

    /**
     * Fallback search handler.
     *
     * @param provider   the provider
     * @param columnName the column name
     * @param value      the value
     */
    protected void fallbackSearchHandler(final S provider, final String columnName, final String value) {
        // Default behavior does nothing
    }

    /**
     * Pre search hook.
     *
     * @param provider   the provider
     * @param payload    the payload
     * @param parameters the parameters
     */
    protected void preSearchHook(final S provider, final P payload, final Parameters parameters) {
        // Default behavior does nothing
    }

    /**
     * Post search hook.
     *
     * @param provider   the provider
     * @param payload    the payload
     * @param parameters the parameters
     */
    protected void postSearchHook(final S provider, final P payload, final Parameters parameters) {
        // Default behavior does nothing
    }

    /**
     * Pre order hook.
     *
     * @param provider   the provider
     * @param payload    the payload
     * @param parameters the parameters
     */
    protected void preOrderHook(final S provider, final P payload, final Parameters parameters) {
        // Default behavior does nothing
    }

    /**
     * Post order hook.
     *
     * @param provider   the provider
     * @param payload    the payload
     * @param parameters the parameters
     */
    protected void postOrderHook(final S provider, final P payload, final Parameters parameters) {
        // Default behavior does nothing
    }
}
