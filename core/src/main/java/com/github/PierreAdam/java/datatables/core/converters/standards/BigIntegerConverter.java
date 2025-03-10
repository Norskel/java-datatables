/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.converters.standards;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.PierreAdam.java.datatables.core.converters.Converter;

import java.math.BigInteger;

/**
 * BigIntegerConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class BigIntegerConverter extends Converter<BigInteger> {

    /**
     * Instantiates a new converter.
     */
    public BigIntegerConverter() {
        super(BigInteger.class);
    }

    @Override
    public void internalAddToArray(final ArrayNode array, final BigInteger obj, final Object context) {
        array.add(obj);
    }
}
