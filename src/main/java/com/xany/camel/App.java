package com.xany.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new Router());
        context.start();
        Thread.sleep(20000);
        context.stop();
        context.close();
    }

}
