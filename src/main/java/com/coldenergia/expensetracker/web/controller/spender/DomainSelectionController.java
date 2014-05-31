package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * User: coldenergia
 * Date: 5/30/14
 * Time: 8:59 PM
 */
@Controller
public class DomainSelectionController {

    private final DomainService domainService;

    @Autowired
    public DomainSelectionController(DomainService domainService) {
        this.domainService = domainService;
    }

    @RequestMapping(value = "/domain-selection", method = RequestMethod.GET)
    public String renderDomainSelection(Model model, Principal principal) {
        model.addAttribute("availableDomains", domainService.findDomainsAccessibleByUser(principal.getName()));
        return "spender/domain-selection";
    }

}
