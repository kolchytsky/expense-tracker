package com.coldenergia.expensetracker.web.controller;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ADMIN_AUTHORITY_NAME;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;

/**
 * User: coldenergia
 * Date: 5/17/14
 * Time: 5:31 PM
 */
// TODO: Add some methods and logic from SimpleUrlAuthenticationSuccessHandler
public class AuthorityBasedAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final DomainService domainService;

    public AuthorityBasedAuthSuccessHandler(DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * Called when a user has been successfully authenticated.<br>
     * Redirects an admin to the admin page; redirects users having access to no or
     * multiple domains to the domain selection page; and redirects users having access
     * to only one domain to that domain's page.
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
            List<Domain> domains = domainService.findDomainsAccessibleByUser(authentication.getName());
            if (domains.size() == 1) {
                Domain domain = domains.get(0);
                response.sendRedirect(request.getContextPath() + "/domains/" + domain.getId());
            } else {
                response.sendRedirect(request.getContextPath() + "/domain-selection");
            }
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
