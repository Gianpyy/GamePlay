<%@ page import="control.dao.ProdottoDAO" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="control.dao.PhotoDAO" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="control.ImageUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Homepage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="static/styles/styles.scss">
</head>
<body>

<%--Include header--%>
<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <%@include file="static/header.jsp"%>
</header>
<% ProdottoDAO prodottoDAO = new ProdottoDAO(); %>
<% LinkedList<ProdottoBean> prodotti;
    try {
        prodotti = (LinkedList<ProdottoBean>) prodottoDAO.doRetrieveAll("");
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<div class="container" id="homepageWrapper">
    <section class="py-5" id="latestProducts">
        <h2>Ultime uscite</h2>
        <div id="productCarousel" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <% int itemsPerSlide = 4;
                for (int i = 0; i < prodotti.size(); i += itemsPerSlide) { %>
                <div class="carousel-item <%= i == 0 ? "active" : "" %>">
                    <div class="d-flex justify-content-around">
                        <% for (int j = i; j < i + itemsPerSlide && j < prodotti.size(); j++) {
                            ProdottoBean p = prodotti.get(j); %>
                        <div class="card h-100 d-flex flex-column" onclick="redirectToProductPage('<%=p.getBarcode()%>')">
                            <div class="flex-fill">
                                <!-- Immagine prodotto -->
                                <%  PhotoDAO photoDAO = new PhotoDAO();
                                    InputStream coverImage = photoDAO.doRetrieveCoverImageForProduct(p.getBarcode());
                                    if (coverImage != null) { %>
                                <img class="card-img-top" src="data:image/jpeg;base64, <%= ImageUtilities.convertToBase64(coverImage) %>" alt="Immagine non trovata">
                                <% } else { %>
                                <img  class="card-img-top" src="static/img/videogame_cover_placeholder.png"  alt="img not found">
                                <% } %>
                                <!-- Dettagli prodotto -->
                                <div class="card-body p-4">
                                    <div class="text-center">
                                        <!-- Nome prodotto-->
                                        <h5 class="fw-bolder"><%=p.getNome()%></h5>
                                        <!-- Prezzo prodotto -->
                                        <%=p.getPrezzo()%> €
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </section>
</div>


<%--Include footer--%>
<footer class="footer mt-auto">
    <%@include file="static/footer.jsp"%>
</footer>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
<script src="static/scripts/product.js"></script>
</body>
</html>
