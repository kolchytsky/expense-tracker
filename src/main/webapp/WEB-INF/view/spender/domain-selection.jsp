<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/30/14
  Time: 8:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row">
  <h2><spring:message code="select.domain.to.work.with" /></h2>
</div>
<div class="row">
  <c:choose>
    <c:when test="${not empty availableDomains}">
      <div class="btn-group">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
          <spring:message code="select.domain" /> <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu">
          <c:forEach var="availableDomain" items="${availableDomains}" varStatus="loopStatus">
            <li><a href="${contextPath}/domains/${availableDomain.id}">${availableDomain.name}</a></li>
            <c:if test="${not loopStatus.last}">
              <li class="divider"></li>
            </c:if>
          </c:forEach>
        </ul>
      </div>
    </c:when>
    <c:otherwise>
      <spring:message code="no.available.domains.for.user" />
    </c:otherwise>
  </c:choose>
</div>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>