<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<%--<fmt:setLocale value="${language}" />--%>
<%--<fmt:setBundle basename="resources/Messages"/>--%>
<%--<fmt:setBundle basename="./Messages"/>--%>
<fmt:bundle basename="Messages"/>
<%--<fmt:setBundle basename="resources.Messages"/>--%>
<%--<fmt:setBundle basename="Messages"/>--%>
<html lang="${language}">
<head>
    <title><fmt:message key="index.title"/></title>

</head>
<body>
asdasd
<FORM action="AddDocument" method="get">
    <fmt:message key="index.add"/>

    <fmt:message key="index.add" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</FORM>
</body>
</html>

