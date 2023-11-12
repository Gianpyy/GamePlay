function redirectToProductPage (id) {
    console.log("function redirectToProductPage with id: "+id);

    //Formatto i dati per la request
    let data = {productId: id.toString()};
    console.log(JSON.stringify(data))

    //Creo la richiesta POST
    fetch("Product", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        switch (res.headers.get("PRODUCT-TYPE")) {
            case "videogioco" :
                window.location.href = "videogioco.jsp"
                break

            case "console" :
                window.location.href = "console.jsp"
                break

            case "gadget" :
                window.location.href = "gadget.jsp"
                break
        }
    })
}

function addProduct() {
    console.log("function addProduct")
    //Formatto i dati per la request
    let data = {actionType: "addProduct"};

    //Recupero la quantità di prodotto da aggiungere e la aggiungo alla request
    data["qta"] = $("#inputQuantity").val()
    console.log(JSON.stringify(data))

    //Creo la richiesta POST.
    fetch("Carrello", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        window.location.href = "carrello.jsp"
    })
}

function removeProductFromCart (id) {
    console.log("function removeProductFromCart with id: "+id);

    //Formatto i dati per la request
    let data = {actionType: "removeProduct", productId: id.toString()};
    console.log(JSON.stringify(data))

    //Creo la richiesta POST
    fetch("Carrello", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        window.location.href = "carrello.jsp"
    })

}

function emptyCart () {
    console.log("function emptyCart")

    //Creo la richiesta POST
    fetch("Carrello", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify({
            actionType: "emptyCart",
        })
    }).then(res => {
        console.log("Request complete! response: ", res)
        window.location.href = "carrello.jsp"
    })
}

function updateProductQuantity (id) {
    console.log("function updateProductQuantity")

    //Recupero il valore dell'opzione della select
    const selectId = "#"+id+"productCount"
    let newProductQuantity = $(selectId).val()
    console.log("New quantity: "+newProductQuantity)

    //Formatto i dati per la request
    let data = {actionType: "updateProductQuantity", productId: id.toString(), newQuantity: newProductQuantity};
    console.log(JSON.stringify(data))

    //Creo la richiesta POST
    fetch("Carrello", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        window.location.href = "carrello.jsp"
    })
}

function deleteProduct (id) {
    let data = {productId: id.toString()}

    fetch("DeleteProduct", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        let operationResult = res.headers.get("OPERATION-RESULT")
        console.log("OPERATION-RESULT: "+operationResult)
        switch (operationResult) {
            case "error":
                alert("C'è stato un errore nell'operazione. Riprova")
                break
            case "unauthorized":
                alert("Non sei autorizzato ad eseguire questa operazione.")
                break
            case "success":
                alert("Operazione eseguita con successo.")
                location.reload()
        }
    })
}

function redirectToEditPage(id, productType) {
    let data = {productId: id.toString(), productType: productType.toString()}
    fetch("RedirectToEditPage", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        let operationResult = res.headers.get("OPERATION-RESULT")
        console.log("OPERATION-RESULT: "+operationResult)
        switch (operationResult) {
            case "error":
                alert("C'è stato un errore nell'operazione. Riprova")
                break
            case "unauthorized":
                alert("Non sei autorizzato ad eseguire questa operazione.")
                break
            case "videogioco":
                window.location.href = "edit_videogioco.jsp"
                break
            case "gadget":
                window.location.href = "edit_gadget.jsp"
                break
            case "console":
                window.location.href = "edit_console.jsp"
                break
        }

    })
}

function changeDataOnVideogamePage(nome, prezzo, piattaforma, edizione, descrizione) {
    //Cambio gli elementi nella pagina
    $("#productName").html(nome)
    $("#productPrice span").text(prezzo + " €")
    $("#productPiattaforma span").text(piattaforma)
    $("#productEdizione span").text(edizione)
    $("#productDescrizione p").text(descrizione)

    //Disabilito il bottone dell'edizione attuale e riattivo gli altri
    $("#edizioni button").each(function () {
        let currentButton = $(this)

        if(currentButton.text() === edizione) {
            currentButton.prop("disabled", true)
        }
        else {
            currentButton.prop("disabled", false)
        }
    })

    //Disabilito il bottone della piattaforma attuale e riattivo gli altri
    $("#piattaforme button").each(function () {
        let currentButton = $(this)

        if(currentButton.text() === piattaforma) {
            currentButton.prop("disabled", true)
        }
        else {
            currentButton.prop("disabled", false)
        }
    })
}

function changeDataOnConsolePage(nome, prezzo, edizione) {
    //Cambio gli elementi nella pagina
    $("#productName").html(nome)
    $("#productPrice span").text(prezzo + " €")
    $("#productEdizione span").text(edizione)

    //Disabilito il bottone dell'edizione attuale e riattivo gli altri
    $("#edizioni button").each(function () {
        let currentButton = $(this)

        if(currentButton.text() === edizione) {
            currentButton.prop("disabled", true)
        }
        else {
            currentButton.prop("disabled", false)
        }
    })
}

