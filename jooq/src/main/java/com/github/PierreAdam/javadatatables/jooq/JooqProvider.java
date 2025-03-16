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

import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;
import lombok.Getter;
import lombok.Setter;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * The type Jooq provider.
 *
 * @author Pierre Adam
 * @since 25.03.10
 */
public class JooqProvider {

    /**
     * The Dsl context.
     */
    @Getter
    private final DSLContext dslContext;

    /**
     * The Conditions.
     */
    private final List<Condition> conditions;


    /**
     * The Sorting.
     */
    private final List<OrderField<?>> sorting;

    /**
     * The Initial select.
     */
    @Setter
    private Function<DSLContext, SelectSelectStep<? extends Record>> initialSelect;

    /**
     * The Initial from.
     */
    @Setter
    private Function<SelectSelectStep<? extends Record>, SelectWhereStep<? extends Record>> initialFrom;

    /**
     * The Initial condition.
     */
    @Setter
    private Condition initialCondition;

    /**
     * The Sorting.
     */
    @Setter
    private GroupField[] groupBy;

    /**
     * The Start element.
     */
    @Setter
    private int startElement;

    /**
     * The Number of element.
     */
    @Setter
    private int numberOfElement;

    /**
     * Instantiates a new Jooq provider.
     *
     * @param dslContext the dsl context
     */
    public JooqProvider(final DSLContext dslContext) {
        this.dslContext = dslContext;
        this.startElement = 0;
        this.numberOfElement = 10;
        this.initialSelect = null;
        this.initialFrom = null;
        this.initialCondition = null;
        this.groupBy = null;
        this.conditions = new ArrayList<>();
        this.sorting = new ArrayList<>();
    }

    /**
     * Add condition jooq provider.
     *
     * @param condition the condition
     * @return the jooq provider
     */
    public JooqProvider addCondition(final Condition condition) {
        this.conditions.add(condition);
        return this;
    }

    /**
     * Add sort jooq provider.
     *
     * @param orderField the order field
     * @return the jooq provider
     */
    public JooqProvider addSort(final OrderField<?> orderField) {
        this.sorting.add(orderField);
        return this;
    }

    /**
     * Add sort jooq provider.
     *
     * @param field     the field
     * @param orderEnum the order enum
     * @return the jooq provider
     */
    public JooqProvider addSort(final Field<?> field, final OrderEnum orderEnum) {
        if (orderEnum == OrderEnum.DESC) {
            this.addSort(field.desc());
        } else {
            this.addSort(field.asc());
        }

        return this;
    }

    /**
     * Gets as forged query.
     *
     * @return the as forged query
     */
    public SelectForUpdateStep<? extends Record> getAsForgedQuery() {
        final SelectConditionStep<? extends Record> where = this.initialFrom
                .apply(this.initialSelect.apply(this.dslContext))
                .where(this.getWhereCondition());
        final SelectHavingStep<? extends Record> postGroupBy = this.groupBy != null ? where.groupBy(this.groupBy) : where;

        return postGroupBy
                .orderBy(this.sorting)
                .limit(this.numberOfElement)
                .offset(this.startElement);
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public List<? extends Record> getResult() {
        return this.getAsForgedQuery().fetch();
    }

    /**
     * Gets total unfiltered result count.
     *
     * @return the total unfiltered result count
     */
    public long getTotalUnfilteredResultCount() {
        final Field<Integer> countField = DSL.field("count", Integer.class);
        final Integer count = this.initialFrom
                .apply(this.dslContext.select(DSL.count().as(countField)))
                .where(this.getWhereInitialCondition())
                .fetchOne(countField);
        return count == null ? 0L : count;
    }

    /**
     * Gets total filtered result count.
     *
     * @return the total filtered result count
     */
    public long getTotalFilteredResultCount() {
        final Field<Integer> countField = DSL.field("count", Integer.class);
        final Integer count = this.initialFrom
                .apply(this.dslContext.select(DSL.count().as(countField)))
                .where(this.getWhereCondition())
                .fetchOne(countField);
        return count == null ? 0L : count;
    }

    /**
     * Gets where initial condition.
     *
     * @return the where initial condition
     */
    private List<Condition> getWhereInitialCondition() {
        final List<Condition> where = new ArrayList<>();

        if (this.initialCondition != null) {
            where.add(this.initialCondition);
        }

        return where;
    }

    /**
     * Gets where condition.
     *
     * @return the where condition
     */
    private List<Condition> getWhereCondition() {
        final List<Condition> where = this.getWhereInitialCondition();

        where.addAll(this.conditions);

        return where;
    }
}
