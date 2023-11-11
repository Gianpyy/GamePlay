<%@ page import="java.util.List" %>
<%@ page import="control.dao.VideogiocoDAO" %>
<%@ page import="model.VideogiocoBean" %>
<%@ page import="model.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Modifica videogioco</title>
    <link rel="stylesheet" href="static/styles/styles.scss">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <%-- Bootstrap icons   --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="container d-flex align-items-center justify-content-center flex-column py-4"> <!-- justify-content-center -->
<main class="form-signin w-100 mx-auto"> <!-- container mx-auto -->
    <% Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) { %>
    <div class="py-5 text-center">
        <h2>Non sei autorizzato a visualizzare il contenuto di questa pagina.</h2>
        <a class="remove" href="index.jsp">Torna alla homepage</a>
    </div>
    <% } else {
        VideogiocoBean v = (VideogiocoBean) session.getAttribute("product");
        String[] numeri = v.getNumeroGiocatori().split("-");
        int numeroGiocatoriMinimo = Integer.parseInt(numeri[0]);
        int numeroGiocatoriMassimo = Integer.parseInt(numeri[1]);
    %>
    <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" id="updateVideogiocoForm" enctype="multipart/form-data">
        <h1 class="h3 mb-3 fw-normal">Dati videogioco</h1>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="nomeProdotto" id="floatingNome" placeholder="Nome" value="<%=v.getNome()%>">
            <label for="floatingNome">Nome</label>
            <div class="invalid-feedback" id="nomeProdottoInvalid">
                Inserisci un nome valido
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="edizione" id="floatingEdizione" placeholder="Edizione" value="<%=v.getEdizione()%>">
            <label for="floatingEdizione">Edizione</label>
            <div class="invalid-feedback" id="edizioneInvalid">
                Inserisci un'edizione valida
            </div>
        </div>

        <div class="row">
            <div class="col">
                <div class="form-floating mb-3">
                    <select class="form-select form-control" name="piattaforma" id="floatingPiattaforma">
                        <option value="notSelected">Seleziona una piattaforma</option>
                        <option value="PlayStation 4" <% if (v.getPiattaforma().equals("PlayStation 4")) { %> selected <% } %>>PlayStation 4</option>
                        <option value="PlayStation 5" <% if (v.getPiattaforma().equals("PlayStation 5")) { %> selected <% } %>>PlayStation 5</option>
                        <option value="Xbox Series X" <% if (v.getPiattaforma().equals("Xbox Series X")) { %> selected <% } %>>Xbox Series X</option>
                        <option value="Xbox Series S" <% if (v.getPiattaforma().equals("Xbox Series S")) { %> selected <% } %>>Xbox Series S</option>
                        <option value="Nintendo Switch" <% if (v.getPiattaforma().equals("Nintendo Switch")) { %> selected <% } %>>Nintendo Switch</option>
                        <option value="PC" <% if (v.getPiattaforma().equals("PC")) { %> selected <% } %>>PC</option>
                    </select>
                    <label for="floatingPiattaforma">Piattaforma</label>
                    <div class="invalid-feedback" id="piattaformaInvalid">
                        Seleziona una piattaforma
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="prezzo" id="floatingPrezzo" placeholder="(EUR €)" value="<%=v.getPrezzo()%>">
                    <label for="floatingPrezzo">Prezzo</label>
                    <div class="invalid-feedback" id="cognomeInvalid">
                        Inserisci un prezzo valido
                    </div>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <textarea class="form-control" name="descrizione" id="floatingDescrizione" placeholder="Descrizione" maxlength="250"><%=v.getDescrizione()%></textarea>
            <label for="floatingDescrizione">Descrizione</label>
            <div class="invalid-feedback" id="descrizioneInvalid">
                Inserisci una descrizione
            </div>
        </div>

        <div class="form-floating mb-3">
            <select class="form-select form-control" name="categoria" id="floatingCategoria">
                <option value="notSelected">Seleziona...</option>
                <% for (Categoria c : Categoria.values()) { %>
                <option value="<%=c%>" <% if (v.getCategoria().equals(c.name())) { %> selected <% } %>><%=c%></option>
                <% } %>
            </select>
            <label for="floatingCategoria">Categoria</label>
            <div class="invalid-feedback" id="categoriaInvalid">
                Seleziona una categoria
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="date" class="form-control" name="dataRilascio" id="floatingDataRilascio" value="<%=v.getDataRilascio()%>">
            <label for="floatingDataRilascio">Data di rilascio</label>
            <div class="invalid-feedback" id="dataRilascioInvalid">
                Inserisci una data valida
            </div>
        </div>

        <div class="form-floating mb-3">
            <select class="form-select form-control" name="condizioni" id="floatingCondizioni">
                <option value="notSelected">Seleziona...</option>
                <option value="Nuovo" <% if (v.getCondizioni().equals("Nuovo")) { %> selected <% } %>>Nuovo</option>
                <option value="Usato" <% if (v.getCondizioni().equals("Usato")) { %> selected <% } %>>Usato</option>
            </select>
            <label for="floatingCondizioni">Condizioni</label>
            <div class="invalid-feedback" id="condizioniInvalid">
                Seleziona una condizione
            </div>
        </div>

        <div class="row">
            <div class="col">
                <div class="form-floating mb-3">
                    <select class="form-select form-control" name="minimo" id="floatingMinimo">
                        <option value="notSelected">Seleziona...</option>
                        <option value="1" <% if (numeroGiocatoriMinimo == 1) { %> selected <% } %>>1</option>
                        <option value="2" <% if (numeroGiocatoriMinimo == 2) { %> selected <% } %>>2</option>
                        <option value="3" <% if (numeroGiocatoriMinimo == 3) { %> selected <% } %>>3</option>
                        <option value="4" <% if (numeroGiocatoriMinimo == 4) { %> selected <% } %>>4</option>
                        <option value="5" <% if (numeroGiocatoriMinimo == 5) { %> selected <% } %>>5</option>
                        <option value="6" <% if (numeroGiocatoriMinimo == 6) { %> selected <% } %>>6</option>
                        <option value="7" <% if (numeroGiocatoriMinimo == 7) { %> selected <% } %>>7</option>
                        <option value="8" <% if (numeroGiocatoriMinimo == 8) { %> selected <% } %>>8</option>
                    </select>
                    <label for="floatingMinimo">Numero giocatori minimo</label>
                    <div class="invalid-feedback" id="minimoInvalid">
                        Seleziona un numero di giocatori minimo
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="form-floating mb-3">
                    <select class="form-select form-control" name="massimo" id="floatingMassimo">
                        <option value="notSelected">Seleziona...</option>
                        <option value="1" <% if (numeroGiocatoriMassimo == 1) { %> selected <% } %>>1</option>
                        <option value="2" <% if (numeroGiocatoriMassimo == 2) { %> selected <% } %>>2</option>
                        <option value="3" <% if (numeroGiocatoriMassimo == 3) { %> selected <% } %>>3</option>
                        <option value="4" <% if (numeroGiocatoriMassimo == 4) { %> selected <% } %>>4</option>
                        <option value="5" <% if (numeroGiocatoriMassimo == 5) { %> selected <% } %>>5</option>
                        <option value="6" <% if (numeroGiocatoriMassimo == 6) { %> selected <% } %>>6</option>
                        <option value="7" <% if (numeroGiocatoriMassimo == 7) { %> selected <% } %>>7</option>
                        <option value="8" <% if (numeroGiocatoriMassimo == 8) { %> selected <% } %>>8</option>
                    </select>
                    <label for="floatingMassimo">Numero giocatori massimo</label>
                    <div class="invalid-feedback" id="massimoInvalid">
                        Seleziona un numero di giocatori massimo
                    </div>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <select class="form-select form-control" name="etaPEGI" id="floatingPegi">
                <option value="notSelected">Seleziona...</option>
                <option value="3" <% if (v.getEtaPegi() == 3) { %> selected <% } %>>3</option>
                <option value="7" <% if (v.getEtaPegi() == 7) { %> selected <% } %>>7</option>
                <option value="12" <% if (v.getEtaPegi() == 12) { %> selected <% } %>>12</option>
                <option value="16" <% if (v.getEtaPegi() == 16) { %> selected <% } %>>16</option>
                <option value="18" <% if (v.getEtaPegi() == 18) { %> selected <% } %>>18</option>
            </select>
            <label for="floatingPegi">PEGI</label>
            <div class="invalid-feedback" id="pegiInvalid">
                Seleziona un valore
            </div>
        </div>

        <div class="mb-3">
            <input class="form-control" type="file" name="img" id="formFileMultiple" accept="image/*" multiple>
            <div class="invalid-feedback" id="imgInvalid">
                Inserisci delle immagini
            </div>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit" id="updateVideogiocoSubmit">Modifica videogioco</button>
    </form>
</main>
<%
    }
%>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
</body>
</html>