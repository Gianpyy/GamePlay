<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Registrati</title>
  <link rel="stylesheet" href="static/styles/styles.css">
</head>
<body>
<header>
  <%@include file="static/header.jsp"%>
</header>

<form class="login" method="post" action="index.jsp">
  <label for="nome">Nome: </label>
  <input type="text" id="nome"> <br>
  <label for="cognome">Cognome: </label>
  <input type="text" id="cognome"> <br>
  <label for="username">Username: </label>
  <input type="text" id="username"> <br>
  <label for="password">Password: </label>
  <input type="password" id="password"> <br>
  <input type="submit" value="Registrati ora">
</form>

<footer>
  <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
