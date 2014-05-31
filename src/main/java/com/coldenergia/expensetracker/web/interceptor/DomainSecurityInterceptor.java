package com.coldenergia.expensetracker.web.interceptor;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Checks whether the user has access to a particular domain.
 * This interceptor checks requests to the spender interface pages,
 * since an administrator can access any domain via the administrator interface.
 * User: coldenergia
 * Date: 5/31/14
 * Time: 1:45 PM
 */
public class DomainSecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private DomainService domainService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        String path = request.getRequestURI();
        if (containsNonAdminDomainPath(path)) {
            Long domainId = findDomainIdFromDomainPath(path);

            if (domainId != null) {
                Domain domain = domainService.findOneAccessibleByUser(domainId, request.getUserPrincipal().getName());
                if (domain == null) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    // Stop processing this request further by returning false.
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws Exception {}

    private boolean containsNonAdminDomainPath(String path) {
        return (!path.contains("/admin/domains/") && path.contains("/domains/"));
    }

    private Long findDomainIdFromDomainPath(String path) {
        path = path.substring(path.indexOf("/domains/") + 9);
        int slashIndex = path.indexOf("/");
        if (slashIndex > 0) {
            path = path.substring(0, slashIndex);
        }
        Long domainId = null;
        try {
            domainId = Long.parseLong(path);
        } catch (NumberFormatException e) {
            // Do nothing
        }
        return domainId;
    }

}
