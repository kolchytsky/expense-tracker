package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.web.view.model.expense.ExpenseForm;
import com.coldenergia.expensetracker.web.view.model.expense.ExpensesForm;
import com.coldenergia.expensetracker.web.view.model.validator.ExpensesFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * User: coldenergia
 * Date: 5/31/14
 * Time: 1:19 PM
 */
@Controller
public class ExpenseLogController {

    private final DomainService domainService;

    private final ExpenseService expenseService;

    private final ExpensesFormValidator expensesFormValidator;

    @Autowired
    public ExpenseLogController(
            DomainService domainService,
            ExpenseService expenseService,
            ExpensesFormValidator expensesFormValidator) {
        this.domainService = domainService;
        this.expenseService = expenseService;
        this.expensesFormValidator = expensesFormValidator;
    }

    @RequestMapping(value = "/domains/{domainId}/expenses/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("domainId") Long domainId, Model model, Principal principal) {
        model.addAttribute("currentDomainId", domainId);
        ExpensesForm expensesForm = new ExpensesForm();
        // Add one expense form to the expense form list, since that would be required by js functionality
        expensesForm.getExpenseFormList().add(new ExpenseForm());
        model.addAttribute("expensesForm", expensesForm);
        return "spender/expenses/new-expenses";
    }

    @RequestMapping(value = "/domains/{domainId}/expenses", method = RequestMethod.POST)
    public String processCreationForm(
            @PathVariable("domainId") Long domainId,
            Model model,
            ExpensesForm expensesForm,
            BindingResult result) {
        expensesFormValidator.validate(expensesForm, result);
        if (result.hasErrors()) {
            model.addAttribute("currentDomainId", domainId);
            return "spender/expenses/new-expenses";
        }
        return "redirect:/domains/" + domainId;
    }

}
