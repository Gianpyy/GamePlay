package model;

import java.util.Date;

public class AnagraficaUtente {
    /*
        VARIABILI DI CLASE
     */
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    private String sesso;

    /*
        COSTRUTTORI
     */
    public AnagraficaUtente() {
    }

    public AnagraficaUtente(String codiceFiscale, String nome, String cognome, Date dataDiNascita, String sesso) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.sesso = sesso;
    }

    /*
        GETTERS
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public String getSesso() {
        return sesso;
    }

    /*
        SETTERS
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }
}
