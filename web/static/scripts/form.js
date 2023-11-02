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
        }
    }
})



//Quando clicko il bottone per mostrare la password, rendo visibile la password e il bottone per nascondere la password
$("#passwordShow").click(function (event) {
    event.preventDefault()
    $("#floatingPassword").attr("type", "text")
    $("#passwordHide").show()
    $("#passwordShow").hide()
})

//Quando clicko il bottone per nascondere la password, rendo visibile la password e il bottone per mostrare la password
$("#passwordHide").click(function (event) {
    event.preventDefault()
    $("#floatingPassword").attr("type", "password")
    $("#passwordShow").show()
    $("#passwordHide").hide()
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