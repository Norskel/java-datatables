/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.java.datatables.core.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * AjaxQueryForm.
 *
 * @author Pierre Adam
 * @since 20.07.02
 */
@Getter
@Setter
public class Parameters {

    /**
     * The draw number. It is increased by one each time the page request the server and must be given back in the answer.
     */
    private int draw;

    /**
     * The list of columns.
     */
    private List<Column> columns;

    /**
     * The order.
     */
    private List<Order> order;

    /**
     * The start offset.
     */
    private int start;

    /**
     * The length of the data to send.
     */
    private int length;

    /**
     * The search.
     */
    private Search search;

    /**
     * Get the list of columns or an empty list if columns is null.
     *
     * @return the list of columns
     */
    public List<Column> getSafeColumns() {
        return this.columns != null ? this.columns : new ArrayList<>();
    }

    /**
     * Get the list for ordering or an empty list if order is null.
     *
     * @return the list of ordering
     */
    public List<Order> getSafeOrder() {
        return this.order != null ? this.order : new ArrayList<>();
    }

    /**
     * Gets optional global search.
     *
     * @return the optional global search
     */
    public Optional<Search> getOptionalGlobalSearch() {
        if (this.getSearch() != null && this.getSearch().getValue() != null && !this.getSearch().getValue().isEmpty()) {
            return Optional.ofNullable(this.getSearch());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get the indexed columns.
     *
     * @return the indexed columns
     */
    public Map<Integer, Column> getIndexedColumns() {
        return new HashMap<Integer, Column>() {{
            for (final Column column : Parameters.this.getSafeColumns()) {
                this.put(column.getData(), column);
            }
        }};
    }

    /**
     * Gets ordered columns.
     *
     * @return the ordered columns
     */
    public List<Column> getOrderedColumns() {
        final TreeMap<Integer, Column> treeMap = new TreeMap<>(this.getIndexedColumns());
        return new ArrayList<>(treeMap.values());
    }
}
