<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 24.02.13
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="Messages"/>
<!doctype html>
<html lang="${language}">
<head>
    <meta charset="utf8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><fmt:message key="login.login"/></title>
    <link rel="stylesheet" href="css/login.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js?ver=1.4.2"></script>
    <script src="js/login.js"></script>
</head>
<body>
<div id="bar">
    <div id="container">
        <form>
            <c:url value="Login" var="ruURL">

                <c:param name="language" value="ru"/>

            </c:url>

            <a href="${ruURL}" onclick="submit()"> <img src="\images\russia-icon.png"/> </a>
            <c:url value="Login" var="enURL">

                <c:param name="language" value="en_US"/>

            </c:url>

            <a href="${enURL}" onclick="submit()"> <img src="images\uk-icon.png"/> </a>
        </form>
        <c:if test="${not empty logmessage}">
            <h1><fmt:message key="${logmessage}"/></h1>
        </c:if>
        <!-- Login Starts Here -->
        <div id="loginContainer">
            <a href="#" id="loginButton"><span><fmt:message key="login.login"/></span><em></em></a>

            <div style="clear:both"></div>
            <div id="loginBox">
                <%
                    String urlParams = "http://localhost:8080/LoginServlet?cLogin";
                %>
                <form id="loginForm" action="<%=urlParams%>" method="POST" name="form">
                    <fieldset id="body">
                        <fieldset>
                            <label for="login"><fmt:message key="login.enterLogin"/></label>
                            <input type="text" required name="login" id="email"/>
                        </fieldset>
                        <fieldset>
                            <label for="password"><fmt:message key="login.password"/></label>
                            <input type="password" required name="password" id="password"/>
                        </fieldset>
                        <fmt:message key="login.login" var="buttonValue"/>
                        <input type="submit" id="login" value="${buttonValue}"/>
                        <label for="checkbox"><input type="checkbox" id="checkbox"/><fmt:message key="login.remember"/></label>
                    </fieldset>
                    <span><a href="#"><fmt:message key="login.forgotPasswor"/></a></span>
                </form>
            </div>
        </div>
        <!-- Login Ends Here -->
    </div>
</div>
</body>
</html>