<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%--<%@ page import="right.model.Function_o" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AAA API</title>
    <jsp:include page="/resources/css.inc"></jsp:include>
    <jsp:include page="/resources/js.inc"></jsp:include>
    <script type="text/javascript" >

        var titleInfo="提示信息";
    </script>
    <script type="text/javascript" src="resources/js/sys/init_list.js"></script>
</head>
<body class="easyui-layout">
<!--north-->
    <%response.sendRedirect("/swagger/index.html"); %>
    <h2>hello,world</h2>
</body>
</html>