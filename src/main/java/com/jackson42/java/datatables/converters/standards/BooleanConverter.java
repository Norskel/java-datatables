/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.converters.standards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jackson42.java.datatables.converters.Converter;
import com.jackson42.java.datatables.interfaces.Context;
import com.jackson42.java.datatables.interfaces.Payload;

/**
 * BooleanConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class BooleanConverter extends Converter<Boolean> {

    /**
     * Instantiates a new converter.
     */
    public BooleanConverter() {
        super(Boolean.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final Boolean obj, final Context<Payload> context) {
        array.add(obj);
    }
}
