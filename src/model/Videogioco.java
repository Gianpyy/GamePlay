package model;

import java.util.Date;
import java.util.List;

public class Videogioco extends Prodotto{
    /*
        VARIABILI DI CLASSE
     */
    private String piattaforma;
    private String descrizione;
    private String condizioni;
    private String numeroGiocatori;
    private Date dataRilascio;
    private List<String> categorie;
    private List<String> pegi;
    private int etaPegi;

    /*
        COSTRUTTORI
     */
    public Videogioco() {
        super();
    }

    public Videogioco(String barcode, String nome, float prezzo, String piattaforma, String descrizione, String condizioni, String numeroGiocatori, Date dataRilascio,
                      List<String> categorie, List<String> pegi, int etaPegi) {
        super(barcode, nome, prezzo);
        this.piattaforma = piattaforma;
        this.descrizione = descrizione;
        this.condizioni = condizioni;
        this.numeroGiocatori = numeroGiocatori;
        this.dataRilascio = dataRilascio;
        this.categorie = categorie;
        this.pegi = pegi;
        this.etaPegi = etaPegi;
    }

    /*
        METODI
     */
    public void addCategoria(String categoria) {
        categorie.add(categoria);
    }

    public void addContenuto(String contenuto) {
        pegi.add(contenuto);
    }

    /*
        GETTERS
     */
    public String getPiattaforma() {
        return piattaforma;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getCondizioni() {
        return condizioni;
    }

    public String getNumeroGiocatori() {
        return numeroGiocatori;
    }

    public Date getDataRilascio() {
        return dataRilascio;
    }

    public List<String> getCategorie() {
        return categorie;
    }

    public List<String> getPegi() {
        return pegi;
    }

    public int getEtaPegi() {
        return etaPegi;
    }

    /*
        SETTERS
     */
    public void setPiattaforma(String piattaforma) {
        this.piattaforma = piattaforma;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setCondizioni(String condizioni) {
        this.condizioni = condizioni;
    }

    public void setNumeroGiocatori(String numeroGiocatori) {
        this.numeroGiocatori = numeroGiocatori;
    }

    public void setDataRilascio(Date dataRilascio) {
        this.dataRilascio = dataRilascio;
    }

    public void setCategorie(List<String> categorie) {
        this.categorie = categorie;
    }

    public void setPegi(List<String> pegi) {
        this.pegi = pegi;
    }

    public void setEtaPegi(int etaPegi) {
        this.etaPegi = etaPegi;
    }
}
