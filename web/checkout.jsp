<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="static/styles/styles.scss">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <%-- Bootstrap icons   --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="static/styles/styles.scss">
</head>
<body>
<%
    //Recupero carrello e contatore prodotti dalla sessione
    List<ProdottoBean> carrello = (List<ProdottoBean>) request.getSession().getAttribute("carrello");
    HashMap<String, Integer> prodottiCounter = (HashMap<String, Integer>) request.getSession().getAttribute("prodottiCounter");

    //Calcolo il numero di prodotti presenti nel carrello
    int numberOfProducts = 0;
    for (int i : prodottiCounter.values()) {
        numberOfProducts += i;
    }

    Float totale = 0f;
%>
<div class="container">
    <main>
        <div class="py-5 text-center">
            <img class="logo mb-4 text-center" src="./static/img/logo_placeholder.png" alt="cool logo" onclick="window.location.href = 'index.jsp'">
            <h2>Checkout</h2>
        </div>
        <div class="row g-5">
            <div class="col-md-5 col-lg-4 order-md-last">
                <h4 class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-primary">Il tuo ordine</span>
                    <span class="badge bg-primary rounded-pill"><%=numberOfProducts%></span>
                </h4>
                <ul class="list-group mb-3">
                    <% for (ProdottoBean p : carrello) {

                    %>
                    <li class="list-group-item d-flex justify-content-between lh-sm">
                        <div>
                            <h6 class="my-0"><%=p.getNome()%></h6>
                            <small class="text-body-secondary"><%=p.getTipo()%></small>
                        </div>
                        <span class="text-body-secondary"><%=p.getPrezzo()%> €</span>
                    </li>
                    <%
                            totale += p.getPrezzo() * prodottiCounter.get(p.getBarcode());
                        }
                    %>
                    <li class="list-group-item d-flex justify-content-between">
                        <span>Totale</span>
                        <strong><%=totale%>€</strong>
                    </li>
                </ul>
            </div>
            <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">Indirizzo di spedizione</h4>
                <form id="checkoutForm">
                    <div class="row g-3">
                        <div class="col-sm-6">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" class="form-control" id="nome" name="nome" placeholder="" value="">
                            <div class="invalid-feedback" id="nomeInvalid">
                                Inserisci un nome valido.
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <label for="cognome" class="form-label">Cognome</label>
                            <input type="text" class="form-control" id="cognome" name="cognome" placeholder="" value="">
                            <div class="invalid-feedback" id="cognomeInvalid">
                                Inserisci un cognome valido.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="email" class="form-label">Email <span class="text-body-secondary">(Opzionale)</span></label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="you@example.com">
                            <div class="invalid-feedback" id="emailInvalid">
                                Inserisci un indirizzo email valido per gli aggiornamenti sulla spedizione.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="indirizzo" class="form-label">Indirizzo</label>
                            <input type="text" class="form-control" name="indirizzo" id="indirizzo" placeholder="Via, civico">
                            <div class="invalid-feedback" id="indirizzoInvalid">
                                Inserisci il tuo indirizzo di spedizione.
                            </div>
                        </div>

                        <div class="col-12">
                            <label for="address2" class="form-label">Indirizzo 2 <span class="text-body-secondary">(Opzionale)</span></label>
                            <input type="text" class="form-control" id="address2" placeholder="Appartamento o suite">
                        </div>

                        <div class="col-md-5">
                            <label for="citta" class="form-label">Città</label>
                            <input type="text" class="form-control" id="citta" name="citta" placeholder="" value="">
                            <div class="invalid-feedback" id="cittaInvalid">
                                Seleziona una città valida.
                            </div>
                        </div>

                        <div class="col-md-4">
                            <label for="provincia" class="form-label">Provincia</label>
                            <input type="text" class="form-control" maxlength="2" id="provincia" name="provincia" placeholder="" value="">
                            <div class="invalid-feedback" id="provinciaInvalid">
                                Fornisci una provincia valida.
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="cap" class="form-label">CAP</label>
                            <input type="text" class="form-control" id="cap" name="cap" placeholder="">
                            <div class="invalid-feedback" id="capInvalid">
                                Inserisci un cap valido.
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <h4 class="mb-3">Pagamento</h4>

                    <div id="pagamento">
                        <div class="my-3">
                            <div class="form-check">
                                <input id="credit" name="paymentMethod" type="radio" class="form-check-input" value="Carta" checked="">
                                <label class="form-check-label" for="credit">Carta di credito</label>
                            </div>
                            <div class="form-check">
                                <input id="debit" name="paymentMethod" type="radio" class="form-check-input" value="Carta di debito">
                                <label class="form-check-label" for="debit">Carta di debito</label>
                            </div>
                            <div class="form-check">
                                <input id="paypal" name="paymentMethod" type="radio" class="form-check-input" value="Paypal">
                                <label class="form-check-label" for="paypal">PayPal</label>
                            </div>
                        </div>
                    </div>

                    <div class="row gy-3">
                        <div class="col-md-6">
                            <label for="cc-name" class="form-label">Nome sulla carta</label>
                            <input type="text" class="form-control" id="cc-name" name="cc-name" placeholder="">
                            <small class="text-body-secondary">Nome completo come appare sulla carta</small>
                            <div class="invalid-feedback" id="cc-nameInvalid">
                                Inserisci un nome valido.
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="cc-number" class="form-label">Numero della carta di credito</label>
                            <input type="text" class="form-control" id="cc-number" name="cc-number" placeholder="">
                            <div class="invalid-feedback" id="cc-numberInvalid">
                                Inserisci un numero di carta valido.
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="cc-expiration" class="form-label">Scadenza</label>
                            <input type="text" class="form-control" id="cc-expiration" maxlength="5" name="cc-expiration" placeholder="mm/aa">
                            <div class="invalid-feedback" id="cc-expirationInvalid">
                                Inserisci una data di scadenza valida.
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="cc-cvv" class="form-label">CVV</label>
                            <input type="text" class="form-control" id="cc-cvv" name="cc-cvv" maxlength="3" placeholder="">
                            <div class="invalid-feedback" id="cc-cvvInvalid">
                                Inserisci un codice di sicurezza valido.
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <button class="w-100 btn btn-primary btn-lg" type="submit">Continua al pagamento</button>
                </form>
            </div>
        </div>

    </main>
</div>
<footer class="my-5">
  <%@include file="static/footer.jsp"%>
</footer>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
<script src="static/scripts/checkout.js"></script>
</body>
</html>