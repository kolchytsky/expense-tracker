package com.coldenergia.expensetracker.web.controller.spender;

import com.coldenergia.expensetracker.domain.ExpenseDetail;
import com.coldenergia.expensetracker.domain.NamedExpenseDetailHolder;
import com.coldenergia.expensetracker.service.DomainService;
import com.coldenergia.expensetracker.service.ExpenseService;
import com.coldenergia.expensetracker.web.view.model.expense.ExpenseForm;
import com.coldenergia.expensetracker.web.view.model.expense.ExpensesForm;
import com.coldenergia.expensetracker.web.view.model.validator.ExpensesFormValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

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
        expenseService.logExpenses(map(expensesForm), domainId);
        return "redirect:/domains/" + domainId;
    }

    // TODO: Create a logout button and a change locale button.
    // TODO: Definitely look into beanutils or something else.
    private List<NamedExpenseDetailHolder> map(ExpensesForm expensesForm) {
        List<ExpenseForm> expenseForms = expensesForm.getExpenseFormList();
        List<NamedExpenseDetailHolder> results = new ArrayList<>(expenseForms.size());
        for (ExpenseForm expenseForm : expenseForms) {
            NamedExpenseDetailHolder result = new NamedExpenseDetailHolder();
            ExpenseDetail expenseDetail = new ExpenseDetail();
            expenseDetail.setFullPrice(expenseForm.getFullPrice());
            if (StringUtils.isEmpty(expenseForm.getUnit())) {
                expenseDetail.setUnit(null);
            } else {
                expenseDetail.setUnit(expenseForm.getUnit());
            }
            expenseDetail.setQuantity(expenseForm.getQuantity());
            expenseDetail.setPricePerUnit(expenseForm.getPricePerUnit());
            expenseDetail.setPayDate(expensesForm.getPayDate());

            result.setExpenseName(expenseForm.getExpenseName());
            result.setExpenseDetail(expenseDetail);

            results.add(result);
        }
        return results;
    }

}
