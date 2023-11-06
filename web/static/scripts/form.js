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
                submitAddProduct(data)
                break
            case "addConsoleForm":
                data["tipo"] = "console"
                submitAddProduct(data)
                break
            case "addGadgetForm":
                data["tipo"] = "gadget"
                submitAddProduct(data)
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

$(".sessoValue").change(function () {
    console.log("tipo changed")
    let selectedValue = $('input[name=tipo]:checked').val();
    switch (selectedValue) {
        case "V":
            let videogameCollapse = new bootstrap.Collapse(document.querySelector('#collapseVideogioco'), {toggle: false})
            videogameCollapse.slideDown()
            break
        case "G":
            let gadgetCollapse = new bootstrap.Collapse(document.querySelector('#collapseGadget'), {toggle: false})
            gadgetCollapse.slideDown()
            break
        case "C":
            let consoleCollapse = new bootstrap.Collapse(document.querySelector('#collapseConsole'), {toggle: false})
            consoleCollapse.slideDown()
            break
    }
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

function submitLogout() {
    fetch("Logout", {
        method: "POST",
        headers: {
            'Accept': "application/json",
            'Content-Type': "application/json"},
    }).then(res => {
        console.log("Request complete! response: ", res)
        window.location.href = "index.jsp"
        window.location.reload()
    })
}


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


function submitAddProduct(data) {
    fetch("AddProduct", {
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

// $(document).ready(function () {
//     $("#passwordHide").hide()
// })