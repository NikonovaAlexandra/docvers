<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 17.02.13
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<c:if test="${not empty header['error']}">
    <h1>${header['error']}</h1>
</c:if>
</body>
</html>