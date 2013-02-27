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
<fmt:setBundle basename="Messages"/>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <fmt:message key="document.wanttodelete" var="deletion"/>
    <script type="text/javascript">
      function deleteA(){
        var retVal = confirm("${deletion}");
        if( retVal == true ){
            return true;
        }else{
            return false;
        }
      }
    </script>
</head>
<body>

    <c:if test="${not empty docmessage}">
        <h1>${docmessage}</h1>
    </c:if>

    <table id="pattern-style-b">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="alldocuments.name"/></th>
            <th scope="col"><fmt:message key="alldocuments.description"/></th>
            <th scope="col">&nbsp</th>
        </tr>
        </thead>

        <c:forEach items="${documentList}" var="item">
            <tr>
                <td><a href="<c:url value="Versions">
                <c:param name="docVers" value="${item.name}"/>
                </c:url>">${item.name}</a></td>
                <td>${item.description}</td>
                <td><a onclick="return deleteA()"
                       href="<c:url value="DeleteDocument">
                    <c:param name="docDel" value="${item.name}"/>
                </c:url>"><fmt:message key="delete"/></a></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>