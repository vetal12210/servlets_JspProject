<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit user</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body onLoad="myFunction()">

<c:set var="currentLogin" scope="session" value="${login}"/>
<c:set var="editUser" scope="request" value="${editUser}"/>
<c:set var="matchPasswords" scope="request" value="${matchPasswords}"/>
<c:set var="emailExist" scope="request" value="${emailExist}"/>
<c:set var="birthdayFuture" scope="request" value="${birthdayFuture}"/>

<p align="right">Admin ${currentLogin} <a href="/logout">(Logout)</a></p>
<script>
    function myFunction() {
        document.getElementById("mySelect").value = "${editUser.role}";
    }
</script>

<form method="post" action="/admin/edit-user">
    <div class="row">
        <div class="mx-auto">
            <h1>Edit user</h1>
            <p>Fill out the form (all fields are required)</p>

            <table class="table">
                <tr>
                    <td> Login:</td>
                    <td><input value="${editUser.login}" type="text" name="login" readonly="true"></td>
                </tr>
                <tr>
                    <td> Password:</td>
                    <td><input value="" type="Password" name="password"></td>
                </tr>
                <tr>
                    <td> Password again:</td>
                    <td><input value="" type="Password" name="password-repeat"> ${matchPasswords}</td>
                </tr>
                <tr>
                    <td> Email Address:</td>
                    <td><input value="${editUser.email}" type="email" name="email" required>${emailExist}</td>
                </tr>
                <tr>
                    <td> First name:</td>
                    <td><input value="${editUser.firstName}" type="text" name="firstName" required></td>
                </tr>
                <tr>
                    <td> Last name:</td>
                    <td><input value="${editUser.lastName}" type="text" name="lastName" required></td>
                </tr>
                <tr>
                    <td> Birthday:</td>
                    <td><input value="${editUser.birthday}" type="date" name="birthday" required>${birthdayFuture}</td>
                </tr>
                <tr>
                    <td> Role:</td>
                    <td>
                        <select id="mySelect" name="role">
                            <option value="1">admin</option>
                            <option value="2">user</option>
                        </select>
                    </td>
                </tr>
            </table>
            <button type="submit" class="registerbtn btn-primary">Edit</button>
        </div>
    </div>
</form>
<form action="/admin">
    <div class="row">
        <div class="mx-auto">
            <button type="submit" class="cancel btn-danger">Cancel</button>
        </div>
    </div>
</form>
</body>
</html>
