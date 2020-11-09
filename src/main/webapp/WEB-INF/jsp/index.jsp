<%--
  Created by IntelliJ IDEA.
  User: guigu
  Date: 06/11/2020
  Time: 09:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Welcome <sec:authentication property="username"/></h1>

<sec:authorize access="! isAuthenticated()">
    <a href="/login">Log in</a><br/>
    <a href="/register">Create a User Account</a><br/>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <a href="/changeUser">Change User Account</a><br/>
    <a href="/listUsers">List User Accounts</a>
</sec:authorize>

<a href="/doLogout">Logout</a>
</body>
</html>
