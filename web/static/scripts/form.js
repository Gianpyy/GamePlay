//Ogni volta che viene tolto il focus da un campo input
$("input").blur(function () {
    //Se non è stato inserito nulla, avviso l'utente che deve inserire qualcosa nel campo
    if ($(this).val().trim() === "") {
        //Aggiungo la classe is-invalid di bootstrap
        $(this).removeClass("is-valid")
        $(this).addClass("is-invalid")

        //Rendo visibile il div con il messaggio di errore
        let inputId = "#" + $(this).attr("name") + "Invalid"
        $(inputId).removeAttr("hidden")
    }
    else {
        //Se è stato inserito qualcosa, rendo valido il campo input inserendo la classe is-valid di bootstrap
        $(this).removeClass("is-invalid")
        $(this).addClass("is-valid")

        //Rendo invisibile il div con il messaggio di errore
        let inputId = "#" + $(this).attr("name") + "Invalid"
        $(inputId).attr("hidden")
    }
})

//Quando viene effettuato il submit del form di input
$("#loginForm").submit(function (event) {
    //Blocco temporaneamente il submit
    event.preventDefault()
    let data = {}

    //Controllo se i valori inseriti nei campi di input sono validi
    let formValid = true
    $("#loginForm").find(".form-control").each(function () {
        if ($(this).val().trim() === "") {
            //Se il valore non è valido, aggiungo la classe is-invalid di bootstrap al campo e visualizzo il div con il messaggio di errore
            formValid = false
            $(this).removeClass("is-valid")
            $(this).addClass("is-invalid")
            let inputId = "#" + $(this).attr("name") + "Invalid"
            $(inputId).removeAttr("hidden")
        }
        else {
            //Se il valore è valido, rendo valido il campo input inserendo la classe is-valid di bootstrap
            $(this).removeClass("is-invalid")
            $(this).addClass("is-valid")

            //Rendo invisibile il div con il messaggio di errore
            let inputId = "#" + $(this).attr("name") + "Invalid"
            $(inputId).attr("hidden")

            //Aggiungo il valore del campo ai dati da mandare alla request
            let inputField = $(this).attr("name")
            data[inputField] = $(this).val()
        }

        console.log($(this).attr("name")+": "+formValid)
    })
    //console.log("formValid: "+formValid)
    console.log(data)

    //Se il form è valido, lo invio
    if (formValid) {
        console.log("Form valido")
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
                    break
                case "noUserFound":
                    $("#error div").html("L'username e/o la password inseriti non sono corretti.")
                    $("#error").removeAttr("hidden")
                    break
                case "success":
                    window.location.replace("index.jsp")
                    break
            }
        })
    }
})

$("#registerButton").click(function () {
    window.location.href = "register.jsp"
})