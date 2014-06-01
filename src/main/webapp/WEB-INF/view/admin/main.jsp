<%--
  Created by IntelliJ IDEA.
  User: coldenergia
  Date: 5/17/14
  Time: 5:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="../include/top.jsp"/>
<div class="well well-lg">
  <h4><spring:message code="quick.admin.guide.intro" /></h4>
  <ul>
    <li><spring:message code="quick.admin.guide.step1" /></li>
    <li><spring:message code="quick.admin.guide.step2" /></li>
    <li><spring:message code="quick.admin.guide.step3" /></li>
  </ul>
  <spring:message code="quick.admin.guide.outro" />
</div>
<jsp:include page="../include/bottom.jsp"/>