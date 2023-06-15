/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.entities;

/**
 * ColumnSearchForm.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
public class Search {

    /**
     * The name to search.
     */
    private String value;

    /**
     * Is the search a regex.
     */
    private boolean regex;

    /**
     * Get the name.
     *
     * @return the name
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the name
     *
     * @param value the name
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Is the query a regex
     *
     * @return true if regex
     */
    public boolean isRegex() {
        return this.regex;
    }

    /**
     * Set is a regex.
     *
     * @param regex true if is a regex
     */
    public void setRegex(final boolean regex) {
        this.regex = regex;
    }
}
