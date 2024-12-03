package com.xany.camel;

import org.apache.camel.builder.RouteBuilder;

public class Router extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("undertow:http://localhost:8080/")
                .setHeader("CamelHttpMethod", constant("GET"))
                .setHeader("Content-Type", constant("application/json"))
                .process(new CalculationLogic())
                .log("The result is ${body}");

    }
}
