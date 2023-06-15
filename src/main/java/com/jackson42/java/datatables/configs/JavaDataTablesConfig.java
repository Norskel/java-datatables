/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.configs;

import com.jackson42.java.datatables.converters.Converter;
import com.jackson42.java.datatables.converters.standards.*;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaDataTablesConfig.
 *
 * @author Pierre Adam
 * @since 21.03.29
 */
public class JavaDataTablesConfig {

    /**
     * The global instance of PlayDataTablesConfig.
     */
    private static JavaDataTablesConfig instance;

    /**
     * The Converters.
     */
    private final Map<Class<?>, Converter<?>> converters;

    /**
     * Instantiates a new PlayDataTablesConfig.
     */
    private JavaDataTablesConfig() {
        this.converters = new HashMap<>();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public synchronized static JavaDataTablesConfig getInstance() {
        if (JavaDataTablesConfig.instance == null) {
            JavaDataTablesConfig.instance = new JavaDataTablesConfig();
            JavaDataTablesConfig.instance
                    .addConverter(new StringConverter())
                    .addConverter(new IntegerConverter())
                    .addConverter(new LongConverter())
                    .addConverter(new DoubleConverter())
                    .addConverter(new FloatConverter())
                    .addConverter(new BooleanConverter())
                    .addConverter(new BigIntegerConverter())
                    .addConverter(new BigDecimalConverter())
                    .addConverter(new EnumConverter())
                    .addConverter(new UUIDConverter())
                    .addConverter(new JsonNodeConverter())
                    .addConverter(new DateTimeConverter());
        }
        return JavaDataTablesConfig.instance;
    }

    /**
     * Add converter play data tables config.
     *
     * @param <T>       the type parameter
     * @param converter the converter
     * @return the play data tables config
     */
    public <T> JavaDataTablesConfig addConverter(final Converter<T> converter) {
        this.converters.put(converter.getBackedType(), converter);
        return this;
    }

    /**
     * Gets the converters.
     *
     * @return the converters
     */
    public Map<Class<?>, Converter<?>> getConverters() {
        return this.converters;
    }
}
