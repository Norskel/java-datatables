package com.github.PierreAdam.javadatatables.core.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.PierreAdam.javadatatables.core.interfaces.RowExtraData;
import lombok.Getter;
import lombok.Setter;

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
    protected Function<E, JsonNode> rowData;

    /**
     * The Row attr.
     */
    protected Function<E, JsonNode> rowAttr;

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
}
