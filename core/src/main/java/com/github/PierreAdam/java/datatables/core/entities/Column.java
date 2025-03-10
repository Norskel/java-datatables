/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * ColumnEntity.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
@Getter
@Setter
public class Column {

    /**
     * The number of the column.
     */
    private int data;

    /**
     * The name of the column.
     */
    private String name;

    /**
     * Is the column searchable.
     */
    private boolean searchable;

    /**
     * Is the column orderable.
     */
    private boolean orderable;

    /**
     * The search object.
     */
    private Search search;

    /**
     * Has search boolean.
     *
     * @return the boolean
     */
    public boolean hasSearch() {
        return this.getSearch() != null && this.getSearch().getValue() != null && !this.getSearch().getValue().isEmpty();
    }
}
