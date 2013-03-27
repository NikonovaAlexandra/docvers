<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 01.03.13
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:requestEncoding value="UTF-8"/>
<fmt:setBundle basename="Messages"/>

<t:TemplatePage>
    <jsp:body>
        <hr>
        <h3><fmt:message key="version.upload"/>:</h3>
        <fmt:message key="version.upload.select"/>: <br/>

        <form action="Upload" method="post" enctype="multipart/form-data" accept-charset="UTF-8">

            <table>
                <tr>
                    <td>
                        <fmt:message key="version.upload.uploadBut" var="butName"/>
                    </td>
                    <td>
                        <input type="file" name="file" value="${butName}" size="100"/>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="version.upload.versdescription"/></td>
                    <td><textarea name="versdescription"></textarea></td>
                </tr>
            </table>
            <br/>
            <input type="submit" class="button" value="${butName}"/>

        </form>
        <form action="Versions" method="get">

            <fmt:message key="backBut" var="back"/>
            <input type="submit" class="button" value="${back}"/>
        </form>
        <hr>
    </jsp:body>
</t:TemplatePage>
