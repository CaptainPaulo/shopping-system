<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 04/01/16
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<%@ include file="menu-admin.jsp" %>
<body>
    <c:forEach items="${members}" var="item">
        ${item.id}
        <br />
        ${item.email}
        <a href="<c:url value="/admin/members/update?id=${item.id}"/>">UPDATE</a> -
        <a href="<c:url value="/admin/members/delete?id=${item.id}"/>">DELETE</a>
    </c:forEach>
</body>
</html>