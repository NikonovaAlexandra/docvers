<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 14.02.13
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="/Documents" method="POST">
    <select name="documentList" id="Document"/>
    <c:forEach items="${documentList}"  var="item">
        <option value="${item.name}">${item.name}</option>
    </c:forEach>
    </select>
    <input type="submit" value="submit">
</form>

</body>
</html>