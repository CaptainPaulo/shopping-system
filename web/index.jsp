<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 26/12/15
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <%@ include file="menu.jsp" %>
  <body>
      <c:forEach items="${news}" var="item">
          ${item.title} - by ${item.author} the ${item.publicationDate}
          <br />
          ${item.body}
          <br />
          <br />
      </c:forEach>
  </body>
</html>
