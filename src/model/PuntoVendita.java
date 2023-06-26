package model;

public class PuntoVendita {
    /*
        VARIABILI DI CLASSE
     */
    private int codice;
    private String via;
    private String civico;
    private String citta;
    private String provincia;

    /*
        COSTRUTTORI
     */
    public PuntoVendita() {
    }

    public PuntoVendita(int codice, String via, String civico, String citta, String provincia) {
        this.codice = codice;
        this.via = via;
        this.civico = civico;
        this.citta = citta;
        this.provincia = provincia;
    }

    /*
        GETTERS
     */
    public int getCodice() {
        return codice;
    }

    public String getVia() {
        return via;
    }

    public String getCivico() {
        return civico;
    }

    public String getCitta() {
        return citta;
    }

    public String getProvincia() {
        return provincia;
    }

    /*
        SETTERS
     */
    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
