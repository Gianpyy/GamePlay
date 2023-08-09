<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <link rel="stylesheet" href="static/styles/styles.css">
    <title>Homepage</title>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>

<!--Sezione sconti e promozioni -->
<section>
<h2>Sconti e promozioni</h2>

<!-- Qui appaiono le cover dei prodotti in promozione -->
  <% for (int i=0; i < 8; i++) { %>
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

<!-- Sezione per te -->
<section>
<h2>Per te</h2>
<!-- Qui appaiono le cover dei prodotti personalizzati in base alle preferenze dell'utente -->
<% for (int i=0; i < 8; i++) { %>
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
<% for (int i=0; i < 8; i++) { %>
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
<% for (int i=0; i < 8; i++) { %>
<div class="card">
    <img class="videogame" src="static/img/videogame_cover_placeholder.jpg" alt="Avatar">
    <div class="container">
        <h4><b>Videogiuco</b></h4>
        <p>70€</p>
    </div>
</div>
<% } %>
</section>

<footer>
      <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
