<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User page</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<c:set var="currentLogin" scope="session" value="${login}"/>
<div class="row">
    <div class="mx-auto">
        <div class="input-group mb-3">
            <div class="card">
                <article class="card-body">
                    <h1 align="center">Hello, ${currentLogin}!</h1>
                    <p align="center">Click <a href="/logout">here</a> to logout</p>
                </article>
            </div>
        </div>
    </div>
</div>
</body>
</html>
