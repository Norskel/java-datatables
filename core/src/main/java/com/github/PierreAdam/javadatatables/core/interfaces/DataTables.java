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

package com.github.PierreAdam.javadatatables.core.interfaces;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import com.github.PierreAdam.javadatatables.core.entities.internal.FieldBehavior;
import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * DataTables.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <C> the Context type
 * @param <U> the type of the implementation
 * @author Pierre Adam
 * @since 21.03.01
 */
public interface DataTables<E, S, C, U extends DataTables<E, S, C, U>> {

    /**
     * The initial condition for the provider.
     * Is called on each forged request and should not contain orders or parameters that could be changed dynamically by datatables.
     *
     * @param initialQuery the consumer that allows to set the initial query.
     * @return itself init provider consumer
     */
    U setInitProviderConsumer(final Consumer<S> initialQuery);

    /**
     * The fields display suppliers. If set for a given field, the supplier will be called when forging the ajax response object.
     * If not set, the answer will try to reach the variable on the given T class.
     *
     * @param fieldName     the field name
     * @param fieldSupplier the field display supplier
     * @return itself field display supplier
     */
    default U setFieldDisplaySupplier(final String fieldName, final Function<E, String> fieldSupplier) {
        this.field(fieldName).setDisplaySupplier((entity, request) -> fieldSupplier.apply(entity));

        return this.asSelf();
    }

    /**
     * The fields display suppliers. If set for a given field, the supplier will be called when forging the ajax response object.
     * If not set, the answer will try to reach the variable on the given T class.
     *
     * @param fieldName     the field name
     * @param fieldSupplier the field display supplier
     * @return itself field display supplier
     */
    default U setFieldDisplaySupplier(final String fieldName, final BiFunction<E, C, String> fieldSupplier) {
        this.field(fieldName).setDisplaySupplier(fieldSupplier);

        return this.asSelf();
    }

    /**
     * The fields search handler. If set for a given field, the handler will be called when searching on that field.
     * If not set, the search will have no effect.
     *
     * @param fieldName     the field name
     * @param searchHandler the field search handler
     * @return itself search handler
     */
    default U setSearchHandler(final String fieldName, final BiConsumer<S, String> searchHandler) {
        this.field(fieldName).setSearchHandler(searchHandler);

        return this.asSelf();
    }

    /**
     * The fields order handler. If set for a given field, the handler will be called when ordering on that field.
     * If not set, the search will be set to the name of the field followed by "ASC" or "DESC"
     *
     * @param fieldName    the field name
     * @param orderHandler the field order handler
     * @return itself order handler
     */
    default U setOrderHandler(final String fieldName, final BiConsumer<S, OrderEnum> orderHandler) {
        this.field(fieldName).setOrderHandler(orderHandler);

        return this.asSelf();
    }

    /**
     * The global search supplier. If set, the handler will be called when a search not specific to a field is required.
     *
     * @param globalSearchHandler the global search handler
     * @return itself global search handler
     */
    U setGlobalSearchHandler(final BiConsumer<S, String> globalSearchHandler);

    /**
     * Sets row extra data.
     *
     * @param rowExtraDataConsumer the row extra data consumer
     * @return the row extra data
     */
    U setRowExtraData(final Consumer<RowExtraData<E>> rowExtraDataConsumer);

    /**
     * Builds the Ajax result in the form of a Json ObjectNode. Parameters SHOULD come from a form.
     *
     * @param parameters the parameters
     * @param context    the context
     * @return the Json ObjectNode
     * @see Parameters
     */
    JsonNode getAjaxResult(final Parameters parameters, final C context);

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
    FieldBehavior<E, S, C> field(final String fieldName);

    /**
     * Gets the field behavior in a consumer.
     *
     * @param fieldName             the field name
     * @param fieldBehaviorConsumer the field behavior consumer
     * @return itself u
     */
    default U field(final String fieldName, final Consumer<FieldBehavior<E, S, C>> fieldBehaviorConsumer) {
        fieldBehaviorConsumer.accept(this.field(fieldName));

        return this.asSelf();
    }

    /**
     * Sets the field behavior.
     *
     * @param fieldName     the field name
     * @param fieldBehavior the field behavior
     * @return itself field
     */
    U setField(final String fieldName, final FieldBehavior<E, S, C> fieldBehavior);

    /**
     * Add a converter specific to this instance.
     *
     * @param <T>       the type parameter
     * @param converter the converter
     * @return itself u
     */
    <T> U addConverter(final Converter<T> converter);

    /**
     * Returns the current instance of the object, allowing method chaining.
     *
     * @return the current instance
     */
    U asSelf();
}
