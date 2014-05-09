package com.coldenergia.expensetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * User: coldenergia
 * Date: 5/5/14
 * Time: 9:08 PM
 */
@Configuration
//@EnableWebMvcSecurity
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = passwordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        /*auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select name, '1', 1 from users where name = ?")
                .authoritiesByUsernameQuery(
  "select users.name, authorities.name from users join user_authorities on users.id = user_authorities.user_id join authorities on user_authorities.authority_id = authorities.id where users.name = ?");
*/
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
