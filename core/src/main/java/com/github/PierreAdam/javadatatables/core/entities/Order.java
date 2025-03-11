/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.entities;

import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

/**
 * Order.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
@Getter
@Setter
public class Order {

    /**
     * The number of the column.
     */
    private int column;

    /**
     * The direction of sort.
     */
    private String dir;

    /**
     * Get the order as an enum. Based on the direction
     *
     * @return the order
     */
    public OrderEnum getOrder() {
        try {
            return OrderEnum.valueOf(this.dir.toUpperCase(Locale.ENGLISH));
        } catch (final IllegalArgumentException ignore) {
            return OrderEnum.ASC;
        }
    }
}
