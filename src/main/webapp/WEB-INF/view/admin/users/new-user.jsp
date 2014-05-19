<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/18/14
  Time: 5:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row">
  <form action="${contextPath}/admin/new" role="form" method="post" class="create-user-form">
    <h2><spring:message code="create.new.user" /></h2>

    <label><spring:message code="name" /> <input name="name" type="text" /></label><br>
    <label><spring:message code="authority" />:</label>
    <label><spring:message code="authority.spender" /> <input name="authority" type="radio" value="spender" /></label>
    <label><spring:message code="authority.admin" /> <input name="authority" type="radio" value="admin" /></label>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button class="btn btn-lg btn-primary btn-block" type="submit">
      <spring:message code="create" />
    </button>
  </form>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>