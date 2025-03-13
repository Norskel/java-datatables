/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * The converters may extends this class to have a standardized behavior.
 *
 * @param <T> the type parameter
 * @author Pierre Adam
 * @since 21.03.28
 */
public abstract class Converter<T> {

    /**
     * The class of T.
     */
    protected final Class<T> tClass;

    /**
     * Instantiates a new converter.
     *
     * @param tClass the class of T
     */
    public Converter(final Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Gets backed type.
     *
     * @return the backed type
     */
    public final Class<T> getBackedType() {
        return this.tClass;
    }

    /**
     * As value node value node.
     *
     * @param obj     the obj
     * @param context the context
     * @return the value node
     */
    public final JsonNode asValueNode(final Object obj, final Object context) {
        return this.internalAsValueNode(this.getBackedType().cast(obj), context);
    }

    /**
     * Internal as value node value node.
     *
     * @param obj     the obj
     * @param context the context
     * @return the value node
     */
    protected abstract JsonNode internalAsValueNode(final T obj, final Object context);
}
