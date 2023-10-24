<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Carrello</title>
    <link rel="stylesheet" href="static/styles/styles.css">
    <script src="static/scripts/product.js"></script>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>
<%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if(errors != null) {
        for (String error : errors) { %>
<%=error%> <br>
<%
        }
     %>

        <br> <br>
<%
    }
%>
<%
    Boolean isCarrelloEmpty = (Boolean) request.getSession().getAttribute("isCarrelloEmpty");
    List<ProdottoBean> carrello;
    HashMap<String, Integer> prodottiCounter;

    //Se c'è un carrello con dei prodotti, procedo a visualizzarli
    if(Boolean.FALSE.equals(isCarrelloEmpty)) {
        //Recupero il carrello e il contatore dei prodotti dalla sessione
        carrello = (List<ProdottoBean>) request.getSession().getAttribute("carrello");
        prodottiCounter = (HashMap<String, Integer>) request.getSession().getAttribute("prodottiCounter");

        //Calcolo il numero di prodotti presenti nel carrello
        int numberOfProducts = 0;
        for (int i : prodottiCounter.values()) {
            numberOfProducts += i;
        }
%>

<h1>Prodotti nel carrello: <%=numberOfProducts%></h1>

<h2>
    <%
        HashMap<String, Boolean> visualizzato = new HashMap<>();
        for (ProdottoBean p : carrello) {
            if (Boolean.FALSE.equals(visualizzato.getOrDefault(p.getBarcode(), Boolean.FALSE))) {

            String productName = p.getNome();
            Integer productCount = prodottiCounter.get(p.getBarcode());
        %>
            <%=productName%> <select name="<%=productName%>productCount" id="<%=productName%>productCount">
                                <option value="1" <% if (productCount == 1 ) { %> selected <% }%>>1</option>
                                <option value="2" <% if (productCount == 2 ) { %> selected <% }%>>2</option>
                                <option value="3" <% if (productCount == 3 ) { %> selected <% }%>>3</option>
                                <option value="4" <% if (productCount == 4 ) { %> selected <% }%>>4</option>
                                <option value="5" <% if (productCount == 5 ) { %> selected <% }%>>5</option>
                                <option value="6" <% if (productCount == 6 ) { %> selected <% }%>>6</option>
                                <option value="7" <% if (productCount == 7 ) { %> selected <% }%>>7</option>
                                <option value="8" <% if (productCount == 8 ) { %> selected <% }%>>8</option>
                                <option value="9" <% if (productCount == 9 ) { %> selected <% }%>>9</option>
                            </select>
        <button onclick="removeProductFromCart(<%=p.getBarcode()%>)">Rimuovi prodotto</button>
            <br>
<%
             visualizzato.put(p.getBarcode(), true);
             }
         }
%>

    <button onclick="emptyCart()">Svuota carrello</button>
        <br>
        <form method="post" action="Checkout">
            <input type="submit" class="carrello" value="Procedi al checkout">
            <input type="hidden" name="actionType" value="checkoutButton">
        </form>
<%
    }

    //Altrimenti, visualizzo che il carrello è vuoto
    else {
     request.getSession().setAttribute("isCarrelloEmpty", Boolean.TRUE);%>
            <h2> Carrello vuoto </h2>
<%
    }
%>
</h2>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
