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