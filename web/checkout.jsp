<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Checkout</title>
  <link rel="stylesheet" href="static/styles/styles.css">
</head>
<body>
<header>
  <%@include file="static/header.jsp"%>
</header>

<h1>Checkout</h1>
<form method="post" action="Checkout">
    <label for="indirizzo">
        Indirizzo: <input type="text" name="indirizzo" id="indirizzo" placeholder="Via, civico">
    </label> <br>


    Pagamento
    <label for="Visa">
        Visa<input type="radio" name="pagamento" id="Visa" value="Visa">
    </label>
    <label for="MasterCard">
        MasterCard<input type="radio" name="pagamento" id="MasterCard" value="MasterCard">
    </label> <br>

    <label>
        Numero di carta: <input type="text" name="numerocarta">
    </label>
    <label>
        Scadenza: <input type="text" name="scadenza" maxlength="5">
    </label>
    <label>
        CVV: <input type="password" name="cvv">
    </label>

    <input type="hidden" name="actionType" value="checkout">
    <input type="submit" class="carrello" value="Procedi all'ordine">
</form>
<footer>
  <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>
