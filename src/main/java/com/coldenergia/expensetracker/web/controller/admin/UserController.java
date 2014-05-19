package com.coldenergia.expensetracker.web.controller.admin;

import com.coldenergia.expensetracker.web.view.model.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: coldenergia
 * Date: 5/18/14
 * Time: 5:58 PM
 */
@Controller
@RequestMapping("/admin/users")
public class UserController {

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        // TODO: Consider specifying route mappings somewhere, or at least /admin prefix
        model.addAttribute("userForm", new UserForm());
        return "admin/users/new-user";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationForm(UserForm userForm) {
        return "redirect:/admin/users";
    }

}
