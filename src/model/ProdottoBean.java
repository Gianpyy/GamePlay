package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProdottoBean implements Serializable {

    /*
        ATTRIBUTI
     */
    private String barcode;
    private String nome;
    private float prezzo;
    private String tipo;

    private List<PhotoBean> photos;

    /*
        COSTRUTTORI
     */
    public ProdottoBean() {
        this.barcode = null;
        photos = new ArrayList<>();
    }

    public ProdottoBean(String barcode, String nome, float prezzo, String tipo) {
        this.barcode = barcode;
        this.nome = nome;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.photos = new ArrayList<>();
    }

    public ProdottoBean(String barcode, String nome, float prezzo, String tipo, List<PhotoBean> photos) {
        this.barcode = barcode;
        this.nome = nome;
        this.prezzo = prezzo;
        this.tipo = tipo;
        this.photos = photos;
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

    public String getTipo() {
        return tipo;
    }

    public List<PhotoBean> getPhotos() {
        return photos;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPhotos(List<PhotoBean> photos) {
        this.photos = photos;
    }

    public void addImage(PhotoBean img) {
        photos.add(img);
    }
}
