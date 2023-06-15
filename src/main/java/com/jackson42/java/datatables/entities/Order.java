/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.entities;

import com.jackson42.java.datatables.enumerations.OrderEnum;

import java.util.Locale;

/**
 * Order.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
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
     * Get the column.
     *
     * @return the column
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Set the column.
     *
     * @param column the column
     */
    public void setColumn(final int column) {
        this.column = column;
    }

    /**
     * Get the direction
     *
     * @return the direction
     */
    public String getDir() {
        return this.dir;
    }

    /**
     * Set the direction
     *
     * @param dir the direction
     */
    public void setDir(final String dir) {
        this.dir = dir;
    }

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
