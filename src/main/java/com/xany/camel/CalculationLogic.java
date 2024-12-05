package com.xany.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class CalculationLogic implements Processor, postOperations {
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
            File directory = new File("timelapses");
            BufferedReader reader = null;
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                files[i] = files[i].substring(12, 24);
            }
            for(int i = 0; i < files.length; i++) {
                for(int j = i + 1; j < files.length; j++) {
                    if(files[i].compareTo(files[j]) > 0) {
                        String temp = files[i];
                        files[i] = files[j];
                        files[j] = temp;
                    }
                }
            }
            exchange.getIn().setBody(Arrays.toString(files));
        }
    }

}
