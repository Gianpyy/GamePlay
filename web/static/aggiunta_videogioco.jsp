<%@ page import="model.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Title</title>
</head>
<body>
<form class="p-4 p-md-5 border rounded-3 bg-body-tertiary" id="addVideogiocoForm" enctype="multipart/form-data">
  <h1 class="h3 mb-3 fw-normal">Dati videogioco</h1>
  <div class="form-floating mb-3">
    <input type="text" class="form-control" name="nomeProdotto" id="floatingNome" placeholder="Nome">
    <label for="floatingNome">Nome</label>
    <div class="invalid-feedback" id="nomeProdottoInvalid">
      Inserisci un nome valido
    </div>
  </div>

  <div class="form-floating mb-3">
    <input type="text" class="form-control" name="edizione" id="floatingEdizione" placeholder="Edizione">
    <label for="floatingEdizione">Edizione</label>
    <div class="invalid-feedback" id="edizioneInvalid">
      Inserisci un'edizione valida
    </div>
  </div>

  <div class="row">
    <div class="col">
      <div class="form-floating mb-3">
        <select class="form-select form-control" name="piattaforma" id="floatingPiattaforma">
          <option selected value="notSelected">Seleziona una piattaforma</option>
          <option value="PlayStation 4">PlayStation 4</option>
          <option value="PlayStation 5">PlayStation 5</option>
          <option value="Xbox Series X">Xbox Series X</option>
          <option value="Xbox Series S">Xbox Series S</option>
          <option value="Nintendo Switch">Nintendo Switch</option>
          <option value="PC">PC</option>
        </select>
        <label for="floatingPiattaforma">Piattaforma</label>
        <div class="invalid-feedback" id="piattaformaInvalid">
          Seleziona una piattaforma
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
    <textarea class="form-control" name="descrizione" id="floatingDescrizione" placeholder="Descrizione" maxlength="250"></textarea>
    <label for="floatingDescrizione">Descrizione</label>
    <div class="invalid-feedback" id="descrizioneInvalid">
      Inserisci una descrizione
    </div>
  </div>

  <div class="form-floating mb-3">
    <select class="form-select form-control" name="categoria" id="floatingCategoria">
      <option selected value="notSelected">Seleziona...</option>
      <% for (Categoria c : Categoria.values()) { %>
        <option value="<%=c%>"><%=c%></option>
      <% } %>
    </select>
    <label for="floatingCategoria">Categoria</label>
    <div class="invalid-feedback" id="categoriaInvalid">
      Seleziona una categoria
    </div>
  </div>

  <div class="form-floating mb-3">
    <input type="date" class="form-control" name="dataRilascio" id="floatingDataRilascio">
    <label for="floatingDataRilascio">Data di rilascio</label>
    <div class="invalid-feedback" id="dataRilascioInvalid">
      Inserisci una data valida
    </div>
  </div>

  <div class="form-floating mb-3">
    <select class="form-select form-control" name="condizioni" id="floatingCondizioni">
      <option selected value="notSelected">Seleziona...</option>
      <option value="Nuovo">Nuovo</option>
      <option value="Usato">Usato</option>
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
        <option selected value="notSelected">Seleziona...</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
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
          <option selected value="notSelected">Seleziona...</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
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
      <option selected value="notSelected">Seleziona...</option>
      <option value="3">3</option>
      <option value="7">7</option>
      <option value="12">12</option>
      <option value="16">16</option>
      <option value="18">18</option>
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
  <button class="w-100 btn btn-lg btn-primary" type="submit" id="videogiocoSubmit">Aggiungi videogioco</button>
</form>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</body>
</html>
