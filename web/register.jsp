<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrati</title>
  <link rel="stylesheet" href="static/styles/styles.css">
</head>
<body>
<header>
  <%@include file="static/header.jsp"%>
</header>

<form class="login" method="post" action="index.jsp">
  Nome: <input type="text" id="nome"> <br>
  Cognome: <input type="text" id="cognome"> <br>
  Username: <input type="text" id="username"> <br>
  Password: <input type="password" id="password"> <br>
  <input type="submit" value="Registrati ora">
</form>

<footer>
  <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
