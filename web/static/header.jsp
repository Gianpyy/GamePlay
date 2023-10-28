<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE>
<html lang="it">
<head>
    <link rel="stylesheet" href="./static/styles/styles.css">
    <title>Header</title>
</head>
<body>
<%
    Boolean isLogged = (Boolean) session.getAttribute("isLogged");
%>
<div class="col-md-3 mb-2 mb-md-0"> <%-- Logo --%>
    <img class="logo" src="./static/img/logo_placeholder.png" alt="cool logo" onclick="window.location.replace('index.jsp')">
</div>

<%-- Barra di ricerca --%>
<form class="col-12 col-md-5 mb-3 mb-lg-0 me-lg-3" role="search">
    <input type="search" class="form-control" placeholder="Cerca un prodotto..." aria-label="Search">
</form>

<div class="col-md-3 text-end"> <%-- Bottone carrello ed utente --%>
    <%-- Bottone carrello --%>
    <button type="button" class="btn btn-primary" onclick="window.location.href = 'carrello.jsp' ">
        <i class="bi bi-cart2"></i> Carrello
    </button>

    <%
        if(isLogged == null || !isLogged) {
            session.setAttribute("isLogged", Boolean.FALSE); %>
    <button type="submit" class="btn btn-primary" onclick="window.location.href = 'login.jsp'">
        <i class="bi bi-person-circle"></i> Login
    </button>
    <% }
    else {
        String username = (String) session.getAttribute("username"); %>
    <button type="submit" class="btn btn-primary" onclick="window.location.href = 'profile.jsp'">
        <i class="bi bi-person-circle"></i> <%=username%>
    </button>
    <%
        }
    %>
</div>
</body>
</html>
