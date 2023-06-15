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
 * LongConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class LongConverter extends Converter<Long> {

    /**
     * Instantiates a new converter.
     */
    public LongConverter() {
        super(Long.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final Long obj, final Context<Payload> context) {
        array.add(obj);
    }
}
