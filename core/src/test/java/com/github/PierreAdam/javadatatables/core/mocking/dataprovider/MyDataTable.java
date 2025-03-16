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

package com.github.PierreAdam.javadatatables.core.mocking.dataprovider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.internal.DataSource;
import com.github.PierreAdam.javadatatables.core.implementations.SimpleDataTables;
import com.github.PierreAdam.javadatatables.testdata.PersonEntity;

import java.util.List;

/**
 * MyDataProvider.
 *
 * @author Pierre Adam
 * @since 21.03.02
 */
public class MyDataTable extends SimpleDataTables<PersonEntity, DummyProvider, Object, MyDataTable> {

    /**
     * Instantiates a new My data table.
     */
    public MyDataTable() {
        super(PersonEntity.class, new ObjectMapper(), DummyProvider::new);
    }

    @Override
    protected MyDataTable setPagination(final DummyProvider dummyProvider, final int startElement, final int numberOfElement) {
        dummyProvider.setPagination(startElement, numberOfElement);

        return this;
    }

    @Override
    protected DataSource<PersonEntity> dataSourceFromProvider(final DummyProvider dummyProvider, final Object payload) {
        final List<PersonEntity> result = dummyProvider.getResult();
        return new DataSource<>(dummyProvider.getInitialSize(), result.size(), result);
    }
}