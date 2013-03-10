<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 26.02.13
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="Messages"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <fmt:message key="version.wanttodelete" var="deletion"/>
    <script type="text/javascript">
        function deleteA() {
            var retVal = confirm("${deletion}");
            if (retVal == true) {
                return true;
            } else {
                return false;
            }
        }
    </script>
</head>
<body>

<c:if test="${not empty versmessage}">
    <h1><fmt:message key="${versmessage}"/></h1>
</c:if>

<c:if test="${not empty versionList}">
    <table id="userDocs" summary="Meeting Results">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="versions.date"/></th>
            <th scope="col"><fmt:message key="versions.author"/></th>
            <th scope="col"><fmt:message key="versions.description"/></th>
            <th scope="col"><fmt:message key="versions.type"/></th>
            <th scope="col"><fmt:message key="versions.type"/></th>
            <th scope="col">&nbsp</th>
            <th scope="col">&nbsp</th>
        </tr>
        </thead>

        <c:forEach items="${versionList}" var="item">

            <tr>
                <td>${item.date}</td>
                <td>${item.author.login}</td>
                <td>${item.description}</td>
                <td>${item.versionType}</td>
                <td>${item.released}</td>
                <td><a href="<c:url value="Download">
                    <c:param name="version" value="${item.versionName}"/>
                </c:url>"><fmt:message key="versions.download"/></a></td>
                <td><a onclick="return deleteA()"
                       href="<c:url value="DeleteVersion">
                    <c:param name="version" value="${item.versionName}"/>
                </c:url>"><fmt:message key="delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:set var="document" value="${param.document}"/>
<form action="/AddVersion" method="post">
    <%--<input type="hidden" value="${param.document}" name="document"/>--%>
    <fmt:message key="version.addNewVersion" var="butName"/>
    <input type="submit" name="submit" value="${butName}"/>
</form>
</body>
</html>