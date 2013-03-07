<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 25.02.13
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="Messages"/>
<html>
<head>
    <title><fmt:message key="index.title"/></title>
    <link rel="stylesheet" type="text/css" href="style.css"/>

</head>
<body>
<FORM action="AddDocument" method="post">
    <fmt:message key="index.add" var="buttonValue"/>
    <input type="submit" name="submit" value="${buttonValue}">
</FORM>
<FORM action="GetAllDocuments" method="get">
    <fmt:message key="index.getAll" var="buttonValue"/>
    <input type="submit" name="submit" value="${buttonValue}">
</FORM>
<FORM action="Logout" method="post">
    <input type="submit" name="submit" value="Logout">
</FORM>

</body>
</html>