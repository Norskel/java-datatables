/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

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
    protected JsonNode internalAsValueNode(final Boolean obj, final Object context) {
        return (obj == null ? NullNode.getInstance() : BooleanNode.valueOf(obj));
    }
}
