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

<form method="post" action="cronologia_ordini.jsp">
    <input type="submit" value="Cronologia ordini">
</form>

<!-- DA RIMUOVERE APPENA RIESCO A FARE IL MENU A TENDINA PER L'UTENTE NELL'HEADER -->
<form method="post" action="Logout">
    <input type="submit" value="Logout">
</form>

<%
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin) { %>
<form method="post" action="#">
    <input type="submit" value="Gestione prodotti">
</form>

<form method="post" action="#">
    <input type="submit" value="Gestione ordini">
</form>
<%
    }
%>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
