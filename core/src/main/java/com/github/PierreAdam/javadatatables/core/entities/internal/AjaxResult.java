/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.entities.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Getter;
import lombok.Setter;

/**
 * AjaxResult.
 *
 * @author Pierre Adam
 * @since 21.03.01
 */
@Getter
@Setter
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
}
