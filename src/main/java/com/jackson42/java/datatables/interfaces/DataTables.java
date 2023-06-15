/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.jackson42.java.datatables.converters.Converter;
import com.jackson42.java.datatables.entities.Parameters;
import com.jackson42.java.datatables.entities.internal.FieldBehavior;
import com.jackson42.java.datatables.enumerations.OrderEnum;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * PlayDataTables.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <P> the Payload type
 * @author Pierre Adam
 * @since 21.03.01
 */
public interface DataTables<E, S, P extends Payload> {

    /**
     * The initial where condition. Is called on each forged request and should not contains orders or weird things.
     *
     * @param initialQuery the consumer that allows to set the initial query.
     */
    void setInitProviderConsumer(final Consumer<S> initialQuery);

    /**
     * The fields display suppliers. If set for a given field, the supplier will be called when forging the ajax response object.
     * If not set, the answer will try to reach the variable on the given T class.
     *
     * @param fieldName     the field name
     * @param fieldSupplier the field display supplier
     */
    default void setFieldDisplaySupplier(final String fieldName, final Function<E, String> fieldSupplier) {
        this.field(fieldName).setDisplaySupplier((entity, request) -> fieldSupplier.apply(entity));
    }

    /**
     * The fields display suppliers. If set for a given field, the supplier will be called when forging the ajax response object.
     * If not set, the answer will try to reach the variable on the given T class.
     *
     * @param fieldName     the field name
     * @param fieldSupplier the field display supplier
     */
    default void setFieldDisplaySupplier(final String fieldName, final BiFunction<E, Context<P>, String> fieldSupplier) {
        this.field(fieldName).setDisplaySupplier(fieldSupplier);
    }

    /**
     * The fields search handler. If set for a given field, the handler will be called when searching on that field.
     * If not set, the search will have no effect.
     *
     * @param fieldName     the field name
     * @param searchHandler the field search handler
     */
    default void setSearchHandler(final String fieldName, final BiConsumer<S, String> searchHandler) {
        this.field(fieldName).setSearchHandler(searchHandler);
    }

    /**
     * The fields order handler. If set for a given field, the handler will be called when ordering on that field.
     * If not set, the search will be set to the name of the field followed by "ASC" or "DESC"
     *
     * @param fieldName    the field name
     * @param orderHandler the field order handler
     */
    default void setOrderHandler(final String fieldName, final BiConsumer<S, OrderEnum> orderHandler) {
        this.field(fieldName).setOrderHandler(orderHandler);
    }

    /**
     * The global search supplier. If set, the handler will be called when a search not specific to a field is required.
     *
     * @param globalSearchHandler the global search handler
     */
    void setGlobalSearchHandler(final BiConsumer<S, String> globalSearchHandler);

    /**
     * Builds the Ajax result in the form of a Json ObjectNode. Parameters SHOULD come from a form.
     *
     * @param parameters the parameters
     * @param payload    the payload
     * @return the Json ObjectNode
     * @see Parameters
     */
    JsonNode getAjaxResult(final Parameters parameters, final P payload);

    /**
     * Builds the Ajax result in the form of a Json ObjectNode. Parameters SHOULD come from a form.
     *
     * @param parameters the parameters
     * @return the Json ObjectNode
     * @see Parameters
     */
    default JsonNode getAjaxResult(final Parameters parameters) {
        return this.getAjaxResult(parameters, null);
    }

    /**
     * Gets the field behavior.
     *
     * @param fieldName the field name
     * @return the field behavior
     */
    FieldBehavior<E, S, P> field(final String fieldName);

    /**
     * Sets the field behavior.
     *
     * @param fieldName     the field name
     * @param fieldBehavior the field behavior
     */
    void setField(final String fieldName, final FieldBehavior<E, S, P> fieldBehavior);

    /**
     * Add a converter specific to this instance.
     *
     * @param <T>       the type parameter
     * @param converter the converter
     */
    <T> void addConverter(final Converter<T> converter);
}
