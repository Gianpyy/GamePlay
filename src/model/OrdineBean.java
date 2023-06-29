package model;

import java.io.Serializable;
import java.util.Date;

public class OrdineBean implements Serializable {
    /*
        VARIABILI DI CLASSE
     */
    private String numeroOrdine;
    private Date data;
    private float totale;
    private float acconto;
    private String metodoPagamento;
    private String stato;
    private String indirizzo;

    /*
        COSTRUTTORI
     */

    public OrdineBean() {}

    public OrdineBean(String numeroOrdine, Date data, float totale, float acconto, String metodoPagamento, String stato, String indirizzo) {
        this.numeroOrdine = numeroOrdine;
        this.data = data;
        this.totale = totale;
        this.acconto = acconto;
        this.metodoPagamento = metodoPagamento;
        this.stato = stato;
        this.indirizzo = indirizzo;
    }

    public OrdineBean(String numeroOrdine, Date data, float totale, String metodoPagamento, String indirizzo) {
        this.numeroOrdine = numeroOrdine;
        this.data = data;
        this.totale = totale;
        this.metodoPagamento = metodoPagamento;
        this.indirizzo = indirizzo;
        acconto = -1;
        stato = null;
    }

    /*
        GETTERS
     */
    public String getNumeroOrdine() {
        return numeroOrdine;
    }

    public Date getData() {
        return data;
    }

    public float getTotale() {
        return totale;
    }

    public float getAcconto() {
        return acconto;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public String getStato() {
        return stato;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    /*
        SETTERS
     */
    public void setNumeroOrdine(String numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public void setAcconto(float acconto) {
        this.acconto = acconto;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
