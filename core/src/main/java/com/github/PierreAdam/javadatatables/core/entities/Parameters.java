/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2025 Pierre Adam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.PierreAdam.javadatatables.core.entities;

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
            int i = 0;
            for (final Column column : Parameters.this.getSafeColumns()) {
                this.put(i, column);
                i++;
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
