<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="control.dao.ProdottoDAO" %>
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
            <a class="remove" href="index.jsp">Torna alla homepage</a>
        </div>
<% } else { %>
        <div class="py-5 text-center mb-3">
            <h2>Gestione catalogo</h2>
            <div class="row g-3 align-items-center justify-content-center my-3">
                <button class="btn btn-primary col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseVideogioco" aria-expanded="false" aria-controls="collapseVideogioco">Aggiungi videogioco</button>
                <button class="btn btn-primary col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseConsole" aria-expanded="false" aria-controls="collapseConsole">Aggiungi console</button>
                <button class="btn btn-primary col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseGadget" aria-expanded="false" aria-controls="collapseGadget">Aggiungi gadget</button>
                <div class="collapse col-12" id="collapseVideogioco">
                    <%@include file="static/aggiunta_videogioco.jsp" %>
                </div>
                <div class="collapse col-12" id="collapseConsole">
                    <%@include file="static/aggiunta_console.jsp" %>
                </div>
                <div class="collapse col-12" id="collapseGadget">
                    <%@include file="static/aggiunta_gadget.jsp" %>
                </div>
                <form class="col-12 mb-3 mb-lg-0 me-lg-3" role="search">
                    <input type="search" class="form-control" placeholder="Cerca un prodotto..." aria-label="Search">
                </form>
            </div>

<% ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<ProdottoBean> prodotti = (List<ProdottoBean>) prodottoDAO.doRetrieveAll("");
    for (ProdottoBean p : prodotti) { %>
            <div class="bg-body-secondary rounded-3 d-flex justify-content-between my-3">
                <img src="static/img/videogame_cover_placeholder.jpg" class="rounded float-start imgRecap" alt="img not found">
                <h5><%=p.getNome()%></h5>
                <h5><%=p.getPrezzo()%></h5>
                <div class="order-last">
                    <button class="btn btn-danger" onclick="deleteProduct(<%=p.getBarcode()%>)">Elimina prodotto</button>
                    <button class="btn btn btn-primary">Modifica prodotto</button>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
<script src="static/scripts/product.js"></script>
</body>
</html>
