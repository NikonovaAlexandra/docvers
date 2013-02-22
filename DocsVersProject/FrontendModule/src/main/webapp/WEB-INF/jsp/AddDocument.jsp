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
<fmt:setBundle basename="Messages"/>
<html lang="${language}">
<head>
    <fmt:message key="adddocument.blankName" var="emptyName"/>
    <script>
        function validateForm() {
            if (document.form.docname.value == "") {
                alert("${emptyName}");
                document.form.docname.focus();
                return false;
            }
        }
    </script>
    <title><fmt:message key="adddocument.tittle"/></title>
</head>
<body>
<div>
    <c:if test="${not empty message}">
        <h1>${message}</h1>
    </c:if>
</div>
<div>
    <form action="Document" method="post" name="form" onSubmit="return validateForm()">
        <table>
            <tr>
                <td><fmt:message key="adddocument.docname"/></td>
                <td><input name="docname" type="text"/></td>
            </tr>
            <tr>
                <td><fmt:message key="adddocument.docdescription"/></td>
                <td><textarea name="docdescription" rows="3"></textarea></td>
            </tr>
            <tr>
                <td><fmt:message key="addocument.add" var="buttonValue"/>
                    <input type="submit" name="submit" value="${buttonValue}"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>