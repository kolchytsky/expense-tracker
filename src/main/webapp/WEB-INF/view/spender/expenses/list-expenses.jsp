<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 6/1/14
  Time: 1:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row"><h2><spring:message code="expense.list" /></h2></div>
<div class="row table-like-row">
  <div class="col-md-1"><spring:message code="expense.id" /></div>
  <div class="col-md-6"><spring:message code="expense.name" /></div>
  <div class="col-md-2"><spring:message code="expense.unit" /></div>
  <div class="col-md-1"><spring:message code="expense.quantity" /></div>
  <div class="col-md-1"><spring:message code="expense.price.per.unit" /></div>
  <div class="col-md-1"><spring:message code="expense.full.price" /></div>
</div>
<c:forEach var="expense" items="${expenses.content}">
  <div class="row table-like-row">
    <div class="col-md-1">${expense.id}</div>
    <div class="col-md-6">${expense.expense.name}</div>
    <div class="col-md-2">
      ${expense.unit}${expense.unit eq null ? '-' : ''}
    </div>
    <div class="col-md-1">
      <fmt:formatNumber value="${expense.quantity}" maxFractionDigits="3"/>${expense.quantity eq null ? '-' : ''}
    </div>
    <div class="col-md-1">
      <fmt:formatNumber value="${expense.pricePerUnit}" maxFractionDigits="3"/>${expense.pricePerUnit eq null ? '-' : ''}
    </div>
    <%-- TODO: The following code could have been easily calculated and put in a ViewModel! And get rid of fmt --%>
    <c:set var="fullPrice" value="${expense.fullPrice}" />
    <c:if test="${fullPrice eq null}">
      <c:set var="fullPrice" value="${expense.quantity.multiply(expense.pricePerUnit)}" />
    </c:if>
    <div class="col-md-1"><fmt:formatNumber value="${fullPrice}" maxFractionDigits="3"/></div>
  </div>
</c:forEach>
<c:if test="${expenses.totalPages > 1}">
  <ul class="pagination">
    <!--li><a href="#">&laquo;</a></li-->
    <c:forEach var="pageNum" begin="0" end="${expenses.totalPages - 1}">
      <li ${expenses.number eq pageNum ? 'class="active"' : ''}>
        <a href="${contextPath}/domains/${currentDomainId}/expenses?page=${pageNum}&size=${expenses.size}">
          ${pageNum + 1}
        </a>
      </li>
    </c:forEach>
    <!--li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li>
    <li><a href="#">&raquo;</a></li-->
  </ul>
</c:if>
<c:if test="${expenses.numberOfElements eq 0}">
  <div class="row table-like-row"><spring:message code="no.expenses.have.been.logged.yet" /></div>
</c:if>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>