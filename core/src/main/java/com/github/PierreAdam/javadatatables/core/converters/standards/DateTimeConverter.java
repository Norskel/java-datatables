/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.converters.standards;

import com.github.PierreAdam.javadatatables.core.converters.ConverterToString;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeConverter.
 *
 * @author Pierre Adam
 * @since 23.06.16
 */
public class DateTimeConverter extends ConverterToString<OffsetDateTime> {

    /**
     * The Format.
     */
    private final DateTimeFormatter format;

    /**
     * Instantiates a new converter.
     */
    public DateTimeConverter() {
        this(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Instantiates a new Date time converter.
     *
     * @param format the format
     */
    public DateTimeConverter(final DateTimeFormatter format) {
        super(OffsetDateTime.class);
        this.format = format;
    }

    @Override
    public String convert(final OffsetDateTime obj, final Object context) {
        return obj.format(this.format);
    }
}
