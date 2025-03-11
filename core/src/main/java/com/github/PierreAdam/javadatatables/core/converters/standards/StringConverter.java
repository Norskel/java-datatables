/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.github.PierreAdam.javadatatables.core.converters.ConverterToString;

/**
 * StringConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class StringConverter extends ConverterToString<String> {

    /**
     * Instantiates a new converter.
     */
    public StringConverter() {
        super(String.class);
    }

    @Override
    public String convert(final String obj, final Object context) {
        return obj;
    }
}
