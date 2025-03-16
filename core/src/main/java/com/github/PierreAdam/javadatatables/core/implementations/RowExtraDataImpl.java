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

package com.github.PierreAdam.javadatatables.core.implementations;

import com.github.PierreAdam.javadatatables.core.interfaces.RowExtraData;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.function.Function;

/**
 * RowExtraDataImpl.
 *
 * @param <E> the type parameter
 * @author Pierre Adam
 * @since 25.03.13
 */
@Getter
@Setter
public class RowExtraDataImpl<E> implements RowExtraData<E> {

    /**
     * The Row id.
     */
    protected Function<E, String> rowId;

    /**
     * The Row class.
     */
    protected Function<E, String> rowClass;

    /**
     * The Row data.
     */
    protected Function<E, Object> rowData;

    /**
     * The Row attr.
     */
    protected Function<E, Object> rowAttr;

    /**
     * Instantiates a new Row extra data.
     */
    public RowExtraDataImpl() {
        this.rowId = null;
        this.rowClass = null;
        this.rowData = null;
        this.rowAttr = null;
    }

    /**
     * Checks if any of the fields are present (not null).
     *
     * @return true if any field is not null, false otherwise
     */
    public boolean present() {
        return this.rowId != null || this.rowClass != null || this.rowData != null || this.rowAttr != null;
    }


    /**
     * Gets the rowId as an Optional.
     *
     * @return Optional containing rowId if present, or an empty Optional
     */
    public Optional<Function<E, String>> getOptionalRowId() {
        return Optional.ofNullable(this.rowId);
    }

    /**
     * Gets the rowClass as an Optional.
     *
     * @return Optional containing rowClass if present, or an empty Optional
     */
    public Optional<Function<E, String>> getOptionalRowClass() {
        return Optional.ofNullable(this.rowClass);
    }

    /**
     * Gets the rowData as an Optional.
     *
     * @return Optional containing rowData if present, or an empty Optional
     */
    public Optional<Function<E, Object>> getOptionalRowData() {
        return Optional.ofNullable(this.rowData);
    }

    /**
     * Gets the rowAttr as an Optional.
     *
     * @return Optional containing rowAttr if present, or an empty Optional
     */
    public Optional<Function<E, Object>> getOptionalRowAttr() {
        return Optional.ofNullable(this.rowAttr);
    }
}
