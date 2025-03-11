/*
 * MIT License
 *
 * Copyright (c) 2021 Pierre Adam
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.AjaxQueryForm;
import com.github.PierreAdam.javadatatables.core.entities.Column;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import com.github.PierreAdam.javadatatables.core.entities.Search;
import com.github.PierreAdam.javadatatables.core.entities.internal.DataSource;
import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;
import com.github.PierreAdam.javadatatables.core.tools.ResourcesLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FormEntitiesTest.
 *
 * @author Pierre Adam
 * @since 21.03.31
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntitiesTest {

    /**
     * Validate the Parameters logic.
     */
    @Test
    @Order(0)
    public void parametersLogic() {
        final Parameters parameters = new Parameters();

        Assertions.assertFalse(parameters.getOptionalGlobalSearch().isPresent());
        Assertions.assertNotNull(parameters.getSafeColumns());
        Assertions.assertNotNull(parameters.getSafeOrder());

        parameters.setSearch(new Search());
        Assertions.assertFalse(parameters.getOptionalGlobalSearch().isPresent());

        parameters.getSearch().setValue("");
        Assertions.assertFalse(parameters.getOptionalGlobalSearch().isPresent());

        parameters.getSearch().setValue("something");
        Assertions.assertTrue(parameters.getOptionalGlobalSearch().isPresent());
    }

    /**
     * Validate the Column logic.
     */
    @Test
    @Order(1)
    public void columnLogic() {
        final Column column = new Column();

        Assertions.assertFalse(column.hasSearch());

        column.setSearch(new Search());
        Assertions.assertFalse(column.hasSearch());

        column.getSearch().setValue("");
        Assertions.assertFalse(column.hasSearch());

        column.getSearch().setValue("something");
        Assertions.assertTrue(column.hasSearch());
    }

    /**
     * Validate the Order entity.
     */
    @Test
    @Order(2)
    public void orderLogic() {
        final com.github.PierreAdam.javadatatables.core.entities.Order order = new com.github.PierreAdam.javadatatables.core.entities.Order();

        order.setDir("asc");
        Assertions.assertEquals(OrderEnum.ASC, order.getOrder());
        order.setDir("AsC");
        Assertions.assertEquals(OrderEnum.ASC, order.getOrder());
        order.setDir("desc");
        Assertions.assertEquals(OrderEnum.DESC, order.getOrder());
        order.setDir("DeSc");
        Assertions.assertEquals(OrderEnum.DESC, order.getOrder());
        order.setDir("invalid");
        Assertions.assertEquals(OrderEnum.ASC, order.getOrder());
    }

    /**
     * Validate the Order entity.
     */
    @Test
    @Order(3)
    public void dataSourceLogic() {
        final DataSource<Integer> integerDataSource = new DataSource<>();

        integerDataSource.setEntities(Arrays.asList(1, 2, 3));
        integerDataSource.setRecordsFiltered(50);
        integerDataSource.setRecordsTotal(80);

        Assertions.assertEquals(3, integerDataSource.getEntities().size());
        Assertions.assertEquals(50, integerDataSource.getRecordsFiltered());
        Assertions.assertEquals(80, integerDataSource.getRecordsTotal());
    }

    /**
     * Load simple json.
     */
    @Test
    @Order(10)
    public void loadSimpleJson() {
        final JsonNode jsonNode = ResourcesLoader.loadBody("test_data/form_entities/simple.json");

        Assertions.assertNotNull(jsonNode);
        final AjaxQueryForm form = Assertions.assertDoesNotThrow(() ->
                new ObjectMapper().convertValue(jsonNode, AjaxQueryForm.class));

        Assertions.assertNotNull(form);
        final Parameters parameters = form.getParameters();
        Assertions.assertNotNull(parameters);
        Assertions.assertNotNull(parameters.getColumns());
        Assertions.assertNotNull(parameters.getOrder());
        Assertions.assertEquals(0, parameters.getStart());
        Assertions.assertEquals(10, parameters.getLength());
        Assertions.assertEquals(5, parameters.getDraw());
        Assertions.assertNull(parameters.getSearch());
        Assertions.assertFalse(parameters.getOptionalGlobalSearch().isPresent());
        Assertions.assertFalse(parameters.getColumns().isEmpty());
        Assertions.assertFalse(parameters.getOrder().isEmpty());
        parameters.getColumns().forEach(column -> {
            Assertions.assertNotNull(column.getName());
            Assertions.assertNull(column.getSearch());
        });
    }

    /**
     * Load complete json.
     */
    @Test
    @Order(11)
    public void loadCompleteJson() {
        final JsonNode jsonNode = ResourcesLoader.loadBody("test_data/form_entities/complete.json");

        Assertions.assertNotNull(jsonNode);
        final AjaxQueryForm form = Assertions.assertDoesNotThrow(() ->
                new ObjectMapper().convertValue(jsonNode, AjaxQueryForm.class));

        Assertions.assertNotNull(form);
        final Parameters parameters = form.getParameters();
        Assertions.assertNotNull(parameters);
        Assertions.assertNotNull(parameters.getColumns());
        Assertions.assertNotNull(parameters.getOrder());
        Assertions.assertEquals(0, parameters.getStart());
        Assertions.assertEquals(10, parameters.getLength());
        Assertions.assertEquals(42, parameters.getDraw());
        Assertions.assertNotNull(parameters.getSearch());
        Assertions.assertTrue(parameters.getOptionalGlobalSearch().isPresent());
        Assertions.assertFalse(parameters.getColumns().isEmpty());
        Assertions.assertFalse(parameters.getOrder().isEmpty());
        parameters.getColumns().forEach(column -> {
            Assertions.assertNotNull(column.getName());
            if (column.getData() > 0) {
                Assertions.assertNotNull(column.getSearch());
                Assertions.assertTrue(column.hasSearch());
            } else {
                Assertions.assertFalse(column.hasSearch());
                Assertions.assertNull(column.getSearch());
            }
        });

        final Map<Integer, Column> indexedColumns = parameters.getIndexedColumns();
        Assertions.assertEquals(3, indexedColumns.size());
        Assertions.assertNotNull(indexedColumns.get(0));
        Assertions.assertNotNull(indexedColumns.get(1));
        Assertions.assertNotNull(indexedColumns.get(2));

        final List<Column> orderedColumns = parameters.getOrderedColumns();
        for (int i = 0; i < orderedColumns.size(); i++) {
            Assertions.assertEquals(indexedColumns.get(i), orderedColumns.get(i));
        }
    }

    /**
     * Load complete json.
     */
    @Test
    @Order(12)
    public void loadInvalidJson() {
        final JsonNode jsonNode = ResourcesLoader.loadBody("test_data/form_entities/invalid.json");

        Assertions.assertNotNull(jsonNode);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new ObjectMapper().convertValue(jsonNode, AjaxQueryForm.class));


//        final Application application = Helpers.fakeApplication();
//        final FormFactory formFactory = application.injector().instanceOf(FormFactory.class);
//        Assertions.assertNotNull(formFactory);
//
//        final Http.Request fakeRequest = Helpers.fakeRequest("POST", "https://localhost:9000/datatables")
//                .transientLang(Locale.ENGLISH)
//                .bodyJson(ResourcesLoader.loadBody("testdata/formentities/invalid.json"))
//                .build();
//
//        Assertions.assertEquals("Success",
//                this.dataTablesAjaxRequest(fakeRequest, formFactory, boundForm -> {
//                    return "Success";
//                }, form -> {
//                    Assertions.fail("The form shouldn't have worked !");
//                    return "Fail";
//                })
//        );
    }

    /**
     * Load complete json.
     */
    @Test
    @Order(13)
    public void loadWeirdJson() {
        final JsonNode jsonNode = ResourcesLoader.loadBody("test_data/form_entities/weird.json");

        Assertions.assertNotNull(jsonNode);
        final AjaxQueryForm form = Assertions.assertDoesNotThrow(() ->
                new ObjectMapper().convertValue(jsonNode, AjaxQueryForm.class));

        Assertions.assertNotNull(form);
        final Parameters parameters = form.getParameters();
        Assertions.assertNotNull(parameters);
        Assertions.assertNull(parameters.getColumns());
        Assertions.assertNull(parameters.getOrder());
        Assertions.assertEquals(0, parameters.getStart());
        Assertions.assertEquals(10, parameters.getLength());
        Assertions.assertEquals(1, parameters.getDraw());
        Assertions.assertNotNull(parameters.getSearch());
        Assertions.assertFalse(parameters.getOptionalGlobalSearch().isPresent());
        parameters.getSearch().setValue("");
        Assertions.assertTrue(parameters.getSafeColumns().isEmpty());
        Assertions.assertTrue(parameters.getSafeOrder().isEmpty());
    }
}
