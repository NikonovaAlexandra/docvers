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
        <form action="Versions">

            <fmt:message key="version.allVersions" var="back"/>
            <input type="submit" class="button" value="${back}"/>
        </form>
        <c:set var="document" value="${param.document}"/>
        <c:if test="${not empty version}">
            <c:set value="${version.document.name}" var="docName"></c:set>
            <h1 align="center">${docName}</h1>

            <form action="EditVersion" method="post">
                <table class="userDocs">
                    <thead>
                    <tr>
                        <th scope="col">№</th>

                        <th scope="col" style="width: 15%"><fmt:message key="versions.date"/></th>
                        <th scope="col"><fmt:message key="versions.author"/></th>
                        <th scope="col"><fmt:message key="versions.type"/></th>
                        <th scope="col" style="width: 10%">&nbsp</th>
                        <th scope="col" style="width: 9%">&nbsp</th>
                        <th scope="col" style="width: 9%">&nbsp</th>
                        <th scope="col" style="width: 9%">&nbsp</th>
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
                        <td><a href="<c:url value="Action">
                    <c:param name="version" value="${version.versionName}"/>
                    <c:param name="author" value="${version.document.author.login}"/>
                    <c:param name="codeDocument" value="${version.document.codeDocumentName}"/>
                    <c:param name="param" value="getLink"/>
                    <c:param name="from" value="Edit"/>
                </c:url>"><fmt:message key="getLink"/></a></td>
                        <td><a href="<c:url value="ViewVersion">
                    <c:param name="version" value="${version.versionName}"/>
                </c:url>"><fmt:message key="version.view"/></a></td>
                    </tr>
                </table>
                <table class="userDocs">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="versions.description"/></th>
                    </tr>
                    </thead>

                </table>

                <textarea name="description" rows="10" cols="5"
                          style="margin-left: 50px; width: 700px;">${version.description}</textarea>
                <div><input type="hidden" name="version" value="${version.versionName}"/>
                <fmt:message key="versions.save" var="buttonValue"/>
                <input name="submit" type="submit" class="button" value="${buttonValue}">
                </div>
            </form>
        </c:if>

    </jsp:body>
</t:TemplatePage>