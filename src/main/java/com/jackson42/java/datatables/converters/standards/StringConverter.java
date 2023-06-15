/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.converters.standards;

import com.jackson42.java.datatables.converters.ConverterToString;
import com.jackson42.java.datatables.interfaces.Context;
import com.jackson42.java.datatables.interfaces.Payload;

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
    public String convert(final String obj, final Context<Payload> context) {
        return obj;
    }
}
