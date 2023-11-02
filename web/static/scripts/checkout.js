$(document).ready(function () {
    // Initialize tooltips
    let tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    let tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })
})

$("#checkoutButton").submit(function () {
    let data = {actionType: "checkoutButton"}
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
            case "userNotLogged":
                alert("Devi essere autenticato per effettuare un ordine.")
                break
            case "cartEmpty":
                alert("Non è possibile effettuare un ordine con un carrello vuoto.")
                break
            case "success":
                window.location.href = "checkout.jsp"
        }
    })
})

