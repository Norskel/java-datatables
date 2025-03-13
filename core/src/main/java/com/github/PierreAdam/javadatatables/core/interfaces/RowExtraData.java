package com.github.PierreAdam.javadatatables.core.interfaces;

import com.fasterxml.jackson.databind.JsonNode;

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
    RowExtraData<E> setRowData(Function<E, JsonNode> rowDataSupplier);

    /**
     * Sets row attr.
     *
     * @param rowAttrSupplier the row attr supplier
     * @return the row attr
     */
    RowExtraData<E> setRowAttr(Function<E, JsonNode> rowAttrSupplier);
}
