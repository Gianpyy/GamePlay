package model;

public class Prodotto {

    /*
        VARIABILI DI CLASSE
     */
    private String barcode, nome;
    private float prezzo;


    /*
        COSTRUTTORI
     */
    public Prodotto() {}

    public Prodotto(String barcode, String nome, float prezzo) {
        this.barcode = barcode;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    /*
        GETTERS
     */
    public String getBarcode() {
        return barcode;
    }

    public String getNome() {
        return nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    /*
        SETTERS
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
}
