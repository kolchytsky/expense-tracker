package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.web.interceptor.DomainSecurityInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * Web application configuration, which has spring data web support enabled.
 * From Spring Data docs:
 * Spring Data modules ships with a variety of web support if the module supports
 * the repository programming model. The web related stuff requires Spring MVC JARs
 * on the classpath, some of them even provide integration with Spring HATEOAS.<br>
 * User: coldenergia
 * Date: 5/9/14
 * Time: 4:47 PM
 */
@Configuration
@ComponentScan(basePackages = {
        "com.coldenergia.expensetracker.web.controller",
        "com.coldenergia.expensetracker.web.interceptor",
        "com.coldenergia.expensetracker.web.view.model.validator"
})
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin/main");
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * {@inheritDoc}
     * <p>This implementation is empty.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(domainSecurityInterceptor());
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    // TODO: Think about changing locales, as there's something with Spring Security (filter?) and login
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //localeResolver.setDefaultLocale(new Locale("uk", "UA"));
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public DomainSecurityInterceptor domainSecurityInterceptor() {
        return new DomainSecurityInterceptor();
    }

}
