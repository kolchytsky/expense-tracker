package com.coldenergia.expensetracker.config;

import com.coldenergia.expensetracker.web.controller.AuthorityBasedAuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configures Spring Security. This class is used in both the tests and production.<br>
 * User: coldenergia
 * Date: 5/5/14
 * Time: 9:08 PM
 */
@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
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

    /**
     * Override this method to configure the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity}.
     * Typically subclasses should not invoke this method by calling super
     * as it may override their configuration. The default configuration is:
     * <p/>
     * <pre>
     * http
     *     .authorizeRequests()
     *         .anyRequest().authenticated().and()
     *     .formLogin().and()
     *     .httpBasic();
     * </pre>
     *
     * @param http the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(new AuthorityBasedAuthSuccessHandler())
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/resources/**").permitAll()
                    .anyRequest().authenticated();
    }

}
