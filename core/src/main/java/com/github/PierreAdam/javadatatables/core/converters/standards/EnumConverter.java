/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

/**
 * EnumConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class EnumConverter extends Converter<Enum> {

    /**
     * Instantiates a new converter.
     */
    public EnumConverter() {
        super(Enum.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final Enum obj, final Object context) {
        final String tmp = obj.toString();
        try {
            array.add(Long.valueOf(tmp));
        } catch (final NumberFormatException ignore) {
            array.add(tmp);
        }
    }
}
