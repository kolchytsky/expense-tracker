package com.coldenergia.expensetracker.web.listener;

import com.coldenergia.expensetracker.defaultdata.DefaultDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: coldenergia
 * Date: 5/17/14
 * Time: 4:00 PM
 */
/**
 * This listener is registered programmatically in {@link com.coldenergia.expensetracker.config.WebAppInitializer},
 * this is why there's no {@code @WebListener} annotation present.
 * */
public class DatabaseInitializer implements ServletContextListener {

    @Autowired
    private DefaultDataInitializer defaultDataInitializer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        defaultDataInitializer.insertInitialDataIntoDb();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

}
