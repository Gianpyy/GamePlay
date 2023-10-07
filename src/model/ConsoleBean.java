package model;

import java.io.Serializable;

public class ConsoleBean extends ProdottoBean implements Serializable {
    /*
        ATTRIBUTI
     */
    private String famiglia;
    private int annoRilascio;
    private String edizione;

    /*
        COSTRUTTORI
     */

    public ConsoleBean() {
    }

    public ConsoleBean(String barcode, String nome, float prezzo, int sconto, String famiglia, int annoRilascio, String edizione) {
        super(barcode, nome, prezzo, sconto, "console");
        this.famiglia = famiglia;
        this.annoRilascio = annoRilascio;
        this.edizione = edizione;
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

    public String getEdizione() {
        return edizione;
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

    public void setEdizione(String edizione) {
        this.edizione = edizione;
    }
}
