package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdineBean implements Serializable {
    /*
        VARIABILI DI CLASSE
     */
    private int numeroOrdine;
    private Date data;
    private float totale;
    private String metodoPagamento;
    private String stato;
    private String indirizzo;
    private List<ProdottoBean> prodotti;
    private List<Integer> quantitaProdotti;
    private int userID;

    /*
        COSTRUTTORI
     */

    public OrdineBean() {
        prodotti = new ArrayList<>();
        quantitaProdotti = new ArrayList<>();
    }

    public OrdineBean(int numeroOrdine, Date data, float totale, String metodoPagamento, String stato, String indirizzo, List<ProdottoBean> prodotti, List<Integer> quantitaProdotti, int userID) {
        this.numeroOrdine = numeroOrdine;
        this.data = data;
        this.totale = totale;
        this.metodoPagamento = metodoPagamento;
        this.stato = stato;
        this.indirizzo = indirizzo;
        this.prodotti = prodotti;
        this.quantitaProdotti = quantitaProdotti;
        this.userID = userID;
    }

    public OrdineBean(int numeroOrdine, Date data, float totale, String metodoPagamento, String indirizzo, List<ProdottoBean> prodotti, List<Integer> quantitaProdotti, int userID) {
        this.numeroOrdine = numeroOrdine;
        this.data = data;
        this.totale = totale;
        this.metodoPagamento = metodoPagamento;
        this.indirizzo = indirizzo;
        stato = null;
        this.prodotti = prodotti;
        this.quantitaProdotti = quantitaProdotti;
        this.userID = userID;
    }

    /*
        GETTERS
     */
    public int getNumeroOrdine() {
        return numeroOrdine;
    }

    public Date getData() {
        return data;
    }

    public float getTotale() {
        return totale;
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

    public List<ProdottoBean> getProdotti() {
        return prodotti;
    }

    public List<Integer> getQuantitaProdotti() {
        return quantitaProdotti;
    }

    public int getUserID() {
        return userID;
    }

    /*
                SETTERS
             */
    public void setNumeroOrdine(int numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTotale(float totale) {
        this.totale = totale;
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

    public void setProdotti(List<ProdottoBean> prodotti) {
        this.prodotti = prodotti;
    }

    public void setQuantitaProdotti(List<Integer> quantitaProdotti) {
        this.quantitaProdotti = quantitaProdotti;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    //Altri metodi
    public void addProdotto(ProdottoBean prodotto, int quantita) {
        prodotti.add(prodotto);
        quantitaProdotti.add(quantita);
    }
}
