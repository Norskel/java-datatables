/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025 Pierre Adam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
