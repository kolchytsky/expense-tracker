package com.coldenergia.expensetracker.config.initializer;

import com.coldenergia.expensetracker.config.production.ProductionSecurityConfiguration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

/**
 * User: coldenergia
 * Date: 5/5/14
 * Time: 9:59 PM
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(ProductionSecurityConfiguration.class);
    }

    // TODO: Would be nice to have a test for that!
    /**
     * Adds two useful filters:
     * <ul>
     *     <li>{@link HiddenHttpMethodFilter} -
     *         used so we can use forms of method type 'PUT' and 'DELETE'. See here:
     *     http://static.springsource.org/spring/docs/current/spring-framework-reference/html/view.html#rest-method-conversion
     *     </li>
     *     <li>{@link CharacterEncodingFilter} - to provide support for utf-8 encoding of the request.
     *     Also, don't forget to set {@code URIEncoding="UTF-8"} on {@code <Connector>} in {@code server.xml}</li>
     * </ul>
     * Has to be declared here because if declared in {@link WebAppInitializer},
     * it won't work because of Spring Security
     * */
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncodingFilterReg = servletContext.addFilter(
                "characterEncodingFilter",
                characterEncodingFilter
        );
        /*characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");*/
        characterEncodingFilterReg.addMappingForUrlPatterns(null, false, "/*");

        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        FilterRegistration.Dynamic hiddenHttpMethodFilterReg = servletContext.addFilter(
                "hiddenHttpMethodFilter",
                hiddenHttpMethodFilter
        );
        hiddenHttpMethodFilterReg.addMappingForUrlPatterns(null, false, "/*");
        super.beforeSpringSecurityFilterChain(servletContext);
    }

}
