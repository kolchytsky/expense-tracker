package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.UserService;
import com.coldenergia.expensetracker.web.view.model.UserForm;
import com.coldenergia.expensetracker.web.view.model.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.ADMIN_AUTHORITY_NAME;
import static com.coldenergia.expensetracker.defaultdata.DefaultDataConstants.SPENDER_AUTHORITY_NAME;

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

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        // TODO: Consider specifying route mappings somewhere, or at least /admin prefix
        // TODO: Make a nicer form. Have a label and then its input on a newline.
        model.addAttribute("userForm", new UserForm());
        return "admin/users/new-user";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(UserForm userForm, BindingResult result) {
        userFormValidator.validate(userForm, result);
        if (result.hasErrors()) {
            return "admin/users/new-user";
        }
        userService.saveUserWithNewPassword(map(userForm), getAuthorityNames(userForm), userForm.getPassword());
        return "redirect:/admin/users";
    }

    private User map(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        return user;
    }

    private Set<String> getAuthorityNames(UserForm userForm) {
        Set<String> authorityNames = new HashSet<>(1);
        String chosenAuthority = userForm.getAuthority();
        if ("admin".equals(chosenAuthority)) {
            authorityNames.add(ADMIN_AUTHORITY_NAME);
        } else if ("spender".equals(chosenAuthority)) {
            authorityNames.add(SPENDER_AUTHORITY_NAME);
        }
        return authorityNames;
    }

}
