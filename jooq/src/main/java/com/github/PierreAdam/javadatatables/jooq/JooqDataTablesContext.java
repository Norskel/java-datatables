package com.github.PierreAdam.javadatatables.jooq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jooq.DSLContext;

/**
 * JooqDataTablesContext.
 *
 * @author Pierre Adam
 * @since 25.03.10
 */
@Getter
@AllArgsConstructor
public class JooqDataTablesContext {

    /**
     * The Dsl context.
     */
    private final DSLContext dslContext;
}
