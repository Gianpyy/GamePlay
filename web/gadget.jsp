<%@ page import="java.util.List" %>
<%@ page import="model.VideogiocoBean" %>
<%@ page import="model.GadgetBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Prodotto</title>
  <link rel="stylesheet" href="static/styles/styles.css">
  <script src="static/scripts/product.js"></script>
</head>
<body>
<header>
  <%@include file="static/header.jsp"%>
</header>
<%
  GadgetBean prodotto;
  prodotto = (GadgetBean) session.getAttribute("prodotto");
  session.setAttribute("productId", prodotto.getBarcode());

%>
<div>
  <h1><%=prodotto.getNome()%></h1> <br>
  <h1><%=prodotto.getPrezzo()%> €</h1> <br>
</div>

<form method="post" action="Carrello">
  <input type="submit" class="carrello" value="Aggiungi al carrello">
</form>

<footer>
  <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>