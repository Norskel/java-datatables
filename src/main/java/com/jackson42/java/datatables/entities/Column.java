/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.entities;

/**
 * ColumnEntity.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
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
     * Is the column searcheable.
     */
    private boolean searcheable;

    /**
     * Is the column orderable.
     */
    private boolean orderable;

    /**
     * The search object.
     */
    private Search search;

    /**
     * Get the data.
     *
     * @return the data
     */
    public int getData() {
        return this.data;
    }

    /**
     * Set the data
     *
     * @param data the data
     */
    public void setData(final int data) {
        this.data = data;
    }

    /**
     * Get the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Is searcheable ?
     *
     * @return true if is searcheable
     */
    public boolean isSearcheable() {
        return this.searcheable;
    }

    /**
     * Set searcheable.
     *
     * @param searcheable true if searcheable
     */
    public void setSearcheable(final boolean searcheable) {
        this.searcheable = searcheable;
    }

    /**
     * Is orderable ?
     *
     * @return true if is orderable
     */
    public boolean isOrderable() {
        return this.orderable;
    }

    /**
     * Set orderable.
     *
     * @param orderable true if orderable
     */
    public void setOrderable(final boolean orderable) {
        this.orderable = orderable;
    }

    /**
     * Get search query.
     *
     * @return search query
     */
    public Search getSearch() {
        return this.search;
    }

    /**
     * Set search query.
     *
     * @param search search query
     */
    public void setSearch(final Search search) {
        this.search = search;
    }

    /**
     * Has search boolean.
     *
     * @return the boolean
     */
    public boolean hasSearch() {
        return this.getSearch() != null && this.getSearch().getValue() != null && !this.getSearch().getValue().isEmpty();
    }
}
