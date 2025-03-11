/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

/**
 * FloatConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class FloatConverter extends Converter<Float> {

    /**
     * Instantiates a new converter.
     */
    public FloatConverter() {
        super(Float.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final Float obj, final Object context) {
        array.add(obj);
    }
}
