<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 14.02.13
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources.Messages" />
<html lang="${language}">
<head>
    <title><fmt:message key="adddocument.tittle"/></title>
</head>
<body>
<div>
    <c:if test="${not empty message}">
        <h1>${message}</h1>
    </c:if>
</div>
<div>
    <form action="Document" method="post">
    <table>
        <tr>
            <td><fmt:message key="adddocument.docname"/> </td>
            <td><input name = "docname" type="text"/></td>
        </tr>
        <tr>
            <td><fmt:message key="adddocument.docdescription"/></td>
            <td><textarea name = "docdescription" rows="3"></textarea> </td>
        </tr>
        <tr>
            <td><fmt:message key="addocument.add" var="buttonValue" />
                <input type="submit" name="submit" value="${buttonValue}"></td>
        </tr>
    </table>
    </form>
</div>
</body>
</html>