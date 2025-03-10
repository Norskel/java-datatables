/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.converters.standards;

import com.github.PierreAdam.java.datatables.core.converters.ConverterToString;

import java.util.UUID;

/**
 * UUIDConverter.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class UUIDConverter extends ConverterToString<UUID> {

    /**
     * Instantiates a new converter.
     */
    public UUIDConverter() {
        super(UUID.class);
    }

    @Override
    public String convert(final UUID obj, final Object context) {
        return obj.toString();
    }
}
