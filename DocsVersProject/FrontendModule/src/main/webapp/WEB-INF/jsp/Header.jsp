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

<div id="header">
    <form>
        <c:url value="${pageContext.request.requestURL}" var="ruURL">

            <c:param name="language" value="ru_RUS"/>

        </c:url>

        <a href="${ruURL}" onclick="submit()"> <img src="flags/russia-icon.png"/> </a>
        <c:url value="${pageContext.request.requestURL}" var="enURL">

            <c:param name="language" value="en_US"/>

        </c:url>

        <a href="${enURL}" onclick="submit()"> <img src="flags/uk-icon.png"/> </a>
    </form>


    <ul id="navigation">
        <li class="home"><a href="AddDocument"><fmt:message key="index.add"/></a></li>
        <li class="about"><a href="GetAllDocuments"><fmt:message key="index.getAll"/></a></li>
        <li class="search"><a href="Logout"><fmt:message key="logout"/></a></li>
    </ul>
</div>

<!-- The JavaScript -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script type="text/javascript">
    $(function () {
        var d = 300;
        $('#navigation a').each(function () {
            var $this = $(this);
            $this.stop().animate({
                'marginTop': '-50px'
            }, d += 150);
        });
        $('#navigation > li').hover(
                function () {
                    var $this = $(this);
                    $('a', $this).stop().animate({
                        'marginTop': '-20px'
                    }, 200);
                },
                function () {
                    var $this = $(this);
                    $('a', $this).stop().animate({
                        'marginTop': '-50px'
                    }, 200);
                });
    });
</script>