<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it" class="h-100">
<head>
    <title>Carrello</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="static/styles/styles.scss">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="d-flex flex-column h-100">
<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <%@include file="static/header.jsp"%>
</header>
<%
    Boolean isCarrelloEmpty = (Boolean) request.getSession().getAttribute("isCarrelloEmpty");
    Boolean isUserLogged = (Boolean) request.getSession().getAttribute("isLogged");
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

        HashMap<String, Boolean> visualizzato = new HashMap<>();
        Float totale = 0f;
%>

<div class="container">
    <div class="row">
        <h1 id="cartHeading">Carrello</h1>
        <a href="#" onclick="emptyCart()" class="remove">Rimuovi tutti gli articoli dal carrello</a>
        <hr>
    </div>

    <div class="row">
        <div class="col-md-8">
                <%
                    for (ProdottoBean p : carrello) {
                        if (Boolean.FALSE.equals(visualizzato.getOrDefault(p.getBarcode(), Boolean.FALSE))) {
                            String productName = p.getNome();
                            String productId = p.getBarcode();
                            Float productPrice = p.getPrezzo();
                            Integer productCount = prodottiCounter.get(p.getBarcode()); %>
                        <div id="<%=productId%>">
                                <h3> <%=productName%>  </h3>
                                <h3> <%=productPrice%> €</h3>
                                <select id="<%=productId%>productCount" onchange="updateProductQuantity(<%=productId%>)">
                                <option value="1" <% if (productCount == 1 ) { %> selected <% }%>>Q.ta: 1</option>
                                <option value="2" <% if (productCount == 2 ) { %> selected <% }%>>Q.ta: 2</option>
                                <option value="3" <% if (productCount == 3 ) { %> selected <% }%>>Q.ta: 3</option>
                                <option value="4" <% if (productCount == 4 ) { %> selected <% }%>>Q.ta: 4</option>
                                <option value="5" <% if (productCount == 5 ) { %> selected <% }%>>Q.ta: 5</option>
                                <option value="6" <% if (productCount == 6 ) { %> selected <% }%>>Q.ta: 6</option>
                                <option value="7" <% if (productCount == 7 ) { %> selected <% }%>>Q.ta: 7</option>
                                <option value="8" <% if (productCount == 8 ) { %> selected <% }%>>Q.ta: 8</option>
                                <option value="9" <% if (productCount == 9 ) { %> selected <% }%>>Q.ta: 9</option>
                                </select>
                <a href="#" onclick="removeProductFromCart(<%=productId%>)" class="remove">Rimuovi prodotto</a>
                <br>
                    <%
             visualizzato.put(productId, true);
             totale += p.getPrezzo() * productCount; } %>
                                <hr>
                        </div>
<%
                    }
%>
        </div>

        <div class="col-6 col-md-4">
            <h2>
                <% String prodottiString = (numberOfProducts > 1) ? "prodotti" : "prodotto"; %>
                Totale (<%=numberOfProducts%> <%=prodottiString%>): <br>
                <%=totale%> € <br>
            </h2>
<%--            <button class="btn btn-primary" type="s">Procedi al checkout</button>--%>
            <form tabindex="0" id="checkoutButton" <% if (!isUserLogged) { %> data-bs-toggle="tooltip" title="Devi aver effettuato il login per poter procedere al checkout"  <% } %>>
                <button type="submit" class="btn btn-primary" <% if (!isUserLogged) { %> disabled  <% } %>>Procedi al checkout</button>
            </form>
        </div>
    </div>

<%
    }

    //Altrimenti, visualizzo che il carrello è vuoto
    else {
     request.getSession().setAttribute("isCarrelloEmpty", Boolean.TRUE); %>
    <div class="row">
        <h2> Il tuo carrello è vuoto. </h2>
    </div>
<%
    }
%>

</div>
<footer class="footer mt-auto">
    <%@include file="static/footer.jsp"%>
</footer>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/checkout.js"></script>
<script src="static/scripts/product.js"></script>
</body>
</html>
