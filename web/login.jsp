<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 04/01/16
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<%@ include file="menu.jsp" %>
<body>
<form method="post" action="login">
    <fieldset>
        <legend>Login</legend>

        <label for="email">Email address *</label>
        <input type="email" id="email" name="email" value="<c:out value="${member.email}"/>" size="20" maxlength="60" />
        ${form.errors['email']}
        <br />

        <label for="password">Password *</label>
        <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
        ${form.errors['password']}
        <br />

        <input type="submit" value="Login" class="sansLabel" />
        <br />

        <p>${form.result}</p>
    </fieldset>
</form>
</body>
</html>
