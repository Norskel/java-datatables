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

package com.github.PierreAdam.javadatatables.sample;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.PierreAdam.javadatatables.core.entities.AjaxQueryForm;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * com.github.PierreAdam.javadatatables.sample.MainClass.
 *
 * @author Pierre Adam
 * @since 25.03.14
 */
@Slf4j
public class MainClass {

    /**
     * The Jooq context.
     */
    private final JooqContext jooqContext;

    /**
     * The Server.
     */
    private final Undertow server;

    /**
     * The Object mapper.
     */
    private final ObjectMapper objectMapper;

    /**
     * The Jooq person data table.
     */
    private final JooqPersonDataTable jooqPersonDataTable;

    /**
     * Instantiates a new Main class.
     *
     * @throws Exception the exception
     */
    public MainClass() throws Exception {
        // Create an instance of com.github.PierreAdam.javadatatables.sample.JooqContext
        this.jooqContext = new JooqContext();

        // Initialize and start an Undertow web server
        this.server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(Handlers.path()
                        .addExactPath("/", this::index)
                        .addExactPath("/data", this::data))
                .build();

        this.objectMapper = new ObjectMapper();
        this.jooqPersonDataTable = new JooqPersonDataTable(this.objectMapper, this.jooqContext.getDslContext());

        System.out.println("Web server is running on http://localhost:8080");
    }

    /**
     * Main.
     *
     * @param args the args
     * @throws Exception the exception
     */
    public static void main(final String[] args) throws Exception {
        final MainClass mainClass = new MainClass();

        mainClass.run();
    }

    /**
     * Run.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void run() throws InterruptedException {
        this.server.start();

        // Wait loop to keep the application running
        synchronized (MainClass.class) {
            MainClass.class.wait();
        }
    }

    /**
     * Index.
     *
     * @param exchange the exchange
     */
    public void index(final HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        Scanner scanner = null;

        try {
            if (true) {
                scanner = new Scanner(new File("sample/src/main/resources/index.html"), StandardCharsets.UTF_8);
            } else {
                scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("index.html"), StandardCharsets.UTF_8);
            }

            final String content = scanner.useDelimiter("\\A").next();
            exchange.getResponseSender().send(content);
        } catch (final Exception e) {
            MainClass.logger.error("Failed to load index.html", e);
            exchange.setStatusCode(500);
            exchange.getResponseSender().send("Internal Server Error");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            exchange.endExchange();
        }
    }

    /**
     * Data.
     *
     * @param exchange the exchange
     */
    private void data(final HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getRequestReceiver().receiveFullString((exchangeObj, requestBody) -> {
            MainClass.logger.info("Received request body: {}", requestBody);

            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                final AjaxQueryForm ajaxQueryForm = objectMapper.readValue(requestBody, AjaxQueryForm.class);
                final JsonNode ajaxResult = this.jooqPersonDataTable.getAjaxResult(ajaxQueryForm.getParameters());
                MainClass.logger.info("Ajax result as string: {}", ajaxResult.toString());
                exchangeObj.getResponseSender().send(ajaxResult.toString());
            } catch (final Exception e) {
                MainClass.logger.error("Failed to parse requestBody into AjaxQueryForm", e);
                exchangeObj.setStatusCode(400);
                exchangeObj.getResponseSender().send("Invalid Request");
            } finally {
                exchangeObj.endExchange();
            }
        });
    }
}
