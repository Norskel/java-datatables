/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.entities.internal;

import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;
import lombok.Setter;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * FieldBehavior.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <C> the Context type
 * @author Pierre Adam
 * @since 21.03.18
 */
@Setter
public class FieldBehavior<E, S, C> {

    /**
     * If set, the supplier will be called when forging the ajax response object.
     * If not set, the value will try to be resolved from the type of the data.
     */
    private BiFunction<E, C, String> displaySupplier;

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
    public Optional<BiFunction<E, C, String>> getDisplaySupplier() {
        return Optional.ofNullable(this.displaySupplier);
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
     * Gets order handler.
     *
     * @return the order handler
     */
    public Optional<BiConsumer<S, OrderEnum>> getOrderHandler() {
        return Optional.ofNullable(this.orderHandler);
    }
}
