<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="static/styles/styles.css">
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>

<form class="login" method="post" action="#">
    <label for="username">Username: </label>
    <input type="text" id="username"> <br>

    <label for="password">Password: </label>
    <input type="password" id="password"> <br>
    Non hai un'account? <a href="register.jsp">Registrati</a> <br>
    <input type="submit" value="Login">
</form>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
