package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.domain.Domain;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.DomainNameIsTakenException;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.UserService;
import com.coldenergia.expensetracker.web.view.model.domain.DomainForm;
import com.coldenergia.expensetracker.web.view.model.domain.DomainUsersForm;
import com.coldenergia.expensetracker.web.view.model.domain.DomainViewModel;
import com.coldenergia.expensetracker.web.view.model.validator.DomainFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 2:39 PM
 */
@Controller
@RequestMapping("/admin/domains")
public class DomainController {

    private final DomainService domainService;

    private final UserService userService;

    private final DomainFormValidator domainFormValidator;

    @Autowired
    public DomainController(
            DomainService domainService,
            UserService userService,
            DomainFormValidator domainFormValidator) {
        this.domainService = domainService;
        this.userService = userService;
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
        try {
            domainService.save(map(domainForm));
        } catch (DomainNameIsTakenException e) {
            result.rejectValue("name", "domain.name.has.already.been.taken");
            return "admin/domains/new-domain";
        }
        return "redirect:/admin/domains";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<Domain> domains = domainService.findAll();
        model.addAttribute("domains", map(domains));
        return "admin/domains/list-domains";
    }

    @RequestMapping(value = "/{domainId}/users/edit", method = RequestMethod.GET)
    public String initDomainUsersForm(@PathVariable Long domainId, Model model) {
        Domain domain = domainService.findOneAndInitUserList(domainId);
        // TODO: This should be a relatively temporary solution. Because the amount of users can be huge.
        List<User> users = userService.findAllSpenders();
        List<User> currentDomainUsers = domain.getUsers();
        DomainUsersForm form = new DomainUsersForm();
        form.setUserIds(getUserIdSet(currentDomainUsers));

        model.addAttribute("domain", map(domain));
        model.addAttribute("userMap", constructUserIdToUserNameMap(users));
        model.addAttribute("domainUsersForm", form);
        return "admin/domains/edit-domain-users";
    }

    @RequestMapping(value = "/{domainId}/users", method = RequestMethod.POST)
    public String processDomainUsersCreationForm(@PathVariable Long domainId, DomainUsersForm domainUsersForm) {
        Domain domain = domainService.findOne(domainId);
        domainService.setDomainUsers(domainId, domainUsersForm.getUserIds());
        return "redirect:/admin/domains";
    }

    /**
     * Constructs a map with userId as key and user name as value.
     * This map will be used by Spring MVC to render checkbox list.
     * */
    private Map<Long, String> constructUserIdToUserNameMap(List<User> users) {
        Map<Long, String> map = new HashMap<>(users.size());
        for (User u : users) {
            map.put(u.getId(), u.getName());
        }
        return map;
    }

    private Set<Long> getUserIdSet(List<User> users) {
        // TODO: Think about predicates.
        Set<Long> userIds = new HashSet<>(users.size());
        for (User u : users) {
            userIds.add(u.getId());
        }
        return userIds;
    }

    // TODO: Come with some nice solution to mapping.
    private Domain map(DomainForm domainForm) {
        Domain domain = new Domain();
        domain.setName(domainForm.getName());
        return domain;
    }

    private List<DomainViewModel> map(List<Domain> domains) {
        List<DomainViewModel> viewModels = new ArrayList<>(domains.size());
        for (Domain domain : domains) {
            viewModels.add(map(domain));
        }
        return viewModels;
    }

    private DomainViewModel map(Domain domain) {
        DomainViewModel viewModel = new DomainViewModel();
        viewModel.setId(domain.getId());
        viewModel.setName(domain.getName());
        return viewModel;
    }

}
