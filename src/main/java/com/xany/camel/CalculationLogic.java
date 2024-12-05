package com.xany.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CalculationLogic implements Processor {
    public void process(Exchange exchange) throws Exception {
        String method = exchange.getIn().getHeader("CamelHttpMethod", String.class);

        if (method.equals("POST")) {
            Map<String, Object> operation = exchange.getIn().getBody(Map.class);

            if (operation == null) {
                System.out.println(operation);
                throw new IllegalArgumentException("Input data is null");
            }

            if (!operation.containsKey("number1") || !operation.containsKey("number2") || !operation.containsKey("operation")) {
                throw new IllegalArgumentException("Missing required keys in input data");
            }

            int a = (int) operation.get("number1");
            int b = (int) operation.get("number2");

            String mathOperation = (String) operation.get("operation");

            String timestamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
            System.out.println("timestamp: " + timestamp);
            String xmlContent = String.format(
                    "<calculation>\n" +
                            "    <timestamp>%s</timestamp>\n" +
                            "    <a>%d</a>\n" +
                            "    <b>%d</b>\n" +
                            "    <operation>%s</operation>\n" +
                            "</calculation>",
                    timestamp, a, b, mathOperation
            );

            File directory = new File("timelapses");
            if (!directory.exists()) {
                directory.mkdir();
            }
            try (FileWriter writer = new FileWriter(new File(directory, "calculation_" + timestamp + ".xml"))) {
                writer.write(xmlContent);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int result = 0;
            switch (mathOperation) {
                case "add":
                    result = a + b;
                    break;
                case "subtract":
                    result = a - b;
                    break;
                case "multiply":
                    result = a * b;
                    break;
                case "divide":
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = a / b;
                    break;
            }
            exchange.getIn().setBody(result);
        }
    }
}