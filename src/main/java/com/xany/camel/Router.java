package com.xany.camel;

import org.apache.camel.builder.RouteBuilder;

public class Router extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("undertow:http://localhost:8080/Calculator")
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .choice()
                .when(header("CamelHttpMethod").isEqualTo("GET"))
                .process(new CalculationLogic())
                .otherwise()
                .unmarshal().json()
                .process(new CalculationLogic())
                .marshal().json()
                .log("Request body:  ${body}");

    }
}
