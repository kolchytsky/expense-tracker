package com.coldenergia.expensetracker.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 2:51 PM
 */
@Configuration
public class TestDataSourceConfiguration {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    // TODO: Configure properties so that it is read from a file
    /*@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://localhost:3306/expense_tracker_test?tcpKeepAlive=true");
        dataSource.setUsername("root");
        dataSource.setPassword("PASSWORD GOES HERE - MOVE IT TO EXTERNAL FILE");

        return dataSource;
    }*/

}
