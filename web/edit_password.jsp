<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Modifica password</title>
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
    <form class="p-4 p-md-5 border rounded-3 bg-body-tertiary backgroundColor" id="editPasswordForm">
        <div class="text-center">
            <img class="logo mb-4" src="./static/img/logo_placeholder.png" alt="cool logo" onclick="window.location.href = 'index.jsp'">
        </div>
        <div class="p-3 border border-danger rounded-3 text-danger mb-3" id="error" hidden>
            <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-exclamation-triangle" viewBox="0 0 16 16">
                <path d="M7.938 2.016A.13.13 0 0 1 8.002 2a.13.13 0 0 1 .063.016.146.146 0 0 1 .054.057l6.857 11.667c.036.06.035.124.002.183a.163.163 0 0 1-.054.06.116.116 0 0 1-.066.017H1.146a.115.115 0 0 1-.066-.017.163.163 0 0 1-.054-.06.176.176 0 0 1 .002-.183L7.884 2.073a.147.147 0 0 1 .054-.057zm1.044-.45a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566z"/>
                <path d="M7.002 12a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 5.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995z"/>
            </svg>
            <div></div>
        </div>
        <h1 class="h3 mb-3 fw-normal">Modifica password</h1>
        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="floatingPassword" placeholder="password" name="oldPassword">
            <label for="floatingPassword">Vecchia password</label>
            <div class="invalid-feedback" id="oldPasswordInvalid">
                Inserisci una password
            </div>
        </div>

        <div class="form-floating mb-3">
            <input type="password" class="form-control" id="floatingNewPassword" placeholder="password" name="newPassword">
            <label for="floatingNewPassword">Nuova password</label>
        </div>

        <div class="form-floating">
            <input type="password" class="form-control" id="floatingNewPasswordRepeat" placeholder="password" name="newPasswordRepeat">
            <label for="floatingNewPasswordRepeat">Ripeti nuova password</label>
            <div class="invalid-feedback" id="newPasswordInvalid">
                Le password non corrispondono
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
        <button class="w-100 btn btn-lg btn-outline-dark flex-shrink-0" type="submit">Modifica password</button>
    </form>
</main>

<div class="modal fade" id="passwordEdited" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Password modificata con successo</h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-dark flex-shrink-0" onclick="location.replace('index.jsp')">Portami alla homepage</button>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="static/scripts/validate.js"></script>
<script src="static/scripts/form.js"></script>
</body>
</html>