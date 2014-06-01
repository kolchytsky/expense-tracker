package com.coldenergia.expensetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 2:05 PM
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.coldenergia.expensetracker.repository")
@ComponentScan(basePackages = {
        "com.coldenergia.expensetracker.service",
        "com.coldenergia.expensetracker.validator"
})
@EnableTransactionManagement
public class JpaConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setPackagesToScan("com.coldenergia.expensetracker.domain");
        factory.setDataSource(dataSource);
        //turnOnHibernateSqlOutput(factory);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    private void turnOnHibernateSqlOutput(LocalContainerEntityManagerFactoryBean factory) {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        factory.setJpaProperties(properties);
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

}
