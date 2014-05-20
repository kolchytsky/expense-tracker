package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.domain.Authority;
import com.coldenergia.expensetracker.domain.User;
import com.coldenergia.expensetracker.service.AuthorityService;
import com.coldenergia.expensetracker.service.UserService;
import com.coldenergia.expensetracker.web.view.model.UserForm;
import com.coldenergia.expensetracker.web.view.model.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

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

    /*private final AuthorityService authorityService;

    private final UserService userService;

    private final UserFormValidator userFormValidator;

    private final Authority adminAuthority;

    private final Authority spenderAuthority;

    @Autowired
    public UserController(AuthorityService authorityService, UserService userService, UserFormValidator userFormValidator) {
        this.authorityService = authorityService;
        this.userService = userService;
        this.userFormValidator = userFormValidator;
        // TODO: Hardly like that - refactor!
        this.adminAuthority = authorityService.findByName(ADMIN_AUTHORITY_NAME);
        this.spenderAuthority = authorityService.findByName(SPENDER_AUTHORITY_NAME);
    }*/

    private final UserFormValidator userFormValidator;

    @Autowired
    public UserController(UserFormValidator userFormValidator) {
        this.userFormValidator = userFormValidator;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        // TODO: Consider specifying route mappings somewhere, or at least /admin prefix
        model.addAttribute("userForm", new UserForm());
        return "admin/users/new-user";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(UserForm userForm, BindingResult result) {
        userFormValidator.validate(userForm, result);
        if (result.hasErrors()) {
            return "admin/users/new-user";
        }
        //userService.saveUserWithNewPassword(map(userForm), userForm.getPassword());
        return "redirect:/admin/users";
    }

    /*private User map(UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        String chosenAuthority = userForm.getAuthority();
        Authority authority = null;
        if ("admin".equals(chosenAuthority)) {
            authority = adminAuthority;
        } else if ("spender".equals(chosenAuthority)) {
            authority = spenderAuthority;
        }
        List<Authority> authorities = new ArrayList<Authority>(1);
        authorities.add(authority);
        user.setAuthorities(authorities);
        return user;
    }*/

}
