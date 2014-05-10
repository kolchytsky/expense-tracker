package com.coldenergia.expensetracker.web.controller;

import com.coldenergia.expensetracker.config.SecurityConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * User: coldenergia
 * Date: 5/9/14
 * Time: 9:49 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { com.coldenergia.expensetracker.config.WebAppConfiguration.class, SecurityConfiguration.class })
@WebAppConfiguration
public abstract class ControllerTest {

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected FilterChainProxy springSecurityFilterChain;

}
