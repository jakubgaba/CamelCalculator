package com.xany.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CalculationLogic implements Processor {
    public void process(Exchange exchange) throws Exception {
        int number1 = Integer.parseInt((String) exchange.getIn().getHeader("number1"));
        int number2 = Integer.parseInt((String) exchange.getIn().getHeader("number2"));
        String operation = (String) exchange.getIn().getHeader("operation");

        int result;
        switch (operation) {
            case "add":
                result = number1 + number2;
                break;
            case "subtract":
                result = number1 - number2;
                break;
            case "multiply":
                result = number1 * number2;
                break;
            case "divide":
                result = number1 / number2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
        exchange.getIn().setBody(result);
    }
}