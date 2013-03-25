<%--
  Created by IntelliJ IDEA.
  User: alni
  Date: 25.02.13
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setBundle basename="Messages"/>

<head>
    <title><fmt:message key="index.title"/></title>
    <script type='text/javascript' src='http://code.jquery.com/jquery-1.8.3.js'></script>

    <style type='text/css'>
        .wrapper {
            padding: 1em;
        }
    </style>



    <script type='text/javascript'>//<![CDATA[
    $(window).load(function(){
//plugin to make any element text editable
        $.fn.extend({
            editable: function() {
                var that = this,
                        $edittextbox = $('<input type="text"></input>').css('min-width', that.width()),
                        submitChanges = function() {
                            if ($edittextbox.val() !== '') {
                                that.html($edittextbox.val());
                                that.show();
                                that.trigger('editsubmit', [that.html()]);
                                $(document).unbind('click', submitChanges);
                                $edittextbox.detach();
                            }
                        },
                        tempVal;
                $edittextbox.click(function(event) {
                    event.stopPropagation();
                });

                that.dblclick(function(e) {
                    tempVal = that.html();
                    $edittextbox.val(tempVal).insertBefore(that).bind('keypress', function(e) {
                        var code = (e.keyCode ? e.keyCode : e.which);
                        if (code == 13) {
                            submitChanges();
                        }
                    }).select();
                    that.hide();
                    $(document).click(submitChanges);
                });
                return that;
            }
        });

//implement plugin
        $('.text-content').editable().bind('editsubmit', function(event, val) {
            console.log('text changed to ' + val);
            alert("chhhh");
        })
    });//]]>

    </script>


</head>
<body>
<%--<t:TemplatePage>--%>
    <%--<jsp:body>--%>
        <FORM action="AddDocument" method="post">
            <fmt:message key="index.add" var="buttonValue"/>
            <input type="submit" name="submit" class="button" value="${buttonValue}">
        </FORM>
        <FORM action="GetAllDocuments" method="get">
            <fmt:message key="index.getAll" var="buttonValue"/>
            <input type="submit" name="submit" class="button" value="${buttonValue}">
        </FORM>
        <FORM action="Logout" method="post">
            <input type="submit" name="submit" class="button" value="Logout">
        </FORM>

<div class="wrapper">
    <span class="text-content">Double Click On Me!</span>
</div>

</body>
