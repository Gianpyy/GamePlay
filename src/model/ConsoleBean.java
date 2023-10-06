package model;

import java.io.Serializable;

public class ConsoleBean extends ProdottoBean implements Serializable {
    /*
        ATTRIBUTI
     */
    private String famiglia;
    private int annoRilascio;

    /*
        COSTRUTTORI
     */

    public ConsoleBean() {
    }

    public ConsoleBean(String barcode, String nome, float prezzo, int sconto, String famiglia, int annoRilascio) {
        super(barcode, nome, prezzo, sconto, "console");
        this.famiglia = famiglia;
        this.annoRilascio = annoRilascio;
    }

    /*
        GETTERS
     */
    public String getFamiglia() {
        return famiglia;
    }

    public int getAnnoRilascio() {
        return annoRilascio;
    }

    /*
        SETTERS
     */
    public void setFamiglia(String famiglia) {
        this.famiglia = famiglia;
    }

    public void setAnnoRilascio(int annoRilascio) {
        this.annoRilascio = annoRilascio;
    }
}
