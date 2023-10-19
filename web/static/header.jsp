<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE>
<html lang="it">
<head>
    <link rel="stylesheet" href="./static/styles/header.css">
    <title>Header</title>
</head>
<body>
    <div style="background-color: indianred">

            <img class="logo" src="./static/img/logo_placeholder.png" alt="cool logo" onclick="window.location.replace('index.jsp')">

        <form method="post" action="#">
            <input class="searchbar" type="text" placeholder="BARRA DI RICERCA">
        </form>

        <input type="button" value="Carrello" onclick="location.href = 'carrello.jsp'">

        <%
            Boolean isLogged = (Boolean) session.getAttribute("isLogged");
            if(isLogged == null || !isLogged) {
                session.setAttribute("isLogged", Boolean.FALSE);
        %>
        <form method="post" action="login.jsp">
            <input type="submit" value="Login">
        </form>
        <%
            }
            else {
                Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
                if (!isAdmin) {
        %>
                    Benvenuto
        <%      }
                else {
        %>
                    Amministratore
        <%
                }
        %>
        <%=session.getAttribute("username")%>

        <form method="post" action="Logout">
            <input type="submit" value="Logout">
        </form>
        <%
            }
        %>
    </div>
</body>
</html>
