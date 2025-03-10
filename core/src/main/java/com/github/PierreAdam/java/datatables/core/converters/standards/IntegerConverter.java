/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.converters.standards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.PierreAdam.java.datatables.core.converters.Converter;

/**
 * IntegerConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class IntegerConverter extends Converter<Integer> {

    /**
     * Instantiates a new converter.
     */
    public IntegerConverter() {
        super(Integer.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final Integer obj, final Object context) {
        array.add(obj);
    }
}
