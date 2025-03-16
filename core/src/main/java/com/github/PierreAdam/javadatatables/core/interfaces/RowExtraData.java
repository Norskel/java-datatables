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

package com.github.PierreAdam.javadatatables.core.interfaces;

import java.util.function.Function;

/**
 * RowExtraData.
 *
 * @param <E> the type parameter
 * @author Pierre Adam
 * @since 25.03.13
 */
public interface RowExtraData<E> {

    /**
     * Sets row id.
     *
     * @param rowIdSupplier the row id supplier
     * @return the row id
     */
    RowExtraData<E> setRowId(Function<E, String> rowIdSupplier);

    /**
     * Sets row class.
     *
     * @param rowClassSupplier the row class supplier
     * @return the row class
     */
    RowExtraData<E> setRowClass(Function<E, String> rowClassSupplier);

    /**
     * Sets row data.
     *
     * @param rowDataSupplier the row data supplier
     * @return the row data
     */
    RowExtraData<E> setRowData(Function<E, Object> rowDataSupplier);

    /**
     * Sets row attr.
     *
     * @param rowAttrSupplier the row attr supplier
     * @return the row attr
     */
    RowExtraData<E> setRowAttr(Function<E, Object> rowAttrSupplier);
}
