<%@ page import="java.util.List" %>
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

<%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if(errors != null) {
        for (String error : errors) { %>
            <%=error%> <br>
        <%
        }
    }
%>
<form class="login" method="post" action="Login">
    <label for="username">Username: </label>
    <input type="text" name="username" id="username"> <br>

    <label for="password">Password: </label>
    <input type="password" name="password" id="password"> <br>
    Non hai un'account? <a href="register.jsp">Registrati</a> <br>
    <input type="submit" value="Login">
</form>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>