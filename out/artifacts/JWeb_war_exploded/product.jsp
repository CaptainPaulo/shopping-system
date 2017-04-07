<%--
  Created by IntelliJ IDEA.
  User: gaetan
  Date: 10/01/16
  Time: 20:48
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
    <h1>${product.name}</h1>
    <p>${product.description}</p>
    <br />
    ${product.price} €
    <br />
    ${category.name}
    <br />
    <h2>Reviews</h2>
    <c:forEach items="${reviews}" var="item">
        by ${item.author}
        <br />
        Stars: ${item.note}/5
        <br />
        Comment:
        <p>
        ${item.description}
        </p>
    </c:forEach>
    <br />
    <form method="post" action="/product">
        <fieldset>
            <legend>Register</legend>

            <input type="hidden" id="product-id" name="product-id" value="<c:out value="${product.id}"/>" />
            <label for="author">Your name</label>
            <input type="text" id="author" name="author" value="<c:out value="${review.author}"/>" size="20" maxlength="60" />
            ${form.errors['author']}
            <br />

            Mark:
            <input type="number" id="note" name="note" value="<c:out value="${review.note}"/>" max="5" min="0" />
            ${form.errors['note']}
            <br />
            <input type="text" id="description" name="description" value="<c:out value="${review.description}"/>" size="20" maxlength="20" />
            ${form.errors['description']}
            <br />
            <input type="submit" value="Post" class="sansLabel" />
            <br />

            <p>${form.result}</p>
        </fieldset>
        </form>
</body>
</html>