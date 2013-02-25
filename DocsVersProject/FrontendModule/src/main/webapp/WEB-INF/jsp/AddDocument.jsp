<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 14.02.13
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
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
        <link rel="stylesheet" type="text/css" href="style.css" />

<meta http-equiv="Content-Type" content="text/html; charset=windows-1251"></head>
<body>
<div>
    <c:if test="${not empty addmessage}">
        <h1>${addmessage}</h1>
    </c:if>
</div>
<div><hr>
    <form action="Document" method="post" name="form" onSubmit="return validateForm()">
        <table>
            <tr>
              <td width="103" height="32"><fmt:message key="adddocument.docname"/></td>
                <td width="161"><input name="docname" type="text"/></td>
            </tr>
            <tr>
                <td><fmt:message key="adddocument.docdescription"/></td>
                <td><textarea name="docdescription"></textarea></td>
            </tr>
            <tr>
                <td><fmt:message key="addocument.add" var="buttonValue"/>
                    <input name="submit" type="submit" value="${buttonValue}"></td>
            </tr>
        </table>
    </form><hr>
</div>
</body>
</html>