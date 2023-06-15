/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.jackson42.java.datatables.entities.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * AjaxResult.
 *
 * @author Pierre Adam
 * @since 21.03.01
 */
public class AjaxResult {

    /**
     * The Draw.
     */
    private final int draw;

    /**
     * The Data.
     */
    private final ArrayNode data;

    /**
     * The Records total.
     */
    private long recordsTotal;

    /**
     * The Records filtered.
     */
    private long recordsFiltered;

    /**
     * Instantiates a new Ajax result.
     *
     * @param draw         the draw
     * @param objectMapper the object mapper
     */
    public AjaxResult(final int draw, final ObjectMapper objectMapper) {
        this.draw = draw;
        this.data = objectMapper.createArrayNode();
    }

    /**
     * Gets draw.
     *
     * @return the draw
     */
    public int getDraw() {
        return this.draw;
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

    /**
     * Gets data.
     *
     * @return the data
     */
    public ArrayNode getData() {
        return this.data;
    }
}
