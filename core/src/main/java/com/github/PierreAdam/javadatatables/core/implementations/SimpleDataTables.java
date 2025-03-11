/*
 * Copyright (C) 2014 - 2023 PayinTech, SAS - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.github.PierreAdam.javadatatables.core.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.PierreAdam.javadatatables.core.configs.JavaDataTablesConfig;
import com.github.PierreAdam.javadatatables.core.converters.Converter;
import com.github.PierreAdam.javadatatables.core.entities.Column;
import com.github.PierreAdam.javadatatables.core.entities.Parameters;
import com.github.PierreAdam.javadatatables.core.entities.internal.AjaxResult;
import com.github.PierreAdam.javadatatables.core.entities.internal.DataSource;
import com.github.PierreAdam.javadatatables.core.entities.internal.FieldBehavior;
import com.github.PierreAdam.javadatatables.core.enumerations.OrderEnum;
import com.github.PierreAdam.javadatatables.core.interfaces.DataTables;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * APlayDataTables.
 *
 * @param <E> the Entity type
 * @param <S> the Source Provider type
 * @param <C> the Context type
 * @param <U> the type parameter
 * @author Pierre Adam
 * @since 21.03.01
 */
public abstract class SimpleDataTables<E, S, C, U extends SimpleDataTables<E, S, C, U>>
        extends CustomizableDataTables<E, S, C, U>
        implements DataTables<E, S, C, U> {

    /**
     * The potential prefixes of the getter in the target classes.
     */
    protected static final String[] METHOD_PREFIXES = {"get", "is", "has", "can"};

    /**
     * The Logger.
     */
    protected final Logger logger;

    /**
     * The Entity class.
     */
    protected final Class<E> entityClass;

    /**
     * The Object mapper.
     */
    protected final ObjectMapper objectMapper;

    /**
     * The fields specific behavior.
     * If set for a given field, the supplier, search handler or order handler might be used.
     */
    protected final Map<String, FieldBehavior<E, S, C>> fieldsBehavior;

    /**
     * The initial provider supplier allows you to create your own initial provider.
     */
    protected final Supplier<S> providerSupplier;

    /**
     * The instance specific converters.
     */
    private final Map<Class<?>, Converter<?>> converters;

    /**
     * The global search supplier. If set, the handler will be called when a search not specific to a field is required.
     */
    protected BiConsumer<S, String> globalSearchHandler;

    /**
     * Initialize the provider object if needed. Is called on each forged request.
     */
    protected Consumer<S> initProviderConsumer;

    /**
     * Instantiates a new A play data tables.
     *
     * @param entityClass      the entity class
     * @param objectMapper     the object mapper
     * @param providerSupplier the query supplier
     */
    public SimpleDataTables(final Class<E> entityClass, final ObjectMapper objectMapper, final Supplier<S> providerSupplier) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.entityClass = entityClass;
        this.objectMapper = objectMapper;
        this.providerSupplier = providerSupplier;

        this.fieldsBehavior = new HashMap<>();
        this.converters = new HashMap<>();
        this.globalSearchHandler = null;
    }

    @Override
    public U setInitProviderConsumer(final Consumer<S> initQueryConsumer) {
        this.initProviderConsumer = initQueryConsumer;

        return this.asSelf();
    }

    @Override
    public U setGlobalSearchHandler(final BiConsumer<S, String> globalSearchHandler) {
        this.globalSearchHandler = globalSearchHandler;

        return this.asSelf();
    }

    @Override
    public JsonNode getAjaxResult(final Parameters parameters, final C context) {
        // Prepare data from the parameters and prepare the answer.
        final AjaxResult result = new AjaxResult(parameters.getDraw(), this.objectMapper);
        final S provider = this.internalForgeInitialProvider(this.providerSupplier);

        if (this.initProviderConsumer != null) {
            this.initProviderConsumer.accept(provider);
        }

        final DataSource<E> source = this.processProvider(provider, context, parameters);

        for (final E entity : source.getEntities()) {
            result.getData().add(this.objectToArrayNode(entity, parameters, context));
        }

        result.setRecordsTotal(source.getRecordsTotal());
        result.setRecordsFiltered(source.getRecordsFiltered());

        return this.objectMapper.valueToTree(result);
    }

    /**
     * Process provider data source.
     *
     * @param provider   the provider
     * @param context    the context
     * @param parameters the parameters
     * @return the data source
     */
    protected DataSource<E> processProvider(final S provider, final C context, final Parameters parameters) {
        // Set the pagination on the provider.
        this.setPagination(provider, parameters.getStart(), parameters.getLength());

        this.preSearchHook(provider, context, parameters);
        this.applySearch(provider, parameters);
        this.postSearchHook(provider, context, parameters);

        this.preOrderHook(provider, context, parameters);
        this.applyOrder(provider, parameters);
        this.postOrderHook(provider, context, parameters);

        return this.dataSourceFromProvider(provider, context);
    }

    /**
     * Apply search.
     *
     * @param provider   the provider
     * @param parameters the parameters
     */
    private void applySearch(final S provider, final Parameters parameters) {
        // Process global search.
        parameters.getOptionalGlobalSearch().ifPresent(search -> {
            if (this.globalSearchHandler != null) {
                this.globalSearchHandler.accept(provider, parameters.getSearch().getValue());
            } else {
                this.logger.warn("A global search has been asked for but the global search handler is null. setGlobalSearchHandler needs to be called.");
            }
        });

        // Process Column search.
        parameters.getSafeColumns()
                .stream()
                .filter(column -> column != null && column.hasSearch())
                .forEach(column -> {
                    final String columnName = column.getName();
                    final Optional<BiConsumer<S, String>> optionalSearchHandler = this.field(columnName).getSearchHandler();

                    if (optionalSearchHandler.isPresent()) {
                        optionalSearchHandler.get().accept(provider, column.getSearch().getValue());
                    } else {
                        this.fallbackSearchHandler(provider, columnName, column.getSearch().getValue());
                    }
                });
    }

    /**
     * Apply order.
     *
     * @param provider   the provider
     * @param parameters the parameters
     */
    private void applyOrder(final S provider, final Parameters parameters) {
        final Map<Integer, Column> indexedColumns = parameters.getIndexedColumns();

        parameters.getSafeOrder().forEach(order -> {
            final String columnName = indexedColumns.get(order.getColumn()).getName();
            final Optional<BiConsumer<S, OrderEnum>> optionalOrderHandler = this.field(columnName).getOrderHandler();

            if (optionalOrderHandler.isPresent()) {
                optionalOrderHandler.get().accept(provider, order.getOrder());
            } else {
                this.fallbackOrderHandler(provider, columnName, order.getOrder());
            }
        });
    }

    /**
     * Internal forge initial query query.
     *
     * @param initialProviderSupplier the initial query supplier
     * @return the query
     */
    protected S internalForgeInitialProvider(final Supplier<S> initialProviderSupplier) {
        return initialProviderSupplier.get();
    }

    /**
     * Convert an object to an Array node using the indexed columns.
     *
     * @param entity     the object
     * @param parameters the parameters
     * @param context    the context
     * @return the array node
     */
    protected JsonNode objectToArrayNode(final E entity, final Parameters parameters, final C context) {
        final ArrayNode data = this.objectMapper.createArrayNode();

        parameters.getOrderedColumns().forEach(column -> {
            if (column == null) {
                data.addNull();
            } else {
                final Optional<BiFunction<E, C, String>> optionalDisplaySupplier = this.field(column.getName()).getDisplaySupplier();

                if (optionalDisplaySupplier.isPresent()) {
                    data.add(optionalDisplaySupplier.get().apply(entity, context));
                } else {
                    this.resolveColumn(column, data, entity, context);
                }
            }
        });

        return data;
    }

    /**
     * Resolve column.
     *
     * @param column  the column
     * @param data    the data
     * @param entity  the entity
     * @param context the context
     */
    protected void resolveColumn(final Column column, final ArrayNode data, final E entity, final C context) {
        final Method method = this.methodForColumn(column);

        if (method == null) {
            this.logger.warn("No getter were find for the field \"{}\" and no displaySupplier were set. Adding null !",
                    column.getName());
            data.addNull();
            return;
        }

        try {
            final Object obj = method.invoke(entity);
            final Converter<?> converter = this.tryFindConverter(obj);

            if (converter != null) {
                converter.addToArray(data, obj, context);
            } else {
                data.addNull();
            }
        } catch (final IllegalAccessException | InvocationTargetException e) {
            data.addNull();
        }
    }

    /**
     * Try to find a converter for the given object.
     *
     * @param obj the obj
     * @return the converter
     */
    protected Converter<?> tryFindConverter(final Object obj) {
        if (obj == null) {
            return null;
        }

        Converter<?> converter = null;

        // Try getting a converter with the instance converters.
        converter = this.getConverterFromMap(obj, this.converters);

        if (converter == null) {
            // Try getting a converter from the global converters.
            converter = this.getConverterFromMap(obj, JavaDataTablesConfig.getInstance().getConverters());
        }

        return converter;
    }

    /**
     * Gets converter.
     *
     * @param obj        the obj
     * @param converters the converters
     * @return the converter
     */
    protected Converter<?> getConverterFromMap(final Object obj, final Map<Class<?>, Converter<?>> converters) {
        final Class<?> objClass = obj.getClass();

        if (converters.containsKey(objClass)) {
            // If the class is explicitly added with a converter.
            return converters.get(objClass);
        }

        // If a converter could match with a sub-type.
        for (final Map.Entry<Class<?>, Converter<?>> entry : converters.entrySet()) {
            if (entry.getKey().isAssignableFrom(objClass)) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Try to solve a getter for a given column.
     *
     * @param column the column
     * @return the method or null
     */
    protected Method methodForColumn(final Column column) {
        for (final String methodPrefix : SimpleDataTables.METHOD_PREFIXES) {
            try {
                return this.entityClass.getMethod(methodPrefix + StringUtils.capitalize(column.getName()));
            } catch (final NoSuchMethodException ignore) {
            }
        }

        try {
            return this.entityClass.getMethod(column.getName());
        } catch (final NoSuchMethodException ignore) {
        }

        return null;
    }

    @Override
    public FieldBehavior<E, S, C> field(final String fieldName) {
        if (!this.fieldsBehavior.containsKey(fieldName)) {
            this.setField(fieldName, new FieldBehavior<>());
        }
        return this.fieldsBehavior.get(fieldName);
    }

    @Override
    public U setField(final String fieldName, final FieldBehavior<E, S, C> fieldBehavior) {
        this.fieldsBehavior.put(fieldName, fieldBehavior);

        return this.asSelf();
    }

    @Override
    public <T> U addConverter(final Converter<T> converter) {
        this.converters.put(converter.getBackedType(), converter);

        return this.asSelf();
    }

    @Override
    @SuppressWarnings("unchecked")
    public U asSelf() {
        return (U) this;
    }
}
