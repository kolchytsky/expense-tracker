package com.coldenergia.expensetracker.web.listener;

import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static com.coldenergia.expensetracker.web.ServletContextAttributeNames.CONTEXT_PATH;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: coldenergia
 * Date: 5/10/14
 * Time: 11:13 AM
 */
public class InitAppListenerTest {

    @Test
    public void shouldSetContextPathInServletContext() {
        InitAppListener listener = new InitAppListener();
        ServletContextEvent eventMock = mock(ServletContextEvent.class);
        ServletContext servletContextMock = mock(ServletContext.class);
        when(eventMock.getServletContext()).thenReturn(servletContextMock);
        listener.contextInitialized(eventMock);
        verify(eventMock).getServletContext();
        verify(servletContextMock).getContextPath();
        verify(servletContextMock).setAttribute(eq(CONTEXT_PATH), anyString());
    }

}
