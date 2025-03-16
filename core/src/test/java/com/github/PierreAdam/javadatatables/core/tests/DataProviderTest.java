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

package com.github.PierreAdam.javadatatables.core.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.PierreAdam.javadatatables.core.converters.standards.DateTimeConverter;
import com.github.PierreAdam.javadatatables.core.entities.Column;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import com.github.PierreAdam.javadatatables.core.entities.Search;
import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;
import com.github.PierreAdam.javadatatables.core.mocking.dataprovider.AddressConverter;
import com.github.PierreAdam.javadatatables.core.mocking.dataprovider.DummyProvider;
import com.github.PierreAdam.javadatatables.core.mocking.dataprovider.MyDataTable;
import com.github.PierreAdam.javadatatables.core.tools.ParametersHelper;
import com.github.PierreAdam.javadatatables.testdata.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * tests.TestDataProvider.
 *
 * @author Pierre Adam
 * @since 21.03.02
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataProviderTest {

    /**
     * The My data table.
     */
    private MyDataTable myDataTable;

    @BeforeEach
    void setUp() {
        final Function<Function<PersonEntity, String>, BiConsumer<DummyProvider, OrderEnum>> orderHandler = method ->
                (dummyProvider, orderEnum) ->
                        dummyProvider.alterData(list -> {
                            list.sort((o1, o2) ->
                                    method.apply(o1).compareTo(method.apply(o2)) * (orderEnum == OrderEnum.ASC ? 1 : -1));
                            return list;
                        });

        this.myDataTable = new MyDataTable()
                .setInitProviderConsumer(DummyProvider::initializeData).setGlobalSearchHandler((dummyProvider, search) ->
                        dummyProvider.alterData(list -> list.stream().filter(entity ->
                                String.format("%s %s", entity.getFirstName(), entity.getLastName()).contains(search)).collect(Collectors.toList()))
                )

                .setSearchHandler("firstName", (dummyProvider, search) ->
                        dummyProvider.alterData(list -> list.stream().filter(entity -> entity.getFirstName().contains(search)).collect(Collectors.toList()))
                )
                .setSearchHandler("bloodGroup", (dummyProvider, search) ->
                        dummyProvider.alterData(list -> list
                                .stream()
                                .filter(entity -> entity.getBloodGroup().contains(search))
                                .collect(Collectors.toList())
                        )
                )

                .setOrderHandler("firstName", orderHandler.apply(PersonEntity::getFirstName))
                .setOrderHandler("lastName", orderHandler.apply(PersonEntity::getLastName))
                .setOrderHandler("title", orderHandler.apply(PersonEntity::getTitle))
                .setOrderHandler("bloodGroup", orderHandler.apply(PersonEntity::getBloodGroup))

                .setFieldDisplaySupplier("fullName", (entity, context) ->
                        String.format("Hello %s %s", entity.getFirstName(), entity.getLastName()))
                .setFieldDisplaySupplier("simpleEnum", personEntity -> personEntity.getSimpleEnum().name())
                .setFieldDisplaySupplier("doubleNumber", personEntity ->
                        String.format("<b>%.2f<b/>", personEntity.getDoubleNumber()))
                .setFieldDisplaySupplier("actions", (personEntity, context) ->
                        String.format("<a href=\"#%s\">Delete</a>", personEntity.getUid().toString()))

                .addConverter(new AddressConverter());
    }

    /**
     * Check dummy query.
     */
    @Test
    @Order(1)
    public void checkDummyQuery() {
        final DummyProvider querySource = new DummyProvider();
        querySource.initializeData();
        final List<PersonEntity> data = querySource.getData();

        for (final PersonEntity entity : data) {
            DataProviderTest.logger.trace("Entry : {}", entity.toString());
        }

        // Check if the data has been correctly generated.
        Assertions.assertEquals(DummyProvider.SAMPLE_SIZE, data.size());
    }

    /**
     * Pagination.
     */
    @Test
    @Order(2)
    public void pagination() {
        final Random random = new Random();
        final Parameters parameters = ParametersHelper.createForNameEntity();
        parameters.setDraw(random.nextInt());

        final JsonNode ajaxResult = this.myDataTable.getAjaxResult(parameters);

        DataProviderTest.logger.trace("{}", ajaxResult.toPrettyString());

        // Validating the returned data.
        Assertions.assertTrue(ajaxResult.has("data"));
        Assertions.assertEquals(parameters.getDraw(), ajaxResult.get("draw").asInt());
        Assertions.assertEquals(DummyProvider.SAMPLE_SIZE, ajaxResult.get("recordsTotal").asInt());
        Assertions.assertEquals(parameters.getLength(), ajaxResult.get("recordsFiltered").asInt());

        final JsonNode data = ajaxResult.get("data");
        Assertions.assertTrue(data.isArray());
        Assertions.assertEquals(parameters.getLength(), data.size());

        // Retrieve the first line and check if the json is correctly formatted and match the given parameters.
        final JsonNode line = data.get(0);
        Assertions.assertNotNull(line);
        Assertions.assertTrue(line.isArray());
        Assertions.assertEquals(parameters.getColumns().size(), line.size());

        // Check that the elements on the first line are well populated.
        parameters.getIndexedColumns().forEach((idx, column) -> {
            final JsonNode node = line.get(idx);
            if (column.getName().contains("null")) {
                Assertions.assertTrue(node.isNull());
            } else {
                Assertions.assertNotNull(node.asText());
                Assertions.assertFalse(node.isNull());
            }
        });
    }

    /**
     * Search.
     */
    @Test
    @Order(3)
    public void search() {
        final Parameters parameters = ParametersHelper.createForNameEntity();

        final Column bloodTypeColumn = parameters.getColumns().get(6);
        final Search search = new Search();
        search.setValue("A+");
        bloodTypeColumn.setSearch(search);

        final JsonNode ajaxResult = this.myDataTable.getAjaxResult(parameters);

        DataProviderTest.logger.trace("{}", ajaxResult.toPrettyString());

        final JsonNode data = ajaxResult.get("data");
        Assertions.assertTrue(data.isArray());
        for (final JsonNode node : data) {
            Assertions.assertEquals("A+", node.get(6).asText());
        }
    }

    /**
     * Search.
     */
    @Test
    @Order(4)
    public void globalSearch() {
        final Parameters parameters = ParametersHelper.createForNameEntity();
        final String searchValue = "a";

        final Search search = new Search();
        search.setValue(searchValue);
        parameters.setSearch(search);

        final JsonNode ajaxResult = this.myDataTable.getAjaxResult(parameters);

        DataProviderTest.logger.trace("{}", ajaxResult.toPrettyString());

        final JsonNode data = ajaxResult.get("data");
        Assertions.assertTrue(data.isArray());
        for (final JsonNode node : data) {
            // Test that the firstname or lastname contains the search
            Assertions.assertTrue(node.get(2).asText().contains(searchValue) || node.get(3).asText().contains(searchValue));
        }
    }

    /**
     * Test ASC ordering.
     */
    @Test
    @Order(5)
    public void orderAsc() {
        this.order(OrderEnum.ASC, diff -> diff >= 0);
    }

    /**
     * Test DESC ordering.
     */
    @Test
    @Order(6)
    public void orderDesc() {
        this.order(OrderEnum.DESC, diff -> diff <= 0);
    }

    /**
     * Ordering logic.
     *
     * @param orderEnum  the order
     * @param comparator the test
     */
    private void order(final OrderEnum orderEnum, final Function<Integer, Boolean> comparator) {
        final Parameters parameters = ParametersHelper.createForNameEntity();
        final List<com.github.PierreAdam.javadatatables.core.entities.Order> orders = new ArrayList<>();
        final com.github.PierreAdam.javadatatables.core.entities.Order order = new com.github.PierreAdam.javadatatables.core.entities.Order();

        final int columnIdx = 2;
        order.setColumn(columnIdx); // First name column
        order.setDir(orderEnum.toString().toLowerCase());
        orders.add(order);
        parameters.setOrder(orders);
        parameters.setLength(100);

        final JsonNode ajaxResult = this.myDataTable.getAjaxResult(parameters);

        final JsonNode data = ajaxResult.get("data");
        Assertions.assertTrue(data.isArray());
        String previous = data.get(0).get(columnIdx).asText();
        for (final JsonNode node : data) {
            final String firstName = node.get(columnIdx).asText();
            final int t = firstName.compareTo(previous);

            DataProviderTest.logger.trace("{} <=> {} == {}", previous, firstName, t);

            Assertions.assertTrue(comparator.apply(t)); // Check if the ordering is correct.
            previous = firstName;
        }
    }

    /**
     * Datetime with context.
     */
    @Test
    @Order(7)
    public void datetimeWithContext() {
        final Random random = new Random();
        final Parameters parameters = ParametersHelper.createForNameEntity();
        parameters.setDraw(random.nextInt());

        this.myDataTable.addConverter(new DateTimeConverter(DateTimeFormatter.ofPattern("yyyy-MM")));

        final JsonNode ajaxResult = this.myDataTable.getAjaxResult(parameters);

        DataProviderTest.logger.trace("{}", ajaxResult.toPrettyString());

        // Validating the returned data.
        Assertions.assertTrue(ajaxResult.has("data"));
        Assertions.assertEquals(parameters.getDraw(), ajaxResult.get("draw").asInt());
        Assertions.assertEquals(DummyProvider.SAMPLE_SIZE, ajaxResult.get("recordsTotal").asInt());
        Assertions.assertEquals(parameters.getLength(), ajaxResult.get("recordsFiltered").asInt());

        final JsonNode data = ajaxResult.get("data");
        Assertions.assertTrue(data.isArray());
        Assertions.assertEquals(parameters.getLength(), data.size());

        // Retrieve the first line and check if the date is correctly formatted
        final JsonNode line = data.get(0);
        final String date = line.get(0).asText();

        Assertions.assertNotNull(date);
        Assertions.assertTrue(date.matches("[0-9]{4}-[0-1][0-9]"));
        DataProviderTest.logger.trace(date);
    }

    /**
     * Missing field.
     */
    @Test
    @Order(7)
    public void missingField() {
        final Random random = new Random();
        final Parameters parameters = ParametersHelper.createForNameEntity();
        parameters.setDraw(random.nextInt());
        parameters.getColumns().clear();
        final ParametersHelper.ColumnFactory columnFactory = new ParametersHelper.ColumnFactory(parameters.getColumns());
        columnFactory.addColumn("missingGetter");

        final JsonNode ajaxResult = this.myDataTable.getAjaxResult(parameters);

        DataProviderTest.logger.trace("{}", ajaxResult.toPrettyString());

        // Validating the returned data.
        Assertions.assertTrue(ajaxResult.has("data"));
        Assertions.assertEquals(parameters.getDraw(), ajaxResult.get("draw").asInt());
        Assertions.assertEquals(DummyProvider.SAMPLE_SIZE, ajaxResult.get("recordsTotal").asInt());
        Assertions.assertEquals(parameters.getLength(), ajaxResult.get("recordsFiltered").asInt());

        final JsonNode data = ajaxResult.get("data");
        Assertions.assertTrue(data.isArray());
        Assertions.assertEquals(parameters.getLength(), data.size());

        // Retrieve the first line and check if the date is correctly formatted
        final JsonNode line = data.get(0);
        Assertions.assertTrue(line.isArray());
        Assertions.assertEquals(1, line.size());
        Assertions.assertTrue(line.get(0).isNull());
    }
}
