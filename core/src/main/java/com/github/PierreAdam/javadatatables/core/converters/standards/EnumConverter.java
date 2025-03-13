/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.PierreAdam.javadatatables.core.converters.Converter;

/**
 * EnumConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class EnumConverter extends Converter<Enum> {

    /**
     * Instantiates a new converter.
     */
    public EnumConverter() {
        super(Enum.class);
    }

    @Override
    protected JsonNode internalAsValueNode(final Enum obj, final Object context) {
        if (obj == null) {
            return NullNode.getInstance();
        }

        final String tmp = obj.toString();

        try {
            return LongNode.valueOf(Long.valueOf(tmp));
        } catch (final NumberFormatException ignore) {
            return TextNode.valueOf(tmp);
        }
    }
}
