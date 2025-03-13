/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

/**
 * DoubleConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class DoubleConverter extends Converter<Double> {

    /**
     * Instantiates a new converter.
     */
    public DoubleConverter() {
        super(Double.class);
    }

    @Override
    protected JsonNode internalAsValueNode(final Double obj, final Object context) {
        return (obj == null ? NullNode.getInstance() : DoubleNode.valueOf(obj));
    }
}
