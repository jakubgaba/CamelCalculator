import com.xany.camel.CalculationLogic;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.support.DefaultMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationLogicTest {

    private CalculationLogic calculationLogic;
    private Exchange exchange;

    @BeforeEach
    public void setUp() {
        calculationLogic = new CalculationLogic();
        exchange = new DefaultExchange(Mockito.mock(org.apache.camel.CamelContext.class));
        exchange.setIn(new DefaultMessage(exchange.getContext()));
    }

    @Test
    void process_addOperation() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("number1", 5);
        body.put("number2", 3);
        body.put("operation", "add");
        exchange.getIn().setBody(body);
        exchange.getIn().setHeader("CamelHttpMethod", "POST");

        calculationLogic.process(exchange);

        assertEquals(8, exchange.getIn().getBody());
    }

    @Test
    void process_subtractOperation() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("number1", 5);
        body.put("number2", 3);
        body.put("operation", "subtract");
        exchange.getIn().setBody(body);
        exchange.getIn().setHeader("CamelHttpMethod", "POST");

        calculationLogic.process(exchange);

        assertEquals(2, exchange.getIn().getBody());
    }

    @Test
    void process_multiplyOperation() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("number1", 5);
        body.put("number2", 3);
        body.put("operation", "multiply");
        exchange.getIn().setBody(body);
        exchange.getIn().setHeader("CamelHttpMethod", "POST");

        calculationLogic.process(exchange);

        assertEquals(15, exchange.getIn().getBody());
    }

    @Test
    void process_divideOperation() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("number1", 6);
        body.put("number2", 3);
        body.put("operation", "divide");
        exchange.getIn().setBody(body);
        exchange.getIn().setHeader("CamelHttpMethod", "POST");

        calculationLogic.process(exchange);

        assertEquals(2, exchange.getIn().getBody());
    }

    @Test
    void process_divideByZero() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("number1", 6);
        body.put("number2", 0);
        body.put("operation", "divide");
        exchange.getIn().setBody(body);
        exchange.getIn().setHeader("CamelHttpMethod", "POST");

        try {
            calculationLogic.process(exchange);
        } catch (ArithmeticException e) {
            assertEquals("/ by zero", e.getMessage());
        }
    }
}