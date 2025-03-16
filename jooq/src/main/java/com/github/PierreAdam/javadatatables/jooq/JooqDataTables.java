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

package com.github.PierreAdam.javadatatables.jooq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import org.jooq.DSLContext;

/**
 * JooqDataTables.
 *
 * @param <E> the type parameter
 * @author Pierre Adam
 * @since 25.03.10
 */
public class JooqDataTables<E> extends AdvancedJooqDataTables<E, JooqDataTablesContext> {

    /**
     * Instantiates a new Jooq data tables.
     *
     * @param entityClass  the entity class
     * @param objectMapper the object mapper
     * @param dslContext   the dsl context
     */
    public JooqDataTables(final Class<E> entityClass, final ObjectMapper objectMapper, final DSLContext dslContext) {
        super(entityClass, objectMapper, dslContext);
    }

    @Override
    public JsonNode getAjaxResult(final Parameters parameters) {
        return super.getAjaxResult(parameters, new JooqDataTablesContext(this.dslContext));
    }
}
