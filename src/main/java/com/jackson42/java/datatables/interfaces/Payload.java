/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.interfaces;

import typedmap.TypedKey;

import java.util.Optional;

/**
 * PlayDataTablesPayload.
 *
 * @author Pierre Adam
 * @since 21.03.01
 */
public interface Payload {

    /**
     * Put the typed key with it's value in the payload.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param value the value
     */
    <T> void put(final TypedKey<T> key, final T value);

    /**
     * Get data of T from the payload.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the t
     */
    <T> T get(TypedKey<T> key);

    /**
     * Get and optional data of T from the payload.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the t
     */
    <T> Optional<T> getOptional(TypedKey<T> key);
}
