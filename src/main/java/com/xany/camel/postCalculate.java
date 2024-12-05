package com.xany.camel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

interface postOperations {
    default int postOperation(Map<String, Object> operation, LinkedList<String> headers) {

        if (operation == null) {
            throw new IllegalArgumentException("Input data is null");
        }
        if (!operation.containsKey("number1") || !operation.containsKey("number2") || !operation.containsKey("operation")) {
            throw new IllegalArgumentException("Missing required keys in input data");
        }

        int a = (int) operation.get("number1");
        int b = (int) operation.get("number2");
        String mathOperation = (String) operation.get("operation");

        if (!mathOperation.equals("add") && !mathOperation.equals("subtract") && !mathOperation.equals("multiply") && !mathOperation.equals("divide")) {
            throw new IllegalArgumentException("Invalid operation type: " + mathOperation + ". Supported operations: add, subtract, multiply, divide");
        }
        String timestamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());

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

        String xmlContent = String.format(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<?xml-stylesheet type=\"text/xsl\" href=\"calculation.xsl\"?>\n" +
                        "<!DOCTYPE calculation SYSTEM \"calculation.dtd\">\n" +
                        "<!--\n" +
                        "    Simple calculations by Jakub G. XML\n" +
                        "-->\n" +
                        "\n" +
                        "<calculation>\n" +
                        "<host>%s</host>\n" +
                        "<Content-Type>%s</Content-Type>\n" +
                        "<User-Agent>%s</User-Agent>\n" +
                        "<!--\n" +
                        "    Operation details\n" +
                        "-->\n" +
                        "<!--\n" +
                        "    Date-n-time\n" +
                        "-->\n" +
                        "    <timestamp>%s</timestamp>\n" +
                        "<!--\n" +
                        "    Input numbers\n" +
                        "-->\n" +
                        "    <number1>%d</number1>\n" +
                        "    <number2>%d</number2>\n" +

                        "<!--\n" +
                        "    Operation\n" +
                        "-->\n" +
                        "    <operation>%s</operation>\n" +
                        "<!--\n" +
                        "    Result\n" +
                        "-->\n" +
                        "    <result>%d</result>\n" +
                        "\n" +
                        "</calculation>"
                , headers.get(0), headers.get(1), headers.get(2), timestamp, a, b, mathOperation, result

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
        return result;
    }
}
