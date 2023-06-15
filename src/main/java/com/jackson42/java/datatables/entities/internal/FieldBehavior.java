/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.entities.internal;

import com.jackson42.java.datatables.enumerations.OrderEnum;
import com.jackson42.java.datatables.interfaces.Context;
import com.jackson42.java.datatables.interfaces.Payload;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * FieldBehavior.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <P> the Payload type
 * @author Pierre Adam
 * @since 21.03.18
 */
public class FieldBehavior<E, S, P extends Payload> {

    /**
     * If set, the supplier will be called when forging the ajax response object.
     * If not set, the value will try to be resolved from the type of the data.
     */
    private BiFunction<E, Context<P>, String> displaySupplier;

    /**
     * If set, the search handler will be called when searching.
     * If not set, the search will fallback to the default behavior
     */
    private BiConsumer<S, String> searchHandler;

    /**
     * The Order handler.
     */
    private BiConsumer<S, OrderEnum> orderHandler;

    /**
     * Gets display supplier.
     *
     * @return the display supplier
     */
    public Optional<BiFunction<E, Context<P>, String>> getDisplaySupplier() {
        return Optional.ofNullable(this.displaySupplier);
    }

    /**
     * Sets display supplier.
     *
     * @param displaySupplier the display supplier
     * @return the display supplier
     */
    public FieldBehavior<E, S, P> setDisplaySupplier(final BiFunction<E, Context<P>, String> displaySupplier) {
        this.displaySupplier = displaySupplier;
        return this;
    }

    /**
     * Gets search handler.
     *
     * @return the search handler
     */
    public Optional<BiConsumer<S, String>> getSearchHandler() {
        return Optional.ofNullable(this.searchHandler);
    }

    /**
     * Sets search handler.
     *
     * @param searchHandler the search handler
     * @return the search handler
     */
    public FieldBehavior<E, S, P> setSearchHandler(final BiConsumer<S, String> searchHandler) {
        this.searchHandler = searchHandler;
        return this;
    }

    /**
     * Gets order handler.
     *
     * @return the order handler
     */
    public Optional<BiConsumer<S, OrderEnum>> getOrderHandler() {
        return Optional.ofNullable(this.orderHandler);
    }

    /**
     * Sets order handler.
     *
     * @param orderHandler the order handler
     * @return the order handler
     */
    public FieldBehavior<E, S, P> setOrderHandler(final BiConsumer<S, OrderEnum> orderHandler) {
        this.orderHandler = orderHandler;
        return this;
    }
}
