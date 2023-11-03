function validateInput(inputToValidate, inputType) {
    switch (inputType) {
        case "username":
            return validateUsername(inputToValidate)

        case "password":
            return validatePassword(inputToValidate)

        case "codiceFiscale":
            return validateCodiceFiscale(inputToValidate)

        case "dataNascita":
            return validateData(inputToValidate)

        case "nome":
            return validateNome(inputToValidate)

        case "cognome":
        case "citta":
        case "cc-name":
            return validateCognome(inputToValidate)

        case "email":
            return validateEmail(inputToValidate)

        case "indirizzo":
            return validateIndirizzo(inputToValidate)

        case "provincia":
            return validateProvincia(inputToValidate)

        case "cap":
            return validateCAP(inputToValidate)

        case "cc-number":
            return validateCardNumber(inputToValidate)

        case "cc-expiration":
            return validateScadenza(inputToValidate)

        case "cc-cvv":
            return validateCVV(inputToValidate)

        default:
            return true
    }
}

function validateNome(stringToValidate) {
    let regex = /^[A-Za-z]+$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateCognome(stringToValidate) {
    let regex = /^[A-Za-z][A-Za-z\s']*$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateUsername(stringToValidate) {
    return (stringToValidate.length < 21 && stringToValidate.length > 3)
}

function validatePassword(stringToValidate) {
    return stringToValidate !== ""
}

function validateCodiceFiscale(stringToValidate) {
    let regex = /^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$/i
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateData(dataToValidate) {
    //Recupero la data di oggi e la data di nascita
    let today = new Date()
    let birthDate = new Date(dataToValidate)

    //Calcolo l'età
    let age = today.getFullYear() - birthDate.getFullYear()
    let monthOffset = today.getMonth() - birthDate.getMonth()

    //Se la differenza tra il mese di nascita e il mese corrente è <0 allora ancora devo compiere gli anni
    //Se la differenza è 0 siamo nel mese del compleanno della persona ma devo controllare se effettivamente è passato il giorno del compleanno
    if (monthOffset < 0 || (monthOffset === 0 && today.getDate() < birthDate.getDate())) {
        age--
    }

    //Restituisco true se l'utente ha almeno 13 anni
    return age >= 13;
}

function validateEmail(stringToValidate) {
    let regex = /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateIndirizzo(stringToValidate) {
    let regex = /^[A-Za-z][A-Za-z\s']*,\s[0-9]{1,3}$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateProvincia(stringToValidate) {
    let regex = /^[A-Z]{2}$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateCAP(stringToValidate) {
    let regex = /^\d{5}$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateCardNumber(stringToValidate) {
    let regex = /^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}

function validateScadenza(stringToValidate) {
    //Controllo che la stringa abbia un formato valido
    let regex = /^\d{2}\/\d{2}$/
    if(!regex.test(stringToValidate)) {
        return false
    }

    //Se ha un formato valido

    //Recupero la data di oggi
    let today = new Date()

    //Recupero mese e anno di scadenza della carta
    let expiryMonth = stringToValidate.substring(0,2)
    let yearPrefix = "20"
    let expiryYear = yearPrefix.concat(stringToValidate.substring(3))

    //Calcolo quanto manca alla scadenza
    let age = expiryYear - today.getFullYear();

    //Se il mese corrente è prima del mese di scadenza, la carta è ancora valida quindi aggiungo 1 per segnalare che è valida
    let monthOffset = today.getMonth()+1 - expiryMonth
    if (monthOffset < 0) {
        age++
    }

    //Restituisco true se l'utente ha almeno 13 anni
    return age > 0;
}

function validateCVV(stringToValidate) {
    let regex = /^\d{3}$/
    console.log(stringToValidate+": "+regex.test(stringToValidate))
    return regex.test(stringToValidate)
}
