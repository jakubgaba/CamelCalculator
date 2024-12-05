package com.xany.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.File;
import java.util.*;

public class CalculationLogic implements Processor, Operations {
    public void process(Exchange exchange) throws Exception {
        String method = exchange.getIn().getHeader("CamelHttpMethod", String.class);
        if (method.equals("POST")) {
            Map<String, Object> operation = exchange.getIn().getBody(Map.class);
            LinkedList<String> headers = new LinkedList<>();
            headers.add(exchange.getIn().getHeader("Host", String.class));
            headers.add(exchange.getIn().getHeader("Content-Type", String.class));
            headers.add(exchange.getIn().getHeader("User-Agent", String.class));
            exchange.getIn().setBody(postOperation(operation, headers));
        } else if (method.equals("GET")) {
            exchange.getIn().setBody(getOperation());
        }
    }

}
