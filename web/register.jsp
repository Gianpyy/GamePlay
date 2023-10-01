<%@ page import="java.util.List" %>
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

<%
  List<String> errors = (List<String>) request.getAttribute("errors");
  if(errors != null) {
    for (String error : errors) { %>
      <%=error%> <br>
<%
    }
  }
%>
<form class="login" method="post" action="Register">
  <label for="nome">Nome: </label>
  <input type="text" name="nome" id="nome"> <br>
  <label for="cognome">Cognome: </label>
  <input type="text" name="cognome" id="cognome"> <br>
  <label for="codiceFiscale">Codice Fiscale: </label>
  <input type="text" maxlength="16" id="codiceFiscale" name="codiceFiscale"> <br>
  Sesso:
  <input type="radio" id="M" value="M" name="sesso">
  <label for="M">M </label>
  <input type="radio" id="F" value="F" name="sesso">
  <label for="F">F </label> <br>
  <label for="dataNascita">Data di nascita: </label>
  <input type="date" id="dataNascita" name="dataNascita"> <br>
  <label for="username">Username: </label>
  <input type="text" name="username" id="username"> <br>
  <label for="password">Password: </label>
  <input type="password" name="password" id="password"> <br>
  <input type="submit" value="Registrati ora">
</form>

<footer>
  <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
