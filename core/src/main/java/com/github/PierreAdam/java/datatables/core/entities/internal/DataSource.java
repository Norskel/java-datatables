/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.entities.internal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DataSource.
 *
 * @param <E> the Entity type
 * @author Pierre Adam
 * @since 21.03.01
 */
@Getter
@Setter
public class DataSource<E> {

    /**
     * The Entities.
     */
    private List<E> entities;

    /**
     * The Records total.
     */
    private long recordsTotal;

    /**
     * The Records filtered.
     */
    private long recordsFiltered;

    /**
     * Instantiates a new Data source.
     */
    public DataSource() {
    }

    /**
     * Instantiates a new Data source.
     *
     * @param recordsTotal    the records total
     * @param recordsFiltered the records filtered
     * @param entities        the entities
     */
    public DataSource(final long recordsTotal, final long recordsFiltered, final List<E> entities) {
        this.entities = entities;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }
}
