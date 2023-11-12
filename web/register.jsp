<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Registrati</title>
    <link rel="stylesheet" href="static/styles/styles.scss">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- Bootstrap core   --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <%-- Bootstrap icons   --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</head>
<body class="container d-flex align-items-center justify-content-center flex-column py-4 backgroundSecondary">
<main class="form-signin w-100 mx-auto">
    <form class="p-4 p-md-5 border rounded-3 backgroundColor" id="registerForm">
        <div class="text-center">
            <img class="logo mb-4" src="./static/img/logo_placeholder.png" alt="cool logo" onclick="window.location.href = 'index.jsp'">
        </div>
        <h1 class="h3 mb-3 fw-normal">Dati anagrafici</h1>
        <div class="row">
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="nome" id="floatingNome" placeholder="Nome">
                    <label for="floatingNome">Nome</label>
                    <div class="invalid-feedback" id="nomeInvalid">
                        Inserisci un nome valido
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="cognome" id="floatingCognome" placeholder="Cognome">
                    <label for="floatingCognome">Cognome</label>
                    <div class="invalid-feedback" id="cognomeInvalid">
                        Inserisci un cognome valido
                    </div>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="text" class="form-control" name="codiceFiscale" maxlength="16" id="floatingCodiceFiscale" placeholder="Codice Fiscale">
            <label for="floatingCodiceFiscale">Codice Fiscale</label>
            <div class="invalid-feedback" id="codiceFiscaleInvalid">
                Inserisci un codice fiscale valido
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="date" class="form-control" name="dataNascita" id="floatingDataNascita">
            <label for="floatingDataNascita">Data di nascita</label>
            <div class="invalid-feedback" id="dataNascitaInvalid">
                Devi avere almeno 13 anni per iscriverti
            </div>
        </div>

        <div class="sesso" id="sesso">
            Sesso:
            <div class="form-check form-check-inline mb-3">
                <input class="form-check-inline sessoValue" type="radio" name="sesso" value="M" id="floatingSessoM" checked="">
                <label class="form-check-label" for="floatingSessoM">M</label>
            </div>

            <div class="form-check form-check-inline mb-3">
                <input class="form-check-inline sessoValue" type="radio" name="sesso" value="F" id="floatingSessoF">
                <label class="form-check-label" for="floatingSessoF">F</label>
            </div>
            <div class="invalid-feedback" id="sessoInvalid">
                Seleziona un valore
            </div>
        </div>

        <hr>

        <h1 class="h3 mb-3 fw-normal">Account</h1>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="floatingUser" placeholder="username" name="username">
            <label for="floatingUser">Username</label>
            <div class="invalid-feedback" id="usernameInvalid">
                La lunghezza dell'username deve essere compresa tra i 4 e i 20 caratteri
            </div>
        </div>

        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="password" name="password">
            <label for="floatingPassword">Password</label>
            <div class="invalid-feedback" id="passwordInvalid">
                Inserisci una password
            </div>
            <button class="password-toggle my-3" type="button" id="passwordShow">
                <i class="bi bi-eye"></i>
                Mostra password
            </button>
            <button class="password-toggle my-3" type="button" id="passwordHide">
                <i class="bi bi-eye-slash"></i>
                Nascondi password
            </button>
        </div>
        <button class="w-100 btn btn-lg btn-outline-dark flex-shrink-0" type="submit" id="registerSubmit">Registrati</button>
    </form>
</main>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
</body>
</html>
