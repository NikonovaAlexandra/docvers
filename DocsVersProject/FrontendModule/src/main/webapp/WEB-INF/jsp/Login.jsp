<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 24.02.13
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="Messages"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <script>
        function validateForm() {
            if (document.form.login.value == "") {
                alert("${emptyName}");
                document.form.login.focus();
                return false;
            }
            if (document.form.password.value == "") {
                alert("${emptyName}");
                document.form.password.focus();
                return false;
            }
        }
    </script>
</head>
<body>
<div>
    <c:if test="${not empty logmessage}">
        <h1>${logmessage}</h1>
    </c:if>
</div>
<div>
    <%
        String urlParams = "http://localhost:8080/LoginServlet?cLogin";
    %>
    <form action="<%=urlParams%>" method="POST" name="form" onSubmit="return validateForm()">
        <table>
            <tr>
                <td><fmt:message key="login.enterLogin"/></td>
                <td><input name="login" type="text"/></td>
            </tr>
            <tr>
                <td><fmt:message key="login.password"/></td>
                <td><input type="password" name="password"/></td>
            </tr>
            <tr>
                <td><fmt:message key="login.login" var="buttonValue"/>
                    <input type="submit" name="submit" value="${buttonValue}"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>