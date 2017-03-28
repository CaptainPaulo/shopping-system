<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 10/01/16
  Time: 16:13
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
<form method="post" action="/admin/members/update">
    <fieldset>
        <legend>Register</legend>

        <input type="hidden" id="id" name="id" value="<c:out value="${member.id}"/>" />
        <label for="email">Email address *</label>
        <input type="email" id="email" name="email" value="<c:out value="${member.email}"/>" size="20" maxlength="60" />
        ${form.errors['email']}
        <br />

        <label for="first-name">First name</label>
        <input type="text" id="first-name" name="first-name" value="<c:out value="${member.firstName}"/>" size="20" maxlength="20" />
        ${form.errors['firstName']}
        <br />

        <label for="last-name">Last Name</label>
        <input type="text" id="last-name" name="last-name" value="<c:out value="${member.lastName}"/>" size="20" maxlength="20" />
        ${form.errors['lastName']}
        <br />

        <input type="checkbox" name="newsletter" value="true" ${checked}> newsletter
        <br />
        <input type="submit" value="Update" class="sansLabel" />
        <br />

        <p>${form.result}</p>
    </fieldset>
</form>
</body>
</html>
