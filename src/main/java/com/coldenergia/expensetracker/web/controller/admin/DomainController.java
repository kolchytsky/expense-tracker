package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.web.view.model.DomainForm;
import com.coldenergia.expensetracker.web.view.model.validator.DomainFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// TODO: Implement /admin/domains which would list all domains
/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 2:39 PM
 */
@Controller
@RequestMapping("/admin/domains")
public class DomainController {

    private final DomainService domainService;

    private final DomainFormValidator domainFormValidator;

    @Autowired
    public DomainController(DomainService domainService, DomainFormValidator domainFormValidator) {
        this.domainService = domainService;
        this.domainFormValidator = domainFormValidator;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        model.addAttribute("domainForm", new DomainForm());
        return "admin/domains/new-domain";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(DomainForm domainForm, BindingResult result) {
        domainFormValidator.validate(domainForm, result);
        if (result.hasErrors()) {
            return "admin/domains/new-domain";
        }
        domainService.save(map(domainForm));
        return "redirect:/admin/domains";
    }

    private Domain map(DomainForm domainForm) {
        Domain domain = new Domain();
        domain.setName(domainForm.getName());
        return domain;
    }

}
