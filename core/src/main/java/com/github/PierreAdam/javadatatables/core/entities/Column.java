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
    private String data;

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

    /**
     * Gets safe name.
     *
     * @return the safe name
     */
    public String getSafeName() {
        return this.name == null || this.name.isEmpty() ? this.data : this.name;
    }
}
