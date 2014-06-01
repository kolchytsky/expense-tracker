<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/10/14
  Time: 9:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <jsp:include page="include/top.jsp"/>
  <div class="row">
    <form action="${contextPath}/login" class="form-signin" role="form" method="post">
      <h2 class="form-signin-heading"><spring:message code="please.login" /></h2>
      <c:if test="${param.error != null}">
        <div class="alert alert-danger"><spring:message code="login.invalid.credentials" /></div>
      </c:if>
      <c:if test="${param.logout != null}">
        <div class="alert alert-success"><spring:message code="logout.msg" /></div>
      </c:if>
      <spring:message code="name" var="msgName" />
      <input name="username" type="text" class="form-control" placeholder="${msgName}" required autofocus>
      <spring:message code="password" var="msgPassword" />
      <input name="password" type="password" class="form-control" placeholder="${msgPassword}" required>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        <spring:message code="login" />
      </button>
    </form>
  </div>
  <jsp:include page="include/bottom.jsp"/>