<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/31/14
  Time: 12:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row">
  <h2>${currentDomain.name}</h2>
</div>
<div class="row">
  <a href="${contextPath}/domains/${currentDomain.id}/expenses/new" class="btn btn-default">
    i18n me! Log expenses against this domain
  </a>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>