<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="control.dao.ProdottoDAO" %>
<%@ page import="control.dao.PhotoDAO" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="control.ImageUtilities" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Gestione catalogo</title>
    <link rel="stylesheet" href="static/styles/styles.scss">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <%-- Bootstrap icons   --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body>
<header class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <%@include file="static/header.jsp"%>
</header>
<div class="container align-items-center justify-content-center mb-3">
    <main>
<% Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) { %>
        <div class="py-5 text-center">
            <h2>Non sei autorizzato a visualizzare il contenuto di questa pagina.</h2>
            <a class="removeDecorations" href="index.jsp">Torna alla homepage</a>
        </div>
<% } else { %>
        <div class="py-5 text-center mb-3">
            <h2>Gestione catalogo</h2>
            <div class="row g-3 align-items-center justify-content-center my-3">
                <button class="btn btn-outline-dark flex-shrink-0 col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseVideogioco" aria-expanded="false" aria-controls="collapseVideogioco">Aggiungi videogioco</button>
                <button class="btn btn-outline-dark flex-shrink-0 col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseConsole" aria-expanded="false" aria-controls="collapseConsole">Aggiungi console</button>
                <button class="btn btn-outline-dark flex-shrink-0 col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseGadget" aria-expanded="false" aria-controls="collapseGadget">Aggiungi gadget</button>
                <div class="collapse col-12" id="collapseVideogioco">
                    <%@include file="static/aggiunta_videogioco.jsp" %>
                </div>
                <div class="collapse col-12" id="collapseConsole">
                    <%@include file="static/aggiunta_console.jsp" %>
                </div>
                <div class="collapse col-12" id="collapseGadget">
                    <%@include file="static/aggiunta_gadget.jsp" %>
                </div>
            </div>

<% ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("");
    for (ProdottoBean p : prodotti) { %>
            <div class="bg-body-secondary rounded-3 my-3">
                <div class="row">
                    <%  PhotoDAO photoDAO = new PhotoDAO();
                        InputStream coverImage = photoDAO.doRetrieveCoverImageForProduct(p.getBarcode());
                        if (coverImage != null) { %>
                        <div class="col-2">
                            <img src="data:image/jpeg;base64, <%= ImageUtilities.convertToBase64(coverImage) %>" class="rounded float-start imgStandard" alt="Immagine non trovata">
                        </div>
                        <%
                        } else { %>
                    <div class="col-2">
                        <img src="static/img/videogame_cover_placeholder.png" class="rounded float-start imgStandard" alt="img not found">
                    </div>
                    <% } %>
                    <div class="col-7 d-flex flex-column align-items-start catalogoText">
                        <div>
                            <h2><%=p.getNome()%></h2>
                        </div>
                        <div>
                            <span class="price"><%=p.getPrezzo()%> €</span>
                        </div>
                    </div>
                    <div class="col-3 d-flex flex-column order-last align-self-center" id="catalogoButtons">
                        <button class="btn btn-primary btn-outline-dark flex-shrink-0 my-1" id="modificaProdotto" onclick="redirectToEditPage(<%=p.getBarcode()%>, '<%=p.getTipo()%>')">Modifica prodotto</button>
                        <button class="btn btn-danger btn-outline-dark flex-shrink-0" onclick="deleteProduct(<%=p.getBarcode()%>)">Elimina prodotto</button>
                    </div>
                </div>
            </div>
<% } %>
        </div>
<% } %>
    </main>
</div>
<footer>
    <%@include file="static/footer.jsp"%>
</footer>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
<script src="static/scripts/product.js"></script>
</body>
</html>
