<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.VideogiocoBean" %>
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
    List<VideogiocoBean> edizioniPiattaforme = (List<VideogiocoBean>) session.getAttribute("edizioni-piattaforme");
    session.setAttribute("productId", prodotto.getBarcode());
%>
<div>
    <h1><%=prodotto.getNome()%></h1> <br>
    <h1><%=prodotto.getPrezzo()%> €</h1> <br>
</div>

<div>
    <h2>Piattaforme: </h2>
    <%
        ArrayList<String> piattaforme = new ArrayList<>();
        for (VideogiocoBean v : edizioniPiattaforme) {
            if (!(piattaforme.contains(v.getPiattaforma()))) {
                piattaforme.add(v.getPiattaforma()); %>
                <button name="<%=v.getBarcode()%>"><%=v.getPiattaforma()%></button>
        <%
             }
        }
    %>
</div>

<div>
    <h2>Edizioni: </h2>
    <%
        ArrayList<String> edizioni = new ArrayList<>();
        for (VideogiocoBean v : edizioniPiattaforme) {
            if(!(edizioni.contains(v.getEdizione()))) {
                edizioni.add(v.getEdizione());
    %>
        <button name="<%=v.getBarcode()%>"><%=v.getEdizione()%></button>
    <%
            }
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