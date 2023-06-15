/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.implementations;

import com.jackson42.java.datatables.interfaces.Payload;
import typedmap.TypedKey;
import typedmap.TypedMap;
import typedmap.TypedMapDecorator;

import java.util.Optional;

/**
 * PlayDataTablesPayloadImpl.
 *
 * @author Pierre Adam
 * @since 21.03.01
 */
public class BasicPayload implements Payload {

    /**
     * The Typed map.
     */
    private final TypedMap typedMap;

    /**
     * Instantiates a new Play data tables payload.
     */
    public BasicPayload() {
        this.typedMap = TypedMapDecorator.typedMap();
    }

    @Override
    public <T> void put(final TypedKey<T> key, final T value) {
        this.typedMap.put(key, value);
    }

    @Override
    public <T> T get(final TypedKey<T> key) {
        return this.typedMap.getTyped(key);
    }

    @Override
    public <T> Optional<T> getOptional(final TypedKey<T> key) {
        return Optional.ofNullable(this.typedMap.getTyped(key));
    }
}
