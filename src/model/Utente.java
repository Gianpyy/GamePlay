package model;

public class Utente {

    /*
        VARIABILI DI CLASSE
     */
    private int codice;
    private String username;
    private String password;
    private boolean admin;

    /*
        COSTRUTTORI
     */
    public Utente() {}

    public Utente(int codice, String username, String password, boolean admin) {
        this.codice = codice;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }


    /*
        GETTERS
     */
    public int getCodice() {
        return codice;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAdmin() {
        return admin;
    }


    /*
        SETTERS
     */
    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
