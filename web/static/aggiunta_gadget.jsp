<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Title</title>
</head>
<body>
<form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" id="addGadgetForm" enctype="multipart/form-data">
    <h1 class="h3 mb-3 fw-normal">Dati gadget</h1>
    <div class="form-floating mb-3">
        <input type="text" class="form-control" name="nomeProdotto" id="floatingNome" placeholder="Nome">
        <label for="floatingNome">Nome</label>
        <div class="invalid-feedback" id="nomeProdottoInvalid">
            Inserisci un nome valido
        </div>
    </div>

    <div class="form-floating mb-3">
        <input type="text" class="form-control" name="prezzo" id="floatingPrezzo" placeholder="(EUR €)">
        <label for="floatingPrezzo">Prezzo</label>
        <div class="invalid-feedback" id="cognomeInvalid">
            Inserisci un prezzo valido
        </div>
    </div>

    <div class="form-floating mb-3">
        <input type="text" class="form-control" name="produttore" id="floatingProduttore" placeholder="">
        <label for="floatingProduttore">Produttore</label>
        <div class="invalid-feedback" id="produttoreInvalid">
            Inserisci un produttore valido
        </div>
    </div>

    <div class="form-floating mb-3">
        <input type="text" class="form-control" name="serie" id="floatingSerie" placeholder="">
        <label for="floatingSerie">Serie</label>
        <div class="invalid-feedback" id="serieInvalid">
            Inserisci una serie valida
        </div>
    </div>

    <div class="mb-3">
        <input class="form-control" type="file" name="img" id="formFileMultiple" accept="image/*" multiple>
        <div class="invalid-feedback" id="imgInvalid">
            Inserisci delle immagini
        </div>
    </div>

    <button class="w-100 btn btn-lg btn-outline-dark flex-shrink-0" type="submit" id="gadgetSubmit">Aggiungi gadget</button>
</form>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</body>
</html>
