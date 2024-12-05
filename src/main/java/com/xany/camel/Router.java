package com.xany.camel;

import org.apache.camel.builder.RouteBuilder;

public class Router extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("undertow:http://localhost:8080/Calculator")
                .setHeader("CamelHttpMethod", constant("POST"))
                .unmarshal().json()
                .process(new CalculationLogic())
                .marshal().json()
                .log("POST request ${body}");

    }
}
