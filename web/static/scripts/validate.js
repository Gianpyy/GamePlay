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
            return validateCognome(inputToValidate)


        default:
            return true
    }
}

function validateNome(stringToValidate) {
    return stringToValidate !== ""
}

function validateCognome(stringToValidate) {
    return stringToValidate !== ""
}

function validateUsername(stringToValidate) {
    return (stringToValidate.length < 21 && stringToValidate.length > 3)
}

function validatePassword(stringToValidate) {
    return stringToValidate !== ""
}

function validateCodiceFiscale(stringToValidate) {
    return (stringToValidate.length === 16)
}

function validateData(dataToValidate) {
    return true
}

