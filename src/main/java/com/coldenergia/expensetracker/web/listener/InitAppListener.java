package com.coldenergia.expensetracker.web.listener;

import com.coldenergia.expensetracker.defaultdata.DefaultDataConstants;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static com.coldenergia.expensetracker.web.ServletContextAttributeNames.*;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 11:16 AM
 */
@WebListener
public class InitAppListener implements ServletContextListener {

    /**
     * Receives notification that the web application initialization
     * process is starting.
     * <p/>
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String contextPath = servletContext.getContextPath();
        // Set context path to use in JSPs, like that:
        // '<script src="${contextPath}/resources/js/jquery-1.11.1.js"></script>'
        servletContext.setAttribute(CONTEXT_PATH, contextPath);
        servletContext.setAttribute(ADMIN_AUTHORITY_NAME, DefaultDataConstants.ADMIN_AUTHORITY_NAME);
        servletContext.setAttribute(SPENDER_AUTHORITY_NAME, DefaultDataConstants.SPENDER_AUTHORITY_NAME);
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     * <p/>
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
