package org.cse486.app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.exit(0);
    }
}
