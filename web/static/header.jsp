<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <link rel="stylesheet" href="./static/styles/styles.scss">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <%-- Bootstrap icons   --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
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
<form id="searchForm" class="col-12 col-md-5 mb-3 mb-lg-0 me-lg-3" role="search">
    <input type="search" name="searchbar" class="form-control" placeholder="Cerca un prodotto..." aria-label="Search">
</form>

<div class="col-md-3 text-end" id="bottoniHeader"> <%-- Bottone carrello ed utente --%>
    <%-- Bottone carrello --%>
    <%  Boolean carrelloEmpty = (Boolean) request.getSession().getAttribute("isCarrelloEmpty");
        int numOfProducts = 0;
        //Se c'è un carrello, conto i prodotti
        if(carrelloEmpty != null && Boolean.FALSE.equals(carrelloEmpty)) {
            //Recupero il contatore dei prodotti
            HashMap<String, Integer> prodottiCount = (HashMap<String, Integer>) request.getSession().getAttribute("prodottiCounter");

            //Conto i prodotti
            for (int i : prodottiCount.values()) {
                numOfProducts += i;
            }
        }
    %>
    <button type="button" class="btn btn-outline-dark flex-shrink-0" onclick="window.location.href = 'carrello.jsp' ">
        <i class="bi bi-cart2"></i> Carrello <% if (numOfProducts > 0) { %> (<%=numOfProducts%>) <% } %>
    </button>

    <% if(isLogged == null || !isLogged) {
            session.setAttribute("isLogged", Boolean.FALSE); %>
    <button type="submit" class="btn btn-outline-dark flex-shrink-0" onclick="window.location.href = 'login.jsp'">
        <i class="bi bi-person-circle"></i> Login
    </button>
    <% }
    else {
        String username = (String) session.getAttribute("username"); %>
    <div class="dropdown">
        <button type="button" class="btn btn-outline-dark flex-shrink-0" data-bs-toggle="dropdown" aria-expanded="true">
            <i class="bi bi-person-circle"></i> <%=username%>
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="edit_password.jsp">Modifica password</a></li>
            <li><a class="dropdown-item" href="cronologia_ordini.jsp">Cronologia ordini</a></li>
            <%  Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
                if(isAdmin) { %>
            <li><a class="dropdown-item" href="gestione_ordini.jsp">Gestione ordini</a></li>
            <li><a class="dropdown-item" href="gestione_catalogo.jsp">Gestione catalogo</a></li>
            <%
                }
            %>
            <li><a class="dropdown-item" onclick="submitLogout()">Logout</a></li>
        </ul>
    </div>
    <%
        }
    %>
</div>
<script src="static/scripts/form.js"></script>
</body>
</html>
