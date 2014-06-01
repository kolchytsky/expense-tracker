<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/18/14
  Time: 3:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<div class="navbar navbar-default navbar-static-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only"><spring:message code="toggle.navigation" /></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#"><spring:message code="expense.tracker" /></a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <!--li class="active"><a href="#">Link</a></li>
        <li><a href="#">Link</a></li>
        <li><a href="#">Link</a></li-->
        <!--li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li class="dropdown-header">Nav header</li>
            <li><a href="#">Separated link</a></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li-->
      </ul>
      <sec:authorize access="hasAuthority('${adminAuthorityName}')">
        <ul class="nav navbar-nav">
          <!--li class="active"><a href="#">Link</a></li>
          <li><a href="#">Link</a></li>
          <li><a href="#">Link</a></li-->
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <spring:message code="manage.users" /> <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="${contextPath}/admin/domains"><spring:message code="manage.domains" /></a></li>
              <li class="divider"></li>
              <li><a href="${contextPath}/admin/users/new"><spring:message code="create.new.user" /></a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <spring:message code="manage.domains" /> <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
              <li><a href="${contextPath}/admin/domains"><spring:message code="list.domains" /></a></li>
              <li class="divider"></li>
              <li><a href="${contextPath}/admin/domains/new"><spring:message code="create.new.domain" /></a></li>
            </ul>
          </li>
        </ul>
      </sec:authorize>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <spring:message code="change.language" /> <b class="caret"></b>
          </a>
          <ul class="dropdown-menu">
            <li><a href="?lang=en"><spring:message code="english.language" /></a></li>
            <li class="divider"></li>
            <li><a href="?lang=uk_UA"><spring:message code="ukrainian.language" /></a></li>
          </ul>
        </li>
        <!--li class="active"><a href="./">Default</a></li>
        <li><a href="../navbar-static-top/">Static top</a></li-->
        <sec:authorize access="isAuthenticated()">
          <li>
            <form:form id="logout-form" action="${contextPath}/logout" method="post">
              <button type="submit" class="btn btn-default logout-btn"><spring:message code="logout" /></button>
            </form:form>
          </li>
        </sec:authorize>
      </ul>
    </div>
  </div>
</div>
<div class="container">