<%@ page import="java.util.List" %>
<%@ page import="model.OrdineBean" %>
<%@ page import="control.dao.OrdineDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <link rel="stylesheet" href="static/styles/styles.css">
    <script src="static/scripts/product.js"></script>
    <title>Cronologia ordini</title>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>
<%
    int userId = (int) session.getAttribute("userid");
    OrdineDAO ordineDAO = new OrdineDAO();
    List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.doRetrieveByUserId(userId);
%>
    <% if (ordini.isEmpty()) { %>
        <h2>La cronologia ordini &egrave; vuota.</h2>
<%
    } else {
        for (OrdineBean ordine : ordini) {
%>
        <div class="ordine">
            <b>Numero ordine: </b> <%=ordine.getNumeroOrdine()%> <br>
            <b>Effettuato il: </b> <%=ordine.getData()%> <br>
            <b>Totale: </b> <%=ordine.getTotale()%> €<br>
            <b>Stato dell'ordine: </b> <%=ordine.getStato()%> <br>
            <b>Indirizzo di spedizione: </b> <%=ordine.getIndirizzo()%> <br>
            <b>Prodotti acquistati</b>
            <ul>
                <% for (int i = 0; i < ordine.getProdotti().size(); i++) {
                    String productName = ordine.getProdotti().get(i).getNome();
                    int quantitaProdotti = ordine.getQuantitaProdotti().get(i);
                    Float productPrice = ordine.getProdotti().get(i).getPrezzo() * quantitaProdotti; %>
                <li> <a href="#" onclick="redirectToProductPage(<%=ordine.getProdotti().get(i).getBarcode()%>)"> <%=productName%>(x<%=quantitaProdotti%>)</a>    <%=productPrice%>€ </li>
                <% } %>
            </ul>
        </div>
        <br> <br>
<%
        }
    }
%>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
