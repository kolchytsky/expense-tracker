<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/24/14
  Time: 5:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row"><h2><spring:message code="domain.list" /></h2></div>
<div class="row table-like-row">
  <div class="col-md-2"><spring:message code="domain.id" /></div>
  <div class="col-md-6"><spring:message code="domain.name" /></div>
  <div class="col-md-4">&nbsp;</div>
</div>
<c:forEach var="domain" items="${domains}">
  <div class="row table-like-row">
    <div class="col-md-2">${domain.id}</div>
    <div class="col-md-6">${domain.name}</div>
    <div class="col-md-4 has-button">
      <a href="${contextPath}/admin/domains/${domain.id}/users/edit" class="btn btn-md btn-primary btn-block">
        <spring:message code="edit.domain.users" />
      </a>
    </div>
  </div>
</c:forEach>
<c:if test="${empty domains}">
  <div class="row table-like-row"><spring:message code="there.are.no.domains.yet" /></div>
</c:if>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>