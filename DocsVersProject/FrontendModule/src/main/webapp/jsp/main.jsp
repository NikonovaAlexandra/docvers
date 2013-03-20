<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 25.02.13
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setBundle basename="Messages"/>

<head>
    <title><fmt:message key="index.title"/></title>
</head>
<t:TemplatePage>
    <jsp:body>
        <FORM action="AddDocument" method="post">
            <fmt:message key="index.add" var="buttonValue"/>
            <input type="submit" name="submit" class="button" value="${buttonValue}">
        </FORM>
        <FORM action="GetAllDocuments" method="get">
            <fmt:message key="index.getAll" var="buttonValue"/>
            <input type="submit" name="submit" class="button" value="${buttonValue}">
        </FORM>
        <FORM action="Logout" method="post">
            <input type="submit" name="submit" class="button" value="Logout">
        </FORM>
    </jsp:body>
</t:TemplatePage>
