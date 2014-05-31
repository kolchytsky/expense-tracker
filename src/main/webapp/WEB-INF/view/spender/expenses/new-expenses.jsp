<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/31/14
  Time: 1:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<!-- TODO: Including a stylesheet here is disastrous. Seems like you're doomed to use Sitemesh. -->
<link href="${contextPath}/resources/css/datepicker/bootstrap-datetimepicker.css" rel="stylesheet">
<div class="row">
  <form:form action="${contextPath}/domains/${currentDomainId}/expenses" modelAttribute="expensesForm" method="post"
             cssClass="expenses-form" id="expenses-form">
    <h2><spring:message code="log.expenses" /></h2>

    <div class="row">
      <div class="col-md-6">
        <fieldset>
          <div class="form-group">
            <form:label path="payDate"><spring:message code="expenses.pay.date" /></form:label>
            <div class="input-group date" id="paydate-datetimepicker">
              <form:input path="payDate" maxlength="30" cssClass="form-control" />
              <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
          </div>
          <form:errors path="payDate" cssClass="text-danger" />
          <form:errors path="expenseFormList" cssClass="text-danger" />
        </fieldset>
      </div>
    </div>

    <div id="expenses-form-wrapper">
      <c:forEach var="expenseItem" items="${expensesForm.expenseFormList}" varStatus="loopStatus">
        <fieldset class="expense-row row-${loopStatus.index}">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <form:label path="expenseFormList[${loopStatus.index}].expenseName">
                  <spring:message code="expense.name" />
                </form:label>
                <form:input path="expenseFormList[${loopStatus.index}].expenseName" maxlength="60" cssClass="form-control" />
              </div>
            </div>
            <div class="col-md-6">
              <!-- Nav tabs -->
              <ul class="nav nav-tabs">
                <li class="active">
                  <a href="#basic${loopStatus.index}" data-toggle="tab">
                    <spring:message code="expense.type.basic" />
                  </a>
                </li>
                <li>
                  <a href="#detailed${loopStatus.index}" data-toggle="tab">
                    <spring:message code="expense.type.detailed" />
                  </a>
                </li>
              </ul>

              <!-- Tab panes -->
              <div class="tab-content">
                <div class="tab-pane active expense-basic" id="basic${loopStatus.index}">
                  <div class="form-group">
                    <form:label path="expenseFormList[${loopStatus.index}].fullPrice">
                      <spring:message code="expense.full.price" />
                    </form:label>
                    <form:input path="expenseFormList[${loopStatus.index}].fullPrice" maxlength="11" cssClass="form-control" />
                  </div>
                </div>
                <div class="tab-pane expense-detailed" id="detailed${loopStatus.index}">
                  <div class="row">
                    <div class="col-md-4">
                      <div class="form-group">
                        <form:label path="expenseFormList[${loopStatus.index}].unit">
                          <spring:message code="expense.unit" />
                        </form:label>
                        <form:input path="expenseFormList[${loopStatus.index}].unit" maxlength="10" cssClass="form-control" />
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="form-group">
                        <form:label path="expenseFormList[${loopStatus.index}].quantity">
                          <spring:message code="expense.quantity" />
                        </form:label>
                        <form:input path="expenseFormList[${loopStatus.index}].quantity" maxlength="11" cssClass="form-control" />
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="form-group">
                        <form:label path="expenseFormList[${loopStatus.index}].pricePerUnit">
                          <spring:message code="expense.price.per.unit" />
                        </form:label>
                        <form:input path="expenseFormList[${loopStatus.index}].pricePerUnit" maxlength="11" cssClass="form-control" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <c:set var="hasErrors"><form:errors path="expenseFormList[${loopStatus.index}]*"/></c:set>
          <div class="expense-form-errors ${not empty hasErrors ? 'has-errors' : ''}">
            <form:errors path="expenseFormList[${loopStatus.index}]*" cssClass="text-danger" />
          </div>
        </fieldset>
      </c:forEach>
    </div>
    <a href="#" class="expense-entry-add-btn btn btn-lg btn-primary btn-block">
      <spring:message code="add.one.more.expense" />
    </a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <div class="row">
      <div class="col-md-8">
        <button class="btn btn-lg btn-primary btn-block" type="submit">
          <spring:message code="log.expenses" />
        </button>
      </div>
      <div class="col-md-4">
        <a href="${contextPath}/domains/${currentDomainId}" class="btn btn-lg btn-default btn-block">
          <spring:message code="cancel" />
        </a>
      </div>
    </div>
  </form:form>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>
<script src="${contextPath}/resources/js/datepicker/moment-with-langs.js"></script>
<script src="${contextPath}/resources/js/datepicker/bootstrap-datetimepicker.js"></script>
<script>
  xpnsTrckr.expenseCreation.init();
  xpnsTrckr.expenseCreation.expenseEntryCount = ${fn:length(expensesForm.expenseFormList)};
</script>