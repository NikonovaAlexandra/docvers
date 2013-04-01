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

<t:TemplatePage>
    <jsp:body>
        <form action="GetAllDocuments">
            <fmt:message key="backBut" var="back"/>
            <input type="submit" class="button" value="${back}"/>
        </form>
        <c:if test="${not empty documentItem}">
            <table class="userDocs">
                <thead>
                <tr>
                    <th scope="col" style="width: 300px;"><fmt:message key="alldocuments.name"/></th>
                    <th scope="col" style="width: 300px;">&nbsp</th>
                    <th scope="col" style="width: 300px;">&nbsp</th>
                </tr>
                </thead>


                    <tr id="${documentItem.codeDocumentName}">
                        <td><a href="<c:url value="Versions">
                <c:param name="document" value="${documentItem.codeDocumentName}"/>
                </c:url>">${documentItem.name}</a></td>
                        <td><a href="<c:url value="DeleteDocument">
                    <c:param name="document to delete" value="${documentItem.codeDocumentName}"/>
                </c:url>"><fmt:message key="delete"/></a></td>
                        <td><a href="<c:url value="GetEditDocument">
                    <c:param name="document to view" value="${documentItem.codeDocumentName}"/>
                </c:url>"><fmt:message key="version.edit"/></a></td>
                    </tr>

            </table>
            <table class="userDocs">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="alldocuments.description"/></th>
                </tr>
                </thead>
                <tbody>
                <tr id="description">
                    <td align="left">${documentItem.description}</td>
                </tr>
                </tbody>

            </table>
        </c:if>

    </jsp:body>
</t:TemplatePage>