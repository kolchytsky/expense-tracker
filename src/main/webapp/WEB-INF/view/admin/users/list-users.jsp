<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 6/1/14
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/include/top.jsp"/>
<div class="row"><h2><spring:message code="user.list" /></h2></div>
<div class="row table-like-row">
  <div class="col-md-2"><spring:message code="user.id" /></div>
  <div class="col-md-10"><spring:message code="user.name" /></div>
</div>
<c:forEach var="user" items="${users.content}">
  <div class="row table-like-row">
    <div class="col-md-2">${user.id}</div>
    <div class="col-md-10">${user.name}</div>
  </div>
</c:forEach>
<c:if test="${users.totalPages > 1}">
  <ul class="pagination">
    <!--li><a href="#">&laquo;</a></li-->
    <c:forEach var="pageNum" begin="0" end="${users.totalPages - 1}">
      <li ${users.number eq pageNum ? 'class="active"' : ''}>
        <a href="${contextPath}/admin/users?page=${pageNum}&size=${users.size}">${pageNum + 1}</a>
      </li>
    </c:forEach>
    <!--li><a href="#">1</a></li>
    <li><a href="#">2</a></li>
    <li><a href="#">3</a></li>
    <li><a href="#">4</a></li>
    <li><a href="#">5</a></li>
    <li><a href="#">&raquo;</a></li-->
  </ul>
</c:if>
<c:if test="${users.numberOfElements eq 0}">
  <div class="row table-like-row"><spring:message code="there.are.no.users.yet" /></div>
</c:if>
<jsp:include page="/WEB-INF/view/include/bottom.jsp"/>