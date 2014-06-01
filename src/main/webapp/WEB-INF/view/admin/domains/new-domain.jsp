<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/24/14
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row">
  <form:form action="${contextPath}/admin/domains" modelAttribute="domainForm" method="post" cssClass="create-domain-form">
    <h2><spring:message code="create.new.domain" /></h2>

    <fieldset>
      <div class="form-group">
        <form:label path="name"><spring:message code="domain.name" /></form:label>
        <form:input path="name" maxlength="40" cssClass="form-control" />
        <form:errors path="name" cssClass="text-danger" />
      </div>
      <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
      Spring Security includes this automatically if Spring MVC form tag is used. --%>
    </fieldset>

    <button class="btn btn-lg btn-primary btn-block" type="submit">
      <spring:message code="create" />
    </button>
  </form:form>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>