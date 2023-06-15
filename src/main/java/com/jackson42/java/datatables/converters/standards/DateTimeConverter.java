/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.converters.standards;

import com.jackson42.java.datatables.converters.ConverterToString;
import com.jackson42.java.datatables.interfaces.Context;
import com.jackson42.java.datatables.interfaces.Payload;
import typedmap.DefaultTypedKey;
import typedmap.TypedKey;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeConverter.
 *
 * @author Pierre Adam
 * @since 23.06.16
 */
public class DateTimeConverter extends ConverterToString<OffsetDateTime> {

    public static final TypedKey<DateTimeFormatter> DATETIME_FORMAT = DefaultTypedKey.typedKey();

    /**
     * Instantiates a new converter.
     */
    public DateTimeConverter() {
        super(OffsetDateTime.class);
    }

    @Override
    public String convert(final OffsetDateTime obj, final Context<Payload> context) {
        final DateTimeFormatter format = context.getPayload()
                .getOptional(DateTimeConverter.DATETIME_FORMAT)
                .orElse(DateTimeFormatter.ISO_DATE_TIME);

        return obj.format(format);
    }
}
