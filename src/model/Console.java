package model;

public class Console extends Prodotto {
    /*
        ATTRIBUTI
     */
    private String famiglia;
    private String annoRilascio;

    /*
        COSTRUTTORI
     */

    public Console() {
    }

    public Console(String barcode, String nome, float prezzo, int sconto, String famiglia, String annoRilascio) {
        super(barcode, nome, prezzo, sconto);
        this.famiglia = famiglia;
        this.annoRilascio = annoRilascio;
    }

    /*
        GETTERS
     */
    public String getFamiglia() {
        return famiglia;
    }

    public String getAnnoRilascio() {
        return annoRilascio;
    }

    /*
        SETTERS
     */
    public void setFamiglia(String famiglia) {
        this.famiglia = famiglia;
    }

    public void setAnnoRilascio(String annoRilascio) {
        this.annoRilascio = annoRilascio;
    }
}
