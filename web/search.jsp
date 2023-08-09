<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="static/styles/styles.css">
    <title>Ricerca</title>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>

<!-- Sezione filtri -->
<div class="filters">
<form name="filters" method="post" action="#">
    <input type="checkbox" id="filtro1" value="Filtro1">
    <label for="filtro1">Filtro 1</label> <br>
    <input type="checkbox" id="filtro2" value="Filtro2">
    <label for="filtro2">Filtro 2</label> <br>
    <input type="checkbox" id="filtro3" value="Filtro3">
    <label for="filtro3">Filtro 3</label> <br>
</form>
</div>

<!-- Risultati ricerca -->
<div class="search">
    <% for (int i=0; i < 3; i++) { %>
    <div class="card">
        <img class="videogame" src="static/img/videogame_cover_placeholder.jpg" alt="Avatar">
        <div class="container">
            <h4><b>Videogiuco</b></h4>
            <p>70€</p>
        </div>
    </div>
    <% } %>
</div>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
