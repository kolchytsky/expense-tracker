package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.UserNameIsTakenException;
import com.coldenergia.expensetracker.service.UserService;
import com.coldenergia.expensetracker.web.view.model.UserForm;
import com.coldenergia.expensetracker.web.view.model.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// TODO: Implement /admin/users so that it would list all the users
/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 5:58 PM
 */
@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;

    private final UserFormValidator userFormValidator;

    @Autowired
    public UserController(UserService userService, UserFormValidator userFormValidator) {
        this.userService = userService;
        this.userFormValidator = userFormValidator;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listUsers(Model model, Pageable pageable) {
        model.addAttribute("users", userService.findAll(pageable));
        return "admin/users/list-users";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        // TODO: Consider specifying route mappings somewhere, or at least /admin prefix
        // TODO: Make a nicer form. Have a label and then its input on a newline.
        model.addAttribute("userForm", new UserForm());
        setAuthorityNames(model);
        return "admin/users/new-user";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(UserForm userForm, Model model, BindingResult result) {
        userFormValidator.validate(userForm, result);
        if (result.hasErrors()) {
            setAuthorityNames(model);
            return "admin/users/new-user";
        }
        try {
            userService.saveUserWithNewPassword(map(userForm), getAuthorityNames(userForm), userForm.getPassword());
        } catch (UserNameIsTakenException e) {
            result.rejectValue("name", "user.name.has.already.been.taken");
            setAuthorityNames(model);
            return "admin/users/new-user";
        }
        return "redirect:/admin/users";
    }

    public void setAuthorityNames(Model model) {
        model.addAttribute("authorityNames", Arrays.asList(UserForm.AuthorityName.values()));
    }

    private User map(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        return user;
    }

    private Set<String> getAuthorityNames(UserForm userForm) {
        Set<String> authorityNames = new HashSet<>(1);
        String chosenAuthority = userForm.getAuthority().getValue();
        authorityNames.add(chosenAuthority);
        return authorityNames;
    }

}
