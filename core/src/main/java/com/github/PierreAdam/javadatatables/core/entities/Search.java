/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * ColumnSearchForm.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
@Setter
@Getter
public class Search {

    /**
     * The name to search.
     */
    private String value;

    /**
     * Is the search a regex.
     */
    private boolean regex;
}
