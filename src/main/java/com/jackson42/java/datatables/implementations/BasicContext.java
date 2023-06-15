/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.implementations;

import com.jackson42.java.datatables.interfaces.Context;
import com.jackson42.java.datatables.interfaces.Payload;

/**
 * Context.
 *
 * @param <P> the type of the payload
 * @author Pierre Adam
 * @since 21.03.01
 */
public class BasicContext<P extends Payload> implements Context<P> {

    /**
     * The current payload.
     */
    private final P payload;

    /**
     * Instantiates a new Basic context.
     *
     * @param payload the payload
     */
    public BasicContext(final P payload) {
        this.payload = payload;
    }

    @Override
    public P getPayload() {
        return this.payload;
    }

    @Override
    public Context<Payload> asGeneric() {
        return new BasicContext<Payload>(this.payload);
    }
}
