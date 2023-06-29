package model;

import java.io.Serializable;

public class GadgetBean extends ProdottoBean implements Serializable {
    /*
        ATTRIBUTI
     */
    private String produttore;
    private String serie;

    /*
        COSTRUTTORI
     */

    public GadgetBean() {
    }

    public GadgetBean(String barcode, String nome, float prezzo, int sconto, String produttore, String serie) {
        super(barcode, nome, prezzo, sconto);
        this.produttore = produttore;
        this.serie = serie;
    }

    /*
        GETTERS
     */
    public String getProduttore() {
        return produttore;
    }

    public String getSerie() {
        return serie;
    }

    /*
        SETTERS
     */
    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
}
