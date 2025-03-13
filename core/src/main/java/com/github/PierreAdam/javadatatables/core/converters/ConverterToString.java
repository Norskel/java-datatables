/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * ConverterToString.
 *
 * @param <T> the type parameter
 * @author Pierre Adam
 * @since 21.03.29
 */
public abstract class ConverterToString<T> extends Converter<T> {

    /**
     * Instantiates a new converter.
     *
     * @param tClass the class of T
     */
    public ConverterToString(final Class<T> tClass) {
        super(tClass);
    }

    @Override
    protected JsonNode internalAsValueNode(final T obj, final Object context) {
        return (obj == null ? NullNode.getInstance() : TextNode.valueOf(this.convert(obj, context)));
    }

    /**
     * Convert the object to a string.
     *
     * @param obj     the object
     * @param context the context
     * @return the string
     */
    public abstract String convert(final T obj, final Object context);
}
