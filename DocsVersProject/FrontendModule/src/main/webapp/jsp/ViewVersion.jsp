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

        <form action="Versions">

            <fmt:message key="version.allVersions" var="back"/>
            <input type="submit" class="button" value="${back}"/>
        </form>
        <c:if test="${not empty version}">
                <c:set value="${version.document.name}" var="docName"></c:set>
            <h1 align="center">${docName}</h1>
            <table class="userDocs1">
                <thead>
                <tr>
                    <th scope="col">№</th>

                    <th scope="col"><fmt:message key="versions.date"/></th>
                    <th scope="col"><fmt:message key="versions.author"/></th>
                    <th scope="col"><fmt:message key="versions.type"/></th>
                    <th scope="col" style="width: 111px;">&nbsp</th>
                    <th scope="col" style="width: 103px;">&nbsp</th>
                    <th scope="col" >&nbsp</th>
                    <th scope="col" style="width: 100px;">&nbsp</th>
                </tr>
                </thead>
                <tr>
                    <td>${version.versionName}</td>
                    <td><fmt:formatDate value="${version.date}" type="both" timeStyle="short"/></td>
                    <td>${version.author.login}</td>
                    <td>${version.versionType}</td>
                    <td><a href="<c:url value="Action">
                    <c:param name="version" value="${version.versionName}"/>
                    <c:param name="author" value="${version.document.author.login}"/>
                    <c:param name="codeDocument" value="${version.document.codeDocumentName}"/>
                </c:url>"><fmt:message key="versions.download"/></a></td>
                    <td><a onclick="return deleteA()"
                           href="<c:url value="DeleteVersion">
                    <c:param name="version" value="${version.versionName}"/>
                </c:url>"><fmt:message key="delete"/></a></td>
                    <td style="width: 109px; height: 90px;"><a href="<c:url value="Action">
                    <c:param name="version" value="${version.versionName}"/>
                    <c:param name="author" value="${version.document.author.login}"/>
                    <c:param name="codeDocument" value="${version.document.codeDocumentName}"/>
                    <c:param name="param" value="getLink"/>
                    <c:param name="from" value="View"/>
                </c:url>"><fmt:message key="getLink"/></a></td>
                    <c:if test="${not version.released}">
                        <td><a href="<c:url value="GetEditVersion">
                    <c:param name="version" value="${version.versionName}"/>
                </c:url>"><fmt:message key="version.edit"/></a></td>
                    </c:if>

                </tr>
            </table>
            <table class="userDocs1">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="versions.description"/></th>
                </tr>
                </thead>
                <tbody style="">
                <tr id="description">
                    <td align="left">${version.description}</td>
                </tr>
                </tbody>

            </table>
        </c:if>

    </jsp:body>
</t:TemplatePage>