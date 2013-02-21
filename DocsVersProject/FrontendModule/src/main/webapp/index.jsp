<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="Messages"/>
<html>
<head>
    <title><fmt:message key="index.title"/></title>

</head>
<body>
<FORM action="AddDocument" method="get">
    <fmt:message key="index.add" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</FORM>
<FORM action="GetAllDocuments" method="get">
    <fmt:message key="index.getAll" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</FORM>
</body>
</html>

