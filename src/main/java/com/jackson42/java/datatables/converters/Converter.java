/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.converters;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jackson42.java.datatables.interfaces.Context;
import com.jackson42.java.datatables.interfaces.Payload;

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
     * Add the object as a value in the ArrayNode.
     *
     * @param array   the array
     * @param obj     the object
     * @param context the context
     */
    public final void addToArray(final ArrayNode array, final Object obj, final Context<Payload> context) {
        this.internalAddToArray(array, this.getBackedType().cast(obj), context);
    }

    /**
     * Internal logic of the method addToArray.
     *
     * @param array   the array
     * @param obj     the object
     * @param context the context
     */
    protected abstract void internalAddToArray(final ArrayNode array, final T obj, final Context<Payload> context);
}
