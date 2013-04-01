<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 22.02.13
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="Messages"/>

<head>
    <fmt:message key="document.wanttodelete" var="deletion"/>
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
        <c:if test="${not empty documentList}">
            <table class="userDocs">
                <thead>
                <tr>
                    <th scope="col" style="width: 200px;"><fmt:message key="alldocuments.name"/></th>
                    <th scope="col" style="width: 190px;"><fmt:message key="alldocuments.description"/></th>
                    <th scope="col" style="width: 120px;">&nbsp</th>
                    <th scope="col" style="width: 170px;">&nbsp</th>
                    <th scope="col">&nbsp</th>
                </tr>
                </thead>

                <c:forEach items="${documentList}" var="item">
                    <tr id="${item.codeDocumentName}">
                        <td><a href="<c:url value="Versions">
                <c:param name="document" value="${item.codeDocumentName}"/>
                </c:url>">${item.name}</a></td>
                        <td><div class="description"> ${item.description}</div></td>
                        <td><a onclick="return deleteA()"
                               href="<c:url value="DeleteDocument">
                    <c:param name="document to delete" value="${item.codeDocumentName}"/>
                </c:url>"><fmt:message key="delete"/></a></td>
                        <td><a href="<c:url value="ViewDocumentServlet">
                    <c:param name="document to view" value="${item.codeDocumentName}"/>
                </c:url>"><fmt:message key="version.view"/></a></td>
                        <td><a href="<c:url value="GetEditDocument">
                    <c:param name="document to view" value="${item.codeDocumentName}"/>
                </c:url>"><fmt:message key="version.edit"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:set var="item" value="${sessionScope.documentToView}"/>
        <%--<FORM action="AddDocument" method="post">--%>
        <%--<fmt:message key="index.add" var="buttonValue"/>--%>
        <%--<input type="submit" name="submit" class="button" value="${buttonValue}">--%>
        <%--</FORM>--%>
    </jsp:body>
</t:TemplatePage>

