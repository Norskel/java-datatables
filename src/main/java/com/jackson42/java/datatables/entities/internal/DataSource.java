/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.entities.internal;

import java.util.List;

/**
 * DataSource.
 *
 * @param <E> the Entity type
 * @author Pierre Adam
 * @since 21.03.01
 */
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

    /**
     * Gets entities.
     *
     * @return the entities
     */
    public List<E> getEntities() {
        return this.entities;
    }

    /**
     * Sets entities.
     *
     * @param entities the entities
     */
    public void setEntities(final List<E> entities) {
        this.entities = entities;
    }

    /**
     * Gets records total.
     *
     * @return the records total
     */
    public long getRecordsTotal() {
        return this.recordsTotal;
    }

    /**
     * Sets records total.
     *
     * @param recordsTotal the records total
     */
    public void setRecordsTotal(final long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    /**
     * Gets records filtered.
     *
     * @return the records filtered
     */
    public long getRecordsFiltered() {
        return this.recordsFiltered;
    }

    /**
     * Sets records filtered.
     *
     * @param recordsFiltered the records filtered
     */
    public void setRecordsFiltered(final long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
