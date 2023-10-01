package model;

import java.io.Serializable;
import java.util.Date;

public class AnagraficaUtenteBean implements Serializable {
    /*
        VARIABILI DI CLASE
     */
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    private String sesso;

    private int codiceUtente;

    /*
        COSTRUTTORI
     */
    public AnagraficaUtenteBean() {
    }

    public AnagraficaUtenteBean(String codiceFiscale, String nome, String cognome, Date dataDiNascita, String sesso, int codiceUtente) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.sesso = sesso;
        this.codiceUtente = codiceUtente;
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
    public int getCodiceUtente() {
        return codiceUtente;
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
    public void setCodiceUtente(int codiceUtente) {
        this.codiceUtente = codiceUtente;
    }
}
