<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 29/12/15
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="menu.jsp" %>
<form method="post" action="register">
    <fieldset>
        <legend>Register</legend>

        <label for="email">Email address *</label>
        <input type="email" id="email" name="email" value="<c:out value="${member.email}"/>" size="20" maxlength="60" />
        ${form.errors['email']}
        <br />

        <label for="password">Password *</label>
        <input type="password" id="password" name="password" value="" size="20" maxlength="20" />
        ${form.errors['password']}
        <br />

        <label for="confirmation-password">Password confirmation *</label>
        <input type="password" id="confirmation-password" name="confirmation-password" value="" size="20" maxlength="20" />
        ${form.errors['confirmationPassword']}
        <br />

        <label for="first-name">First name</label>
        <input type="text" id="first-name" name="first-name" value="<c:out value="${member.firstName}"/>" size="20" maxlength="20" />
        ${form.errors['firstName']}
        <br />

        <label for="last-name">Last Name</label>
        <input type="text" id="last-name" name="last-name" value="<c:out value="${member.lastName}"/>" size="20" maxlength="20" />
        ${form.errors['lastName']}
        <br />


        <input type="checkbox" name="newsletter" value="true" ${checked}> newsletter<br />

        <input type="submit" value="Register" class="sansLabel" />
        <br />

        <p>${form.result}</p>
    </fieldset>
</form>
</body>
</html>
