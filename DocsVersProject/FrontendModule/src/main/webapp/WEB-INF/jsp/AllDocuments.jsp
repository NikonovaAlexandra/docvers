<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 22.02.13
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>

<form action="/Documents" method="POST">
    <c:if test="${not empty noanydocmessage}">
        <h1>${noanydocmessage}</h1>
    </c:if>

    <table>
        <c:forEach items="${documentList}" var="item">
            <tr>
                <td><a href="#">${item.name}</a></td>
                <td>${item.description}</td>
            </tr>
        </c:forEach>
    </table>

    <select name="documentList" id="Document"/>
    <c:forEach items="${documentList}" var="item">
        <option value="${item.name}">${item.name}</option>
    </c:forEach>
    </select>
    <input type="submit" value="submit">
</form>

</body>
</html>