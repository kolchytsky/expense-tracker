<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/18/14
  Time: 5:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row">
  <form:form action="${contextPath}/admin/users" modelAttribute="userForm" method="post" cssClass="create-user-form">
    <h2><spring:message code="create.new.user" /></h2>

    <fieldset>
      <div class="form-group">
        <form:label path="name"><spring:message code="name" /></form:label>
        <form:input path="name" maxlength="40" cssClass="form-control" />
        <form:errors path="name" cssClass="text-danger" />
      </div>
      <div class="form-group">
        <form:label path="password"><spring:message code="password" /></form:label>
        <form:password path="password" maxlength="50" cssClass="form-control" />
        <form:errors path="password" cssClass="text-danger" />
      </div>
      <div class="form-group">
        <form:label path="authority"><spring:message code="authority" />:</form:label><br>
        <c:forEach var="authorityName" items="${authorityNames}">
          <form:radiobutton path="authority" value="${authorityName}" />
          <spring:message code="${authorityName.messageCode}" /><br>
        </c:forEach>
        <form:errors path="authority" cssClass="text-danger" />
      </div>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </fieldset>

    <button class="btn btn-lg btn-primary btn-block" type="submit">
      <spring:message code="create" />
    </button>
  </form:form>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>