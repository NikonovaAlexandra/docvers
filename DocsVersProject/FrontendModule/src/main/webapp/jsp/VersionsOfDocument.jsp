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
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--<fmt:setLocale value="ru_RUS"></fmt:setLocale>--%>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setBundle basename="Messages"/>

<head>
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
<t:TemplatePage>
    <jsp:body>
        <c:set var="document" value="${param.document}"/>

        <form action="GetAllDocuments">

            <fmt:message key="backBut" var="back"/>
            <input type="submit" class="button" value="${back}"/>
        </form>
        <form action="/AddVersion" method="post">
            <fmt:message key="version.addNewVersion" var="butName"/>
            <input type="submit" name="submit" class="button" value="${butName}"/>
        </form>
        <br>
        <c:if test="${not empty versionList}">
            <c:forEach items="${versionList}" var="item">
                <c:set value="${item.document.name}" var="docName"></c:set>
            </c:forEach>
            <h1 align="center">${docName}</h1>
            <table class="userDocs" summary="Meeting Results">
                <thead>
                <tr>
                    <th scope="col" style="width: 37px;">№</th>

                    <th scope="col" style="width: 96px;"><fmt:message key="versions.date"/></th>
                    <th scope="col" style="width: 85px;"><fmt:message key="versions.author"/></th>
                    <th scope="col" style="width: 67px;"><fmt:message key="versions.type"/></th>
                    <th scope="col" style="width: 95px;">&nbsp</th>
                    <th scope="col" style="width: 87px;">&nbsp</th>
                    <th scope="col" style="width: 100px;">&nbsp</th>
                    <th scope="col" style="width: 109px;">&nbsp</th>
                    <th scope="col" style="width: 77px;">&nbsp</th>
                </tr>
                </thead>
                <c:forEach items="${versionList}" var="item">
                    <tr>
                        <td>${item.versionName}</td>
                        <td><fmt:formatDate value="${item.date}" type="both" timeStyle="short"/></td>
                        <td>${item.author.login}</td>
                        <td>${item.versionType}</td>
                        <td><a href="<c:url value="Action">
                    <c:param name="version" value="${item.versionName}"/>
                    <c:param name="author" value="${item.document.author.login}"/>
                    <c:param name="codeDocument" value="${item.document.codeDocumentName}"/>
                </c:url>"><fmt:message key="versions.download"/></a></td>
                        <td><a onclick="return deleteA()"
                               href="<c:url value="DeleteVersion">
                    <c:param name="version" value="${item.versionName}"/>
                </c:url>"><fmt:message key="delete"/></a></td>
                        <td style="width: 109px; height: 90px;"><a href="<c:url value="Action">
                    <c:param name="version" value="${item.versionName}"/>
                    <c:param name="author" value="${item.document.author.login}"/>
                    <c:param name="codeDocument" value="${item.document.codeDocumentName}"/>
                    <c:param name="param" value="getLink"/>
                    <c:param name="from" value="Versions"/>
                </c:url>"><fmt:message key="getLink"/></a></td>
                        <td><a href="<c:url value="ViewVersion">
                    <c:param name="version" value="${item.versionName}"/>
                </c:url>"><fmt:message key="version.view"/></a></td>
                        <c:if test="${not item.released}">
                            <td><a href="<c:url value="GetEditVersion">
                    <c:param name="version" value="${item.versionName}"/>
                </c:url>"><fmt:message key="version.edit"/></a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </jsp:body>
</t:TemplatePage>