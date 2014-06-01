<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/24/14
  Time: 6:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row">
  <form:form action="${contextPath}/admin/domains/${domain.id}/users"
             modelAttribute="domainUsersForm" method="post" cssClass="domain-users-form">
    <h2><spring:message code="choose.domain.users" /> ${domain.name}</h2>

    <fieldset>
      <div class="form-group">
        <c:forEach var="item" items="${userMap}">
          <div>
            <form:checkbox path="userIds" value="${item.key}" /> ${item.value}
          </div>
        </c:forEach>
      </div>
      <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
      Spring Security includes this automatically if Spring MVC form tag is used. --%>
    </fieldset>

    <button class="btn btn-lg btn-primary" type="submit">
      <spring:message code="confirm" />
    </button>
  </form:form>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>