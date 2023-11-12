<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ConsoleBean" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="control.dao.PhotoDAO" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="control.ImageUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Prodotto</title>
    <link rel="stylesheet" href="static/styles/styles.scss">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
<body>
<%--Include header--%>
<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <%@include file="static/header.jsp"%>
</header>
<%
    //Dati da visualizzare nella pagina
    ConsoleBean prodotto = (ConsoleBean) session.getAttribute("prodotto");
    List<ConsoleBean> edizioniPiattaforme = (List<ConsoleBean>) session.getAttribute("edizioni-piattaforme");
    session.setAttribute("productId", prodotto.getBarcode());
%>
<!-- Product section-->
<section class="py-5">
    <div class="container px-4 px-lg-5 my-5">
        <div class="row gx-4 gx-lg-5 align-items-center">
            <%-- Immagine prodotto--%>
            <div class="col-md-6">
                <%  PhotoDAO photoDAO = new PhotoDAO();
                    InputStream coverImage = photoDAO.doRetrieveCoverImageForProduct(prodotto.getBarcode());
                    if (coverImage != null) { %>
                <img class="card-img-top mb-5 mb-md-0 rounded-3" src="data:image/jpeg;base64, <%= ImageUtilities.convertToBase64(coverImage) %>" alt="Immagine non trovata">
                <% } else { %>
                <img  class="card-img-top mb-5 mb-md-0 rounded-3" src="static/img/videogame_cover_placeholder.png"  alt="img not found">
                <% } %>
            </div>
            <div class="col-md-6">
                <h1 class="display-5 fw-bolder" id="productName"><%=prodotto.getNome()%></h1>
                <hr>
                <div class="fs-2 mb-3" id="productPrice">
                    <span><%=prodotto.getPrezzo()%> €</span>
                </div>

                <div class="fs-5" id="productEdizione">
                    Edizione: <span><%=prodotto.getEdizione()%></span>
                </div>
                <div class="mt-1 mb-4" id="edizioni">
                    <%  ArrayList<String> edizioni = new ArrayList<>();
                        for (ConsoleBean v : edizioniPiattaforme) {
                            String edizione = v.getEdizione();
                            if (!(edizioni.contains(edizione))) {
                                edizioni.add(edizione); %>
                    <button class="btn btn-outline-dark flex-shrink-0" onclick="changeDataOnConsolePage('<%=v.getNome()%>', '<%=v.getPrezzo()%>','<%=v.getEdizione()%>')" <% if(edizione.equals(prodotto.getEdizione())) { %> disabled <% } %>><%=edizione%></button>
                    <% }
                    }
                    %>
                </div>
                <hr>

                <div class="d-flex my-3">
                    <select class="form-select text-center me-3 w-25" id="inputQuantity">
                        <option value="1" selected>1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                    </select>
                    <button class="btn btn-outline-dark flex-shrink-0" type="submit" onclick="addProduct()">
                        <i class="bi-cart-fill me-1"></i>
                        Aggiungi al carrello
                    </button>
                </div>
            </div>
        </div>
    </div>
</section>

<%--<div>--%>
<%--    <h1><%=prodotto.getNome()%></h1> <br>--%>
<%--    <h1><%=prodotto.getPrezzo()%> €</h1> <br>--%>
<%--</div>--%>

<%--<div>--%>
<%--    <h2>Edizioni: </h2>--%>
<%--    <%--%>
<%--        for (ConsoleBean v : edizioniPiattaforme) { %>--%>
<%--    <button name="<%=v.getBarcode()%>"><%=v.getEdizione()%></button>--%>
<%--    <%--%>
<%--        }--%>
<%--    %>--%>
<%--</div>--%>

<%--Import js--%>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
<script src="static/scripts/product.js"></script>

<%--Import footer--%>
<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>