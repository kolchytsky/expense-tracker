<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/18/14
  Time: 3:51 PM
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