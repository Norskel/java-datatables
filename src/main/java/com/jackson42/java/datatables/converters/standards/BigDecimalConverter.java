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

import java.math.BigDecimal;

/**
 * BigDecimalConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class BigDecimalConverter extends Converter<BigDecimal> {

    /**
     * Instantiates a new converter.
     */
    public BigDecimalConverter() {
        super(BigDecimal.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final BigDecimal obj, final Context<Payload> context) {
        array.add(obj);
    }
}
