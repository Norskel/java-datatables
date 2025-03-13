/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

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
    protected JsonNode internalAsValueNode(final Long obj, final Object context) {
        return (obj == null ? NullNode.getInstance() : LongNode.valueOf(obj));
    }
}
