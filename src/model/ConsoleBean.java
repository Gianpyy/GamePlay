package model;

import java.io.Serializable;

public class ConsoleBean extends ProdottoBean implements Serializable {
    /*
        ATTRIBUTI
     */
    private String famiglia;
    private String annoRilascio;

    /*
        COSTRUTTORI
     */

    public ConsoleBean() {
    }

    public ConsoleBean(String barcode, String nome, float prezzo, int sconto, String famiglia, String annoRilascio) {
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
