<%@ page import="model.OrdineBean" %>
<%@ page import="model.ProdottoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Riepilogo ordine</title>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>

<%
    OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
%>

<h1>Grazie per l'ordine!</h1>
<h3>Di seguito un riepilogo dei dati dell'ordine</h3>
Importo totale: <%=ordine.getTotale()%> € <br>
Indirizzo spedizione: <%=ordine.getIndirizzo()%> <br>
Prodotti acquistati:
<ul>
    <% for (int i = 0; i < ordine.getProdotti().size(); i++) {
        String productName = ordine.getProdotti().get(i).getNome();
        int quantitaProdotti = ordine.getQuantitaProdotti().get(i);
        Float productPrice = ordine.getProdotti().get(i).getPrezzo() * quantitaProdotti;

    %>
            <li><%=productName%>(x<%=quantitaProdotti%>)    <%=productPrice%>€</li>
    <% } %>
</ul>

<br>

Puoi controllare lo stato del tuo ordine dalla <a href="cronologia_ordini.jsp">cronologia ordini</a> del tuo profilo. <br>

<a href="index.jsp">Torna alla homepage</a>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>

