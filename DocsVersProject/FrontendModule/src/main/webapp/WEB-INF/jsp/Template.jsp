<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="Messages"/>
<!DOCTYPE HTML>
<html>
<head>
    <title>${param.title}</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/Header.jsp"/>

<h1>${param.title}</h1>

<jsp:include page="/WEB-INF/jsp/${param.content}.jsp"/>

<jsp:include page="/WEB-INF/jsp/Footer.jsp"/>

</body>
</html>