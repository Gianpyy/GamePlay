<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <link rel="stylesheet" href="./static/styles/header.css">
    <title>Profilo</title>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>

<h1>Area utente di <%=session.getAttribute("username")%></h1>

<form method="post" action="edit_password.jsp">
    <input type="submit" value="Modifica password">
</form>

<form method="post" action="#">
    <input type="submit" value="Modifica profilo">
</form>

<form method="post" action="#">
    <input type="submit" value="Cronologia ordini">
</form>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
