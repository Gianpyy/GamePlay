package model;

import java.io.InputStream;
import java.io.Serializable;

public class PhotoBean implements Serializable {

    //Attributi
    private String prodottoAssociato;
    private String nomeImmagine;
    private InputStream img;

    //Costruttori
    public PhotoBean() {/* Costruttore di default vuoto e senza parametri */}

    public PhotoBean(String prodottoAssociato, String nomeImmagine, InputStream img) {
        this.prodottoAssociato = prodottoAssociato;
        this.nomeImmagine = nomeImmagine;
        this.img = img;
    }

    //Getter
    public String getProdottoAssociato() {
        return prodottoAssociato;
    }

    public String getNomeImmagine() {
        return nomeImmagine;
    }

    public InputStream getImg() {
        return img;
    }

    //Setters
    public void setProdottoAssociato(String prodottoAssociato) {
        this.prodottoAssociato = prodottoAssociato;
    }

    public void setNomeImmagine(String nomeImmagine) {
        this.nomeImmagine = nomeImmagine;
    }

    public void setImg(InputStream img) {
        this.img = img;
    }
}
