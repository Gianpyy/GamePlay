package model;

public class Gadget extends Prodotto {
    /*
        ATTRIBUTI
     */
    private String produttore;
    private String serie;

    /*
        COSTRUTTORI
     */

    public Gadget() {
    }

    public Gadget(String barcode, String nome, float prezzo, int sconto, String produttore, String serie) {
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
