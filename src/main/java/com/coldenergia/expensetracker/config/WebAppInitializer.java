package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.web.listener.DatabaseInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 4:23 PM
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Specify {@link org.springframework.context.annotation.Configuration @Configuration}
     * and/or {@link org.springframework.stereotype.Component @Component} classes to be
     * provided to the {@linkplain #createRootApplicationContext() root application context}.
     *
     * @return the configuration classes for the root application context, or {@code null}
     *         if creation and registration of a root context is not desired
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * Specify {@link org.springframework.context.annotation.Configuration @Configuration}
     * and/or {@link org.springframework.stereotype.Component @Component} classes to be
     * provided to the {@linkplain #createServletApplicationContext() dispatcher servlet
     * application context}.
     *
     * @return the configuration classes for the dispatcher servlet application context
     *         (may not be empty or {@code null})
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebAppConfiguration.class };
    }

    /**
     * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash;
     * for example {@code "/"}, {@code "/app"}, etc.
     *
     * @see #registerDispatcherServlet(javax.servlet.ServletContext)
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        // Internally the following method registers a Spring Context Loader listener - this has to be done...
        registerContextLoaderListener(servletContext);
        // ...before registering our own listener, which will use @Autowired
        servletContext.addListener(new DatabaseInitializer());
    }

}
