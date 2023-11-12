<%@ page import="java.util.List" %>
<%@ page import="control.dao.OrdineDAO" %>
<%@ page import="model.OrdineBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Gestione ordini</title>
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
  <main class="mx-auto">
    <% Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
      if (isAdmin == null || !isAdmin) { %>
    <div class="py-5 text-center">
      <h2>Non sei autorizzato a visualizzare il contenuto di questa pagina.</h2>
      <a class="remove" href="index.jsp">Torna alla homepage</a>
    </div>
    <% } else { %>
    <div class="py-5 text-center mb-3">
      <h2>Gestione ordini</h2>
      <div class="row g-3 align-items-center justify-content-center my-3">
        <button class="btn btn-primary col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFiltroUtente" aria-expanded="false" aria-controls="collapseFiltroUtente">Filtra ordini per utente</button>
        <button class="btn btn-primary col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFiltroData" aria-expanded="false" aria-controls="collapseFiltroData">Filtra ordini per data</button>
        <button class="btn btn-primary col-8 col-md-3 mx-3" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFiltroID" aria-expanded="false" aria-controls="collapseFiltroID">Filtra ordini per numero ordine</button>
        <div class="collapse col-12 p-2 p-md-3 border rounded-3 bg-body-tertiary filtroData" id="collapseFiltroUtente">
          <form class="" id="filtroUtente">
            <div class="row g-1">
              <div class="form-floating mb-3 col-8 col-md-7 mx-3">
                <input type="text" class="form-control" name="userId" id="floatingUserID" >
                <label for="floatingUserID">ID</label>
                <div class="invalid-feedback" id="userIDInvalid">
                  Inserisci un ID valido
                </div>
              </div>

              <button class="btn btn-xl btn-primary col-8 col-md-2 mx-3" type="submit">Filtra</button>
            </div>
          </form>
        </div>
        <div class="collapse col-12 p-2 p-md-3 border rounded-3 bg-body-tertiary filtroData" id="collapseFiltroData">
          <form class="" id="filtroData">
            <div class="row g-1">
              <div class="form-floating mb-3 col-8 col-md-5 mx-3">
                <input type="date" class="form-control" name="dataInizio" id="floatingDataInizio" placeholder="">
                <label for="floatingDataInizio">Da</label>
                <div class="invalid-feedback" id="dataInizioInvalid">
                  Inserisci una data valida
                </div>
              </div>
              <div class="form-floating mb-3 col-8 col-md-5 mx-3">
                <input type="date" class="form-control" name="dataFine" id="floatingDataFine" placeholder="">
                <label for="floatingDataFine">A</label>
                <div class="invalid-feedback" id="dataFineInvalid">
                  Inserisci una data valida
                </div>
              </div>
              <button class="btn btn-primary col-8" type="submit">Filtra</button>
            </div>
          </form>
        </div>
        <div class="collapse col-12 p-3 border rounded-3 bg-body-tertiary filtroData" id="collapseFiltroID">
          <form class="" id="filtroID">
            <div class="row g-1">
              <div class="form-floating mb-3 col-8 col-md-7 mx-3">
                <input type="text" class="form-control" name="orderID" id="floatingOrderID" >
                <label for="floatingOrderID">ID</label>
                <div class="invalid-feedback" id="orderIDInvalid">
                  Inserisci un ID valido
                </div>
              </div>

              <button class="btn btn-xl btn-primary col-8 col-md-2 mx-3" type="submit">Filtra</button>
            </div>
          </form>
        </div>
      </div>

      <div id="ordiniContainer">
      <% OrdineDAO ordineDAO = new OrdineDAO();
        List<OrdineBean> ordini = (List<OrdineBean>) ordineDAO.doRetrieveAll("");
        for (OrdineBean o : ordini) { %>
        <div class="ordine<%=o.getNumeroOrdine()%>wrapper">
          <div class="bg-body-secondary rounded-3 my-3">
            <div class="row">
              <div class="col-2">
                <img src="static/img/videogame_cover_placeholder.jpg" class="rounded float-start imgStandard" alt="img not found">
              </div>
              <div class="col-7 d-flex flex-column align-items-start">
                <div>
                  <h4>Numero ordine: <%=o.getNumeroOrdine()%></h4>
                </div>
                <div>
                  <h4>Effettuato il: <%=o.getData()%></h4>
                </div>
                <div>
                  <select class="form-select" name="statoOrdine" id="statoOrdine<%=o.getNumeroOrdine()%>" onchange="changeOrderStatus('<%=o.getNumeroOrdine()%>')">
                    <option value="Pagamento ricevuto" <% if (o.getStato().equals("Pagamento ricevuto")) { %> selected <% } %>>Pagamento ricevuto</option>
                    <option value="In preparazione alla spedizione" <% if (o.getStato().equals("In preparazione alla spedizione")) { %> selected <% } %>>In preparazione alla spedizione</option>
                    <option value="Spedito" <% if (o.getStato().equals("Spedito")) { %> selected <% } %>>Spedito</option>
                    <option value="In Consegna" <% if (o.getStato().equals("In Consegna")) { %> selected <% } %>>In Consegna</option>
                    <option value="Consegnato" <% if (o.getStato().equals("Consegnato")) { %> selected <% } %>>Consegnato</option>
                  </select>
                </div>
              </div>
              <div class="col-3 d-flex flex-column order-last align-self-center">
                <button class="btn btn-primary my-1" id="modificaProdotto">Visualizza dettagli ordine</button>
                <button class="btn btn-danger" onclick="deleteOrder(<%=o.getNumeroOrdine()%>)">Elimina ordine</button>
              </div>
            </div>
          </div>
        </div>
      <% } %>
      </div>
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
