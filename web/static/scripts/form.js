//Ogni volta che viene tolto il focus da un campo input
$("input").blur(function () {
    //Controllo se l'input è valido
    if (validateInput($(this).val().trim(), $(this).attr("name"))) {
        //Se è valido, rendo valido il campo input inserendo la classe is-valid di bootstrap
        $(this).removeClass("is-invalid")
        $(this).addClass("is-valid")

        //Rendo invisibile il div con il messaggio di errore
        let inputId = "#" + $(this).attr("name") + "Invalid"
        console.log("hidden: "+inputId)
        $(inputId).hide()
    }
    else {
        //Aggiungo la classe is-invalid di bootstrap
        $(this).removeClass("is-valid")
        $(this).addClass("is-invalid")

        //Rendo visibile il div con il messaggio di errore
        let inputId = "#" + $(this).attr("name") + "Invalid"
        console.log("visible: "+inputId)
        $(inputId).show()
    }
})

//Quando viene effettuato il submit di un form
$("form").submit(function (event) {
    //Blocco temporaneamente il submit
    event.preventDefault()
    let data = {}

    //Controllo se i valori inseriti nei campi di input sono validi
    let formValid = true
    $(this).find(".form-control").each(function () {
        if (validateInput($(this).val().trim(), $(this).attr("name"))) {
            //Se il valore è valido, rendo valido il campo input inserendo la classe is-valid di bootstrap
            $(this).removeClass("is-invalid")
            $(this).addClass("is-valid")

            //Rendo invisibile il div con il messaggio di errore
            let inputId = "#" + $(this).attr("name") + "Invalid"
            $(inputId).hide()

            //Aggiungo il valore del campo ai dati da mandare alla request
            let inputField = $(this).attr("name")
            data[inputField] = $(this).val()
        }
        else {
            //Se il valore non è valido, aggiungo la classe is-invalid di bootstrap al campo e visualizzo il div con il messaggio di errore
            formValid = false
            $(this).removeClass("is-valid")
            $(this).addClass("is-invalid")
            let inputId = "#" + $(this).attr("name") + "Invalid"
            $(inputId).show()
        }

        console.log($(this).attr("name")+": "+formValid)
    })

    //Controllo se i valori inseriti nei campi select sono corretti
    $(this).find(".form-select").each(function () {
        if ($(this).val() === "notSelected") {
            //Se il valore non è valido, aggiungo la classe is-invalid di bootstrap al campo e visualizzo il div con il messaggio di errore
            formValid = false
            $(this).removeClass("is-valid")
            $(this).addClass("is-invalid")
            let inputId = "#" + $(this).attr("name") + "Invalid"
            $(inputId).show()
        }
        else {
            //Se il valore è valido, rendo valido il campo input inserendo la classe is-valid di bootstrap
            $(this).removeClass("is-invalid")
            $(this).addClass("is-valid")

            //Rendo invisibile il div con il messaggio di errore
            let inputId = "#" + $(this).attr("name") + "Invalid"
            $(inputId).hide()

            //Aggiungo il valore del campo ai dati da mandare alla request
            let inputField = $(this).attr("name")
            data[inputField] = $(this).val()
        }
    })

    //Controllo sul valore sesso (solo form register)
    if($(this).attr("id") === "registerForm") {
        let selected = false

        //Se è stato selezionato un valore, lo aggiungo ai dati da inviare alla request
        if($("#sesso .sessoValue:checked").length > 0) {
            selected = true

            //Aggiungo il valore del campo ai dati da mandare alla request
            data["sesso"] = $("#sesso .sessoValue:checked").val()
        }
        else {
            formValid = false
            $("#sessoInvalid").show()
        }

        console.log("sesso: "+selected)
    }

    //Controllo valore metodo pagamento (solo form checkout)
    if($(this).attr("id") === "checkoutForm") {
        data["pagamento"] = $("input[name='paymentMethod']:checked").val()
    }

    //Controllo che il valore della nuova password sia uguale in entrambi i campi del form (solo form modifica password)
    if ($(this).attr("id") === "editPasswordForm") {
        let newPassword = $("#floatingNewPassword").val()
        let newPasswordRepeat = $("#floatingNewPasswordRepeat").val()

        if(newPassword.trim() !== newPasswordRepeat.trim()) {
            formValid = false
            $("#floatingNewPassword").removeClass("is-valid")
            $("#floatingNewPasswordRepeat").removeClass("is-valid")
            $("#floatingNewPassword").addClass("is-invalid")
            $("#floatingNewPasswordRepeat").addClass("is-invalid")
            $("#newPasswordInvalid").show()
        }
    }

    //Recupero i file dal form (solo form aggiunta)
    let formData = new FormData()
    if($(this).attr("id") === "addVideogiocoForm" || $(this).attr("id") === "addConsoleForm" || $(this).attr("id") === "addGadgetForm" || $(this).attr("id") === "updateVideogiocoForm" || $(this).attr("id") === "updateGadgetForm" || $(this).attr("id") === "updateConsoleForm") {
        //Aggiungo i file
        let input = $("input[type=file]")[0]
        $.each(input.files, function (index, file) {
            if(file.type.startsWith("image/")) {
                formData.append("img", file)
            }
            else {
                console.warn("Il file "+file.name+" non è un'immagine e non verrà aggiunto")
            }
        })
    }


    //Se il form è valido, lo invio
    if (formValid) {
        console.log("Form valido")
        console.log(data)
        let formId = $(this).attr("id")
        switch (formId) {
            case "loginForm":
                submitLogin(data)
                break
            case  "registerForm":
                submitRegister(data)
                break
            case "checkoutForm":
                submitCheckout(data)
                break
            case "editPasswordForm":
                submitEditPassword(data)
                break
            case "addVideogiocoForm":
                data["tipo"] = "videogioco"
                submitAddProduct(data, formData)
                break
            case "addConsoleForm":
                data["tipo"] = "console"
                submitAddProduct(data, formData)
                break
            case "addGadgetForm":
                data["tipo"] = "gadget"
                submitAddProduct(data, formData)
                break

            case "updateVideogiocoForm":
                data["tipo"] = "videogioco"
                submitUpdateProduct(data, formData)
                break

            case "updateGadgetForm":
                data["tipo"] = "gadget"
                submitUpdateProduct(data, formData)
                break

            case "updateConsoleForm":
                data["tipo"] = "console"
                submitUpdateProduct(data, formData)
                break

            case "filtroData":
                data["filterType"] = "data"
                submitOrderFilter(data)
                break

            case "filtroID":
                data["filterType"] = "orderID"
                submitOrderFilter(data)
                break

            case "filtroUtente":
                data["filterType"] = "userId"
                submitOrderFilter(data)
                break

            case "searchForm":
                submitSearchForm(data)
                break
        }
    }
})



//Quando clicko il bottone per mostrare la password, rendo visibile la password e il bottone per nascondere la password
$("#passwordShow").click(function (event) {
    event.preventDefault()
    $("#floatingPassword").attr("type", "text")
    // $("#floatingNewPassword").attr("type", "text")
    // $("#floatingNewPasswordRepeat").attr("type", "text")
    $("#passwordHide").show()
    $("#passwordShow").hide()
})

//Quando clicko il bottone per nascondere la password, rendo visibile la password e il bottone per mostrare la password
$("#passwordHide").click(function (event) {
    event.preventDefault()
    $("#floatingPassword").attr("type", "password")
    // $("#floatingNewPassword").attr("type", "password")
    // $("#floatingNewPasswordRepeat").attr("type", "password")
    $("#passwordShow").show()
    $("#passwordHide").hide()
})


$("#floatingNewPasswordRepeat").blur(function () {
    let newPassword = $("#floatingNewPassword").val()
    let newPasswordRepeat = $("#floatingNewPasswordRepeat").val()

    if(newPassword.trim() !== newPasswordRepeat.trim()) {
        formValid = false
        $("#floatingNewPassword").removeClass("is-valid")
        $("#floatingNewPasswordRepeat").removeClass("is-valid")
        $("#floatingNewPassword").addClass("is-invalid")
        $("#floatingNewPasswordRepeat").addClass("is-invalid")
        $("#newPasswordInvalid").show()
    }
})

//Quando clicko il bottone per registrarsi nella pagina del login
$("#registerButton").click(function () {
    window.location.href = "register.jsp"
})


//Quando inserisco un valore nel campo cc-expiration, aggiungo automaticamente uno "/" dopo aver inserito i primi 2 caratteri
// $("#cc-expiration").on("input", "input:text",  function () {
//     console.log("Input event fired")
//     let input = $(this).val()
//     console.log("Expiration date current input: "+input)
//
//     //Rimuovo eventuali caratteri non numerici
//     let numericValue = input.replace(/\D/g, "");
//
//     if (input.length >= 2) {
//         // Formatto la data inserendo uno "/" dopo i primi due caratteri
//         let formattedValue = numericValue.substring(0, 2) + "/" + numericValue.substring(2);
//         input.val(formattedValue);
//     }
// })

//Funzione che effettua il submit del form di login alla servlet
function submitLogin(data) {
    fetch("Login", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        console.log("OPERATION-RESULT: "+res.headers.get("OPERATION-RESULT"))
        switch (res.headers.get("OPERATION-RESULT")) {
            //Visualizzo il messaggio di errore, se c'è
            case "error":
                $("#error div").html("Inserisci un username e/o una password.")
                $("#error").removeAttr("hidden")

                //Aggiungo la classe is-invalid agli elementi del form
                $("#loginForm").find(".form-control").each(function () {
                    $(this).removeClass("is-valid")
                    $(this).addClass("is-invalid")
                })
                break
            case "noUserFound":
                $("#error div").html("L'username e/o la password inseriti non sono corretti.")
                $("#error").removeAttr("hidden")

                //Aggiungo la classe is-invalid agli elementi del form
                $("#loginForm").find(".form-control").each(function () {
                    $(this).removeClass("is-valid")
                    $(this).addClass("is-invalid")
                })
                break
            case "success":
                window.location.replace("index.jsp")
                break
        }
    })
}

//Funzione che effettua il submit del form di registrazione alla servlet
function submitRegister(data) {
    fetch("Register", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        console.log("OPERATION-RESULT: "+res.headers.get("OPERATION-RESULT"))
        switch (res.headers.get("OPERATION-RESULT")) {
            //Visualizzo il messaggio di errore, se c'è
            case "error":
                //Aggiungo la classe is-invalid agli elementi del form
                $("#loginForm").find(".form-control").each(function () {
                    $(this).removeClass("is-valid")
                    $(this).addClass("is-invalid")
                })
                break
            case "success":
                window.location.replace("index.jsp")
                break
        }
    })
}

//Funzione che effettua il submit del form di checkout alla servlet
function submitCheckout(data) {
    data["actionType"] = "checkout"
    fetch("Checkout", {
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
                window.location.replace("carrello.jsp")
                alert("C'è stato un problema con la tua richiesta. Riprova.")
                break
            case "success":
                window.location.href = "riepilogo_ordine.jsp"
        }
    })
}

//Funzione che effettua il submit del form di logout alla servlet
function submitLogout() {
    fetch("Logout", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
    }).then(res => {
        console.log("Request complete! response: ", res)
        window.location.replace("index.jsp")
    })
}


//Funzione che effettua il submit del form di modifica password alla servlet
function submitEditPassword(data) {
    fetch("EditPassword", {
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
                alert("C'è stato un errore nell'operazione da te richiesta. Riprova")
                break
            case "newPasswordNotSame":
                $("#error div").html("Le nuove password non corrispondono.")
                $("#error").removeAttr("hidden")
                $("#floatingNewPassword").removeClass("is-valid")
                $("#floatingNewPasswordRepeat").removeClass("is-valid")
                $("#floatingNewPassword").addClass("is-invalid")
                $("#floatingNewPasswordRepeat").addClass("is-invalid")
                break
            case "oldPasswordError":
                $("#error div").html("La password che hai inserito non è corretta.")
                $("#error").removeAttr("hidden")
                $("#floatingPassword").removeClass("is-valid")
                $("#floatingPassword").addClass("is-invalid")
                break
            case "success":
                $("#passwordEdited").modal("show")
        }
    })
}


//Funzione che effettua il submit del form di aggiunta del prodotto alla servlet
function submitAddProduct(data, formData) {
    fetch("AddProduct", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        if (res.ok) {
            let productId = res.headers.get("PRODUCT-ID")
            formData.append("productId", productId)
            submitAddImage(formData)
        }
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


//Funzione che effettua il submit per l'aggiornamento delle immagini
function submitAddImage(formData) {
    fetch("UploadImage", {
        method: "POST",
        body: formData
    }).then(res => {
        console.log("Request complete! response: ", res)
    })
}

//Funzione che effettua il submit del form di modifica del prodotto alla servlet
function submitUpdateProduct(data, formData) {
    fetch("UpdateProduct", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        console.log("Request complete! response: ", res)
        if (res.ok) {
            let productId = res.headers.get("PRODUCT-ID")
            formData.append("productId", productId)
            submitAddImage(formData)
        }
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
                window.location.href = "gestione_catalogo.jsp"
        }
    })
}

//Funzione che effettua il submit del form di cambio stato dell'ordine alla servlet
function changeOrderStatus(id) {
    //Recupero i dati da inviare alla form
    let selectVal = $("#statoOrdine"+id).val()
    let data = {'orderId': id, 'newStatus': selectVal.toString()}
    console.log(data)

    //Invio la richiesta
    fetch("UpdateOrderStatus", {
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
        }
    })
}

//Funzione che effettua il submit del form di cancellazione di un ordine alla servlet
function deleteOrder(id) {
    //Recupero i dati da inviare alla form
    let data = {orderId: id}

    //Invio la richiesta
    fetch("DeleteOrder", {
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
                location.href = "../error_pages/unauthorized.jsp"
                break
            case "success":
                alert("Operazione eseguita con successo.")
                location.reload()
        }
    })
}


//Funzione che effettua la richiesta alla servlet di filtro per gli ordini
function submitOrderFilter(requestData) {
    fetch("OrderFilter", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(requestData)
    }).then(res => {
        if(!res.ok) {
            throw new Error("Errore nella richiesta"+res.statusText)
        }
        return res.json()
    }).then(responseData => {
        updateOrdini(responseData)
    })
}

//Aggiorna il div contente gli ordini nella pagina
function updateOrdini(data) {
    const ordiniContainer = $("#ordiniContainer");
    ordiniContainer.empty(); // Svuoto il contenuto attuale del div

    if (data.length === 0) {
        const noResultDiv = $("<div>").addClass("no-result-message")
        noResultDiv.text("Nessun ordine trovato")

        ordiniContainer.append(noResultDiv)
    }
    else  {
        $.each(data, function (index, ordine) {
            const ordineDiv = $("<div>").addClass("row");
            ordineDiv.html(`
            <div class="bg-body-secondary rounded-3 my-3">
                <div class="row">
                    <div class="col-8 d-flex flex-column align-items-start" id="textGestioneOrdini">
                        <div>
                            <h4>Numero ordine: ${ordine.numeroOrdine}</h4>
                        </div>
                        <div>
                            <h4>Effettuato il: ${ordine.data}</h4>
                        </div>
                    </div>
                    <div class="col-3 d-flex flex-column order-last align-self-center" id="buttonGestioneOrdini">
                          <select class="form-select" name="statoOrdine" id="statoOrdine${ordine.numeroOrdine}" onchange="changeOrderStatus('${ordine.numeroOrdine}')">
                                <option value="Pagamento ricevuto" ${ordine.stato === 'Pagamento Ricevuto' ? 'selected' : ''}>Pagamento ricevuto</option>
                                <option value="In preparazione alla spedizione" ${ordine.stato === 'In preparazione alla spedizione' ? 'selected' : ''}>In preparazione alla spedizione</option>
                                <option value="Spedito" ${ordine.stato === 'Spedito' ? 'selected' : ''}>Spedito</option>
                                <option value="In Consegna" ${ordine.stato === 'In consegna' ? 'selected' : ''}>In Consegna</option>
                                <option value="Consegnato" ${ordine.stato === 'Consegnato' ? 'selected' : ''}>Consegnato</option>
                            </select>
                          <button class="btn btn-outline-dark flex-shrink-0 btn-danger" onclick="deleteOrder(${ordine.numeroOrdine})">Elimina ordine</button>
                    </div>
                </div>
            </div>
            `);
            ordiniContainer.append(ordineDiv);
        })
    }

}

function submitSearchForm(data) {
    fetch("Search", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
        body: JSON.stringify(data)
    }).then(res => {
        if (res.ok) {
            window.location.href = "search_result.jsp"
        }
    })
}