function redirectToProductPage (id) {
    console.log("function redirectToProductPage with id: "+id);

    //Formatto i dati per la request
    let data = {productId: id};
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
                window.location.replace("videogioco.jsp")
                break

            case "console" :
                window.location.replace("console.jsp")
                break

            case "gadget" :
                window.location.replace("gadget.jsp")
                break
        }
    })
}