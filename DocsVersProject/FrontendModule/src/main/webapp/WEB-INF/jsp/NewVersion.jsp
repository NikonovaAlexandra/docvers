<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 01.03.13
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="Messages"/>
<html>
<head>
    <title><fmt:message key="version.upload.title"/></title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<h3><fmt:message key="version.upload"/>:</h3>
<fmt:message key="version.upload.select"/>: <br/>

<form action="Upload" method="post" enctype="multipart/form-data">
    <input type="hidden" name="document" value="${param.document}"/>
    <table>
        <tr>
            <td>
                <fmt:message key="version.upload.uploadBut" var="butName"/>
            </td>
            <td>
                <input type="file" name="file" size="50"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="version.upload.versdescription"/></td>
            <td><textarea name="versdescription"></textarea></td>
        </tr>
    </table>

    <br/>

    <input type="submit" value="${butName}"/>
</form>
</body>
</html>