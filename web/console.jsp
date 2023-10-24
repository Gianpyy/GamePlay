<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ConsoleBean" %>
<%@ page import="model.ProdottoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Prodotto</title>
    <link rel="stylesheet" href="static/styles/styles.css">
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>
<%
    //Dati da visualizzare nella pagina
    ProdottoBean prodotto = (ProdottoBean) session.getAttribute("prodotto");
    List<ConsoleBean> edizioniPiattaforme = (List<ConsoleBean>) session.getAttribute("edizioni-piattaforme");
    session.setAttribute("productId", prodotto.getBarcode());
%>

<div>
    <h1><%=prodotto.getNome()%></h1> <br>
    <h1><%=prodotto.getPrezzo()%> €</h1> <br>
</div>

<div>
    <h2>Edizioni: </h2>
    <%
        for (ConsoleBean v : edizioniPiattaforme) { %>
    <button name="<%=v.getBarcode()%>"><%=v.getEdizione()%></button>
    <%
        }
    %>
</div>

<form method="post" action="Carrello">
    <input type="hidden" name="actionType" value="addProduct">
    <input type="submit" class="carrello" value="Aggiungi al carrello">
</form>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>