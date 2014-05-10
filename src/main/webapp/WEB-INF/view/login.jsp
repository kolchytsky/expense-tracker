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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="expense.tracker" /></title>

    <!-- Bootstrap -->
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <link href="${contextPath}/resources/css/expense-tracker.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="${contextPath}/resources/js/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="${contextPath}/resources/js/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
  <div class="row"><h1><spring:message code="expense.tracker" /></h1></div>
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
      <input type="username" class="form-control" placeholder="${msgName}" required autofocus>
      <spring:message code="password" var="msgPassword" />
      <input type="password" class="form-control" placeholder="${msgPassword}" required>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        <spring:message code="login" />
      </button>
    </form>
  </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${contextPath}/resources/js/jquery-1.11.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>