<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 04/01/16
  Time: 13:40
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
<form method="post" action="/admin/news/new">
    <fieldset>
        <legend>Create news</legend>

        <label for="title">Title</label>
        <input type="title" id="title" name="title" value="<c:out value="${news.title}"/>" size="20" maxlength="60" />
        ${form.errors['title']}
        <br />
        <textarea id="body" name="body"><c:out value="${news.body}"/></textarea>
        ${form.errors['body']}
        <br />
        <input type="submit" value="Create" class="sansLabel" />
        <br />

        <p>${form.result}</p>
    </fieldset>
</form>
</body>
</html>
