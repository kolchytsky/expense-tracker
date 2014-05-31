package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * User: coldenergia
 * Date: 5/31/14
 * Time: 12:02 PM
 */
@Controller
public class DomainHomeController {

    private final DomainService domainService;

    @Autowired
    public DomainHomeController(DomainService domainService) {
        this.domainService = domainService;
    }

    @RequestMapping(value = "/domains/{domainId}", method = RequestMethod.GET)
    public String renderDomainHome(
            @PathVariable("domainId") Long domainId,
            Model model,
            Principal principal,
            HttpServletResponse response) {
        Domain domain = domainService.findOneAccessibleByUser(domainId, principal.getName());
        if (domain != null) {
            model.addAttribute("currentDomain", domain);
            return "spender/domain-home";
        } else {
            // TODO: Should definitely refactor this logic later. Too many raw details.
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "spender/domain-home";
        }
    }

}
