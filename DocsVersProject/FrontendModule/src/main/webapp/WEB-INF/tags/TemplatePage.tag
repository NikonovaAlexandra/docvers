<%@tag description="Overall Page template" pageEncoding="UTF-8" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<!DOCTYPE HTML>
<html lang="${language}">
<head>
    <title>${param.title}</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
<jsp:include page="/jsp/Header.jsp"/>
<div id="body">
    <jsp:doBody/>
</div>

<jsp:include page="/jsp/Footer.jsp"/>
</body>
</html>