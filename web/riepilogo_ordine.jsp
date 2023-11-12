<%@ page import="model.OrdineBean" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="control.dao.PhotoDAO" %>
<%@ page import="control.ImageUtilities" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Riepilogo ordine</title>
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
<%  OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
    String username = (String) session.getAttribute("username"); %>
<section class="h-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-10 col-xl-8">
                <div class="card" id="orderRecapWrapper">
                    <div class="card-header px-4 py-5">
                        <h5 class="text-muted mb-0">Grazie per il tuo ordine, <span class="highlighted"><%=username%></span>!</h5>
                    </div>
                    <div class="card-body p-4">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <p class="lead fw-normal mb-0 highlighted">Riepilogo</p>
                        </div>
                        <% for (int i = 0; i < ordine.getProdotti().size(); i++) {
                            String productBarcode = ordine.getProdotti().get(i).getBarcode();
                            String productName = ordine.getProdotti().get(i).getNome();
                            int quantitaProdotti = ordine.getQuantitaProdotti().get(i);
                            Float productPrice = ordine.getProdotti().get(i).getPrezzo() * quantitaProdotti; %>

                        <div class="card shadow-0 border mb-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-2">
                                        <%  PhotoDAO photoDAO = new PhotoDAO();
                                            InputStream thumbnailImage = photoDAO.doRetrieveThumbnailImageForProduct(productBarcode);
                                            if (thumbnailImage != null) { %>
                                            <img src="data:image/jpeg;base64, <%= ImageUtilities.convertToBase64(thumbnailImage) %>" class="rounded float-start img-fluid img-thumbnail" alt="Immagine non trovata">
                                            <% }
                                            else { %>
                                            <img src="static/img/videogame_thumbnail_placeholder.png" class="rounded float-start img-fluid img-thumbnail" alt="img not found">
                                            <% }%>
                                    </div>
                                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                        <p class="text-muted mb-0"><%=productName%></p>
                                    </div>
                                    <div class="col-md-2"></div>
                                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                        <p class="text-muted mb-0 small">Q.ta: <%=quantitaProdotti%></p>
                                    </div>
                                    <div class="col-md-2"></div>
                                    <div class="col-md-2 text-center d-flex justify-content-center align-items-center">
                                        <p class="text-muted mb-0 small"><%=productPrice%> €</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                    <div class="card-footer border-0 px-4 py-5">
                        <h5 class="d-flex align-items-center justify-content-end text-white text-uppercase mb-0">Totale: <span class="h2 mb-0 ms-2"><%=ordine.getTotale()%> €</span></h5>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container text-center">
        Puoi controllare lo stato del tuo ordine dalla <a href="cronologia_ordini.jsp">cronologia ordini</a> del tuo profilo. <br>

        <a href="index.jsp">Torna alla homepage</a>
    </div>
</section>

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

