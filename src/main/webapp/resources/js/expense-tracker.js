/**
 * Created with IntelliJ IDEA.
 * User: coldenergia
 * Date: 5/10/14
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
var xpnsTrckr = {
    expenseCreation: {
        expenseEntryCount: 1,
        addNewExpenseEntry: function() {
            var expensesForm = $('#expenses-form');
            var expenseRow = $('.expense-row.row-0', expensesForm).clone();

            xpnsTrckr.expenseCreation.expenseEntryCount++;
            /* Arrays and lists in Java begin with the 0 index, so it's one less than count. */
            var rowIndex = xpnsTrckr.expenseCreation.expenseEntryCount - 1;
            expenseRow.removeClass('row-0').addClass('row-' + rowIndex);

            $('.expense-form-errors', expenseRow).html('');

            /*$('[id^=expenseFormList]', expenseRow).each(function(index, field) {
                field = $(field);
                field.attr('id', field.attr('id').replace(/0/, rowIndex));
                field.val('');
            });
            $('[name^=expenseFormList]', expenseRow).each(function(index, field) {
                field = $(field);
                field.attr('name', field.attr('name').replace(/0/, rowIndex));
                field.val('');
            });
            $('[for^=expenseFormList]', expenseRow).each(function(index, field) {
                field = $(field);
                field.attr('for', field.attr('for').replace(/0/, rowIndex));
                field.val('');
            });
            $('[id^=basic]', expenseRow).each(function(index, field) {
                field = $(field);
                field.attr('id', field.attr('id').replace(/0/, rowIndex));
                field.val('');
            });
            $('[id^=detailed]', expenseRow).each(function(index, field) {
                field = $(field);
                field.attr('id', field.attr('id').replace(/0/, rowIndex));
                field.val('');
            });*/
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[id^=expenseFormList]', 'id', 0, rowIndex);
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[name^=expenseFormList]', 'name', 0, rowIndex);
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[for^=expenseFormList]', 'for', 0, rowIndex);
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[id^=basic]', 'id', 0, rowIndex);
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[href*=basic]', 'href', 0, rowIndex);
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[id^=detailed]', 'id', 0, rowIndex);
            xpnsTrckr.expenseCreation._replaceIndexesInAttrs(expenseRow, '[href*=detailed]', 'href', 0, rowIndex);

            $('#expenses-form-wrapper').append(expenseRow);
        },
        _replaceIndexesInAttrs: function(row, selector, attrName, indexToReplace, indexReplacement) {
            $(selector, row).each(function(index, field) {
                field = $(field);
                var regEx = new RegExp(indexToReplace, 'gi');
                var previousAttrVal = field.attr(attrName);
                field.attr(attrName, previousAttrVal.replace(regEx, indexReplacement));
                field.val('');
            });
        },
        init: function() {
            var expensesForm = $('#expenses-form');
            $('.expense-entry-add-btn', expensesForm).click(function(event) {
                event.preventDefault();
                xpnsTrckr.expenseCreation.addNewExpenseEntry();
                return false;
            });
        }
    }
};
