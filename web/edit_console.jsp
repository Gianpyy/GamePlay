<%@ page import="model.ConsoleBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Modifica console</title>
    <link rel="stylesheet" href="static/styles/styles.scss">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <%-- Bootstrap icons   --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="container d-flex align-items-center justify-content-center flex-column py-4 backgroundSecondary"> <!-- justify-content-center -->
<main class="form-signin w-100 mx-auto"> <!-- container mx-auto -->
    <% Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) { %>
    <div class="py-5 text-center">
        <h2>Non sei autorizzato a visualizzare il contenuto di questa pagina.</h2>
        <a class="removeDecorations" href="index.jsp">Torna alla homepage</a>
    </div>
    <% } else {
        ConsoleBean c = (ConsoleBean) session.getAttribute("product");
    %>

    <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary backgroundColor" id="updateConsoleForm" enctype="multipart/form-data">
        <h1 class="h3 mb-3 fw-normal">Dati console</h1>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="nomeProdotto" id="floatingNome" placeholder="Nome" value="<%=c.getNome()%>">
            <label for="floatingNome">Nome</label>
            <div class="invalid-feedback" id="nomeProdottoInvalid">
                Inserisci un nome valido
            </div>
        </div>

        <div class="row">
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="edizione" id="floatingEdizione" placeholder="Edizione" value="<%=c.getEdizione()%>">
                    <label for="floatingEdizione">Edizione</label>
                    <div class="invalid-feedback" id="edizioneInvalid">
                        Inserisci un'edizione valida
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="prezzo" id="floatingPrezzo" placeholder="(EUR €)" value="<%=c.getPrezzo()%>">
                    <label for="floatingPrezzo">Prezzo</label>
                    <div class="invalid-feedback" id="cognomeInvalid">
                        Inserisci un prezzo valido
                    </div>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="famiglia" id="floatingFamiglia" placeholder="Famiglia" value="<%=c.getFamiglia()%>">
            <label for="floatingFamiglia">Famiglia</label>
            <div class="invalid-feedback" id="famigliaInvalid">
                Inserisci una famiglia valida
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="annoRilascio" id="floatingAnnoRilascio" placeholder="Anno" maxlength="4" value="<%=c.getAnnoRilascio()%>">
            <label for="floatingAnnoRilascio">Anno rilascio</label>
            <div class="invalid-feedback" id="annoRilascioInvalid">
                Inserisci un anno di rilascio valido
            </div>
        </div>

        <div class="mb-3">
            <input class="form-control" type="file" name="img" id="formFileMultiple" accept="image/*" multiple>
            <div class="invalid-feedback" id="imgInvalid">
                Inserisci delle immagini
            </div>
        </div>

        <button class="w-100 btn btn-lg btn-outline-dark flex-shrink-0" type="submit" id="updateConsoleSubmit">Modifica console</button>
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