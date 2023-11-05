<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Title</title>
</head>
<body>
<form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" id="addVideogiocoForm">
  <h1 class="h3 mb-3 fw-normal">Dati videogioco</h1>
  <div class="form-floating mb-3">
    <input type="text" class="form-control" name="nomeProdotto" id="floatingNome" placeholder="Nome">
    <label for="floatingNome">Nome</label>
    <div class="invalid-feedback" id="nomeProdottoInvalid">
      Inserisci un nome valido
    </div>
  </div>

  <div class="row">
    <div class="col">
      <div class="form-floating mb-3">
        <input type="text" class="form-control" name="edizione" id="floatingEdizione" placeholder="Edizione">
        <label for="floatingEdizione">Edizione</label>
        <div class="invalid-feedback" id="edizioneInvalid">
          Inserisci un'edizione valida
        </div>
      </div>
    </div>
    <div class="col">
      <div class="form-floating mb-3">
        <input type="text" class="form-control" name="prezzo" id="floatingPrezzo" placeholder="(EUR €)">
        <label for="floatingPrezzo">Prezzo</label>
        <div class="invalid-feedback" id="cognomeInvalid">
          Inserisci un prezzo valido
        </div>
      </div>
    </div>
  </div>

  <div class="form-floating mb-3">
    <input type="text" class="form-control" name="famiglia" id="floatingFamiglia" placeholder="Famiglia">
    <label for="floatingFamiglia">Famiglia</label>
    <div class="invalid-feedback" id="famigliaInvalid">
      Inserisci una famiglia valida
    </div>
  </div>

  <div class="form-floating mb-3">
    <input type="text" class="form-control" name="annoRilascio" id="floatingAnnoRilascio" placeholder="Nome">
    <label for="floatingAnnoRilascio">Anno rilascio</label>
    <div class="invalid-feedback" id="annoRilascioInvalid">
      Inserisci un anno di rilascio valido
    </div>
  </div>

  <button class="w-100 btn btn-lg btn-primary" type="submit" id="videogiocoSubmit">Aggiungi videogioco</button>
</form>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</body>
</html>
