<%@ page import="java.util.List" %>
<%@ page import="model.OrdineBean" %>
<%@ page import="control.dao.OrdineDAO" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="control.dao.PhotoDAO" %>
<%@ page import="control.ImageUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Cronologia ordini</title>
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
    int userId = (int) session.getAttribute("userid");
    OrdineDAO ordineDAO = new OrdineDAO();
    List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.doRetrieveByUserId(userId);
%>
    <% if (ordini.isEmpty()) { %>
        <h2>La cronologia ordini &egrave; vuota.</h2>
<%
    } else {
%>
<section class="h-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-10 col-xl-8">
                <h1 class="fw-bold">Cronologia ordini</h1>
                <% for (OrdineBean ordine : ordini) { %>
                <div class="card" id="orderRecapWrapper">
                    <div class="card-body p-4">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <p class="lead fw-normal mb-0 highlighted">Ordine #<%=ordine.getNumeroOrdine()%></p>
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
                </div>
                <% } %>
            </div>
        </div>
    </div>

</section>

<%--<section class="h100">--%>
<%--    <div class="container py-5 h-100">--%>
<%--        <h1 class="fw-bold">Cronologia ordini</h1>--%>
<%--        <hr>--%>

<%--        <% for (OrdineBean ordine : ordini) { %>--%>
<%--        <div class="card">--%>
<%--            <div class="card-body">--%>
<%--                <div class="mb-5">--%>
<%--                    <h5 class="mb-0">Ordine #<%=ordine.getNumeroOrdine()%></h5>--%>
<%--                </div>--%>
<%--                <% for (int i = 0; i < ordine.getProdotti().size(); i++) {--%>
<%--                    String productBarcode = ordine.getProdotti().get(i).getBarcode();--%>
<%--                    String productName = ordine.getProdotti().get(i).getNome();--%>
<%--                    int quantitaProdotti = ordine.getQuantitaProdotti().get(i);--%>
<%--                    Float productPrice = ordine.getProdotti().get(i).getPrezzo() * quantitaProdotti; %>--%>
<%--                <div class="row justify-content-between align-items-center">--%>
<%--                    <!-- col -->--%>
<%--                    <div class="col-lg-8 col-12">--%>
<%--                        <div class="d-md-flex">--%>
<%--                            <div>--%>
<%--                                <!-- img -->--%>
<%--                                <%  PhotoDAO photoDAO = new PhotoDAO();--%>
<%--                                    InputStream thumbnailImage = photoDAO.doRetrieveThumbnailImageForProduct(productBarcode);--%>
<%--                                    if (thumbnailImage != null) { %>--%>
<%--                                <img src="data:image/jpeg;base64, <%= ImageUtilities.convertToBase64(thumbnailImage) %>" class="rounded float-start img-fluid img-thumbnail" alt="Immagine non trovata">--%>
<%--                                <% }--%>
<%--                                else { %>--%>
<%--                                <img src="static/img/videogame_thumbnail_placeholder.png" class="rounded float-start img-fluid img-thumbnail" alt="img not found">--%>
<%--                                <% }%>--%>
<%--                            </div>--%>
<%--                            <div class="ms-md-4 mt-2 mt-lg-0">--%>
<%--                                <!-- heading -->--%>
<%--                                <h5 class="mb-1">--%>
<%--                                    <%=productName%>--%>
<%--                                </h5>--%>
<%--                                <!-- text -->--%>
<%--                                <div class="mt-3">--%>
<%--                                    <h4><%=productPrice%> €</h4>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <div class="col-lg-3 col-12"></div>--%>
<%--                </div>--%>
<%--                <hr>--%>
<%--                <% } %>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</section>--%>
<%
    }
%>

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