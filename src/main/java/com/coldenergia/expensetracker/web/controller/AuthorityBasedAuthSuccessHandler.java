package com.coldenergia.expensetracker.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ADMIN_AUTHORITY_NAME;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;

/**
 * User: coldenergia
 * Date: 5/17/14
 * Time: 5:31 PM
 */
// TODO: Add some methods and logic from SimpleUrlAuthenticationSuccessHandler
public class AuthorityBasedAuthSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (hasAuthority(authentication, ADMIN_AUTHORITY_NAME)) {
            // Redirect to main administrator page
            response.sendRedirect(request.getContextPath() + "/admin");
        } else if (hasAuthority(authentication, SPENDER_AUTHORITY_NAME)) {
            // Redirect to main user page
            response.sendRedirect(request.getContextPath());
        } else {
            // TODO: Throw some exception since this mustn't be reached
        }
    }

    private boolean hasAuthority(Authentication authentication, String authorityName) {
        Collection<? extends  GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasAdminAuthority = false;
        for (GrantedAuthority authority : authorities) {
            if (authorityName.equals(authority.getAuthority())) {
                hasAdminAuthority = true;
                break;
            }
        }
        return hasAdminAuthority;
    }

}
