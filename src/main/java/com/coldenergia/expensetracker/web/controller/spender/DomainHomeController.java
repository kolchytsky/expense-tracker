package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String renderDomainHome(@PathVariable("domainId") Long domainId, Model model) {
        model.addAttribute("currentDomain", domainService.findOne(domainId)); // TODO: View models...
        return "spender/domain-home";
    }

}
