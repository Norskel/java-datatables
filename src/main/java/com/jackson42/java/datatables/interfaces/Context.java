/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.interfaces;

/**
 * PdtContext.
 *
 * @param <P> the type parameter
 * @author Pierre Adam
 * @since 21.03.01
 */
public interface Context<P extends Payload> {

    /**
     * Gets payload.
     *
     * @return the payload
     */
    P getPayload();

    /**
     * As generic context.
     *
     * @return the context
     */
    Context<Payload> asGeneric();
}
