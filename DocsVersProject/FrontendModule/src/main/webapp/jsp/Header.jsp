<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10.03.13
  Time: 23:06
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="Messages"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Animated Label Navigation Menu</title>

    <link href="css/menu.css" rel="stylesheet" type="text/css" media="screen"/>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.min.js" type="text/javascript"></script>
    <script src="js/animate-bg.js" type="text/javascript"></script>
    <script src="js/scripts.js" type="text/javascript"></script>
    <script type="text/javascript">
        function refreshpage()
        {
//            alert("yoho");
            window.location.reload(false);
        }
    </script>
</head>

<body>
<div id="message">
    <c:if test="${not empty addmessage}">
        <h1><fmt:message key="${addmessage}"/></h1>
    </c:if>
    <c:if test="${not empty docmessage}">
        <h1><fmt:message key="${docmessage}"/></h1>
    </c:if>
    <c:if test="${not empty uploadmessage}">
        <h1><fmt:message key="${uploadmessage}"/></h1>
    </c:if>
    <c:if test="${not empty versmessage}">
        <h1><fmt:message key="${versmessage}"/></h1>
    </c:if>
</div>

<div id="container">
    <ul id="nav">
        <li class="GetAllDocs"><a href="GetAllDocuments"><fmt:message key="index.getAll"/></a></li>
        <li class="AddDoc"><a href="AddDocument"><fmt:message key="index.add"/></a></li>
        <li class="Logout"><a href="Logout"><fmt:message key="logout"/></a></li>

      <form onsubmit="refreshpage()">
            <c:set var="document" value="${param.document}"/>
            <c:url value="" var="ruURL">
                <c:if test="${not empty document}">
                    <c:param name="document" value="${document}"/>
                </c:if>

                <c:param name="language" value="ru_RUS"/>
            </c:url>

            <a href="${ruURL}" onclick="refreshpage()"> <img src="images/russia-icon.png"/> </a>
            <c:url value="${url}" var="enURL">
                <c:if test="${not empty document}">
                    <c:param name="document" value="${document}"/>
                </c:if>
                <c:param name="language" value="en_US"/>

            </c:url>

            <a href="${enURL}" onclick="refreshpage()"> <img src="images/uk-icon.png"/> </a>
      </form>
    </ul>
    <hr>
</div>


</body>
</html>