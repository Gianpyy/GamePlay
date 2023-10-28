<%@ page import="model.ProdottoBean" %>
<%@ page import="control.dao.ProdottoDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="static/scripts/product.js"></script>
    <link rel="stylesheet" href="static/styles/styles.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <title>Homepage</title>
</head>
<body>
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
<div class="container">
<!--Sezione sconti e promozioni -->
<section>
<h2>Sconti e promozioni</h2>

<!-- Qui appaiono le cover dei prodotti in promozione -->
  <% for (ProdottoBean p : prodotti) { %>
    <div class="card" id="<%=p.getBarcode()%>" onclick="redirectToProductPage(this.id)">
      <img class="videogame" src="static/img/videogame_cover_placeholder.jpg" alt="Avatar">
      <div class="container">
          <h4><b><%=p.getNome()%></b></h4>
          <p><%=p.getPrezzo()%> €</p>
      </div>
  </div>
  <% } %>

<div class="nofloat">
    <hr>
</div>
</section>

<!-- Sezione per te -->
<section>
<h2>Per te</h2>
<!-- Qui appaiono le cover dei prodotti personalizzati in base alle preferenze dell'utente -->
<% for (int i = 0; i < 8; i++) { %>
<div class="card">
    <img class="videogame" src="static/img/videogame_cover_placeholder.jpg" alt="Avatar">
    <div class="container">
        <h4><b>Videogiuco</b></h4>
        <p>70€</p>
    </div>
</div>
<% } %>

<div class="nofloat">
    <hr>
</div>
</section>

<!-- Sezione In arrivo -->
<section>
<h2>In arrivo</h2>
<!-- Qui appaiono le cover dei prodotti in arrivo -->
<% for (int i = 0; i < 8; i++) { %>
<div class="card">
    <img class="videogame" src="static/img/videogame_cover_placeholder.jpg" alt="Avatar">
    <div class="container">
        <h4><b>Videogiuco</b></h4>
        <p>70€</p>
    </div>
</div>
<% } %>

<div class="nofloat">
    <hr>
</div>
</section>

<!-- Sezione uscite recenti -->
<section>
<h2>Uscite recenti</h2>
<!-- Qui appaiono le cover dei prodotti usciti di recente -->
<% for (int i = 0; i < 8; i++) { %>
<div class="card">
    <img class="videogame" src="static/img/videogame_cover_placeholder.jpg" alt="Avatar">
    <div class="container">
        <h4><b>Videogiuco</b></h4>
        <p>70€</p>
    </div>
</div>
<% } %>
</section>
</div>
<footer>
      <%@include file="static/footer.jsp"%>
</footer>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
</html>
