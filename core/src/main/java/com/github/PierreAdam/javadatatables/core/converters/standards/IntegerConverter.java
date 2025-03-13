/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

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
    protected JsonNode internalAsValueNode(final Integer obj, final Object context) {
        return (obj == null ? NullNode.getInstance() : IntNode.valueOf(obj));
    }
}
