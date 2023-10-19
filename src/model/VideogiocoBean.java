package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VideogiocoBean extends ProdottoBean implements Serializable {
    /*
        ATTRIBUTI
     */
    private String piattaforma;
    private String descrizione;
    private String condizioni;
    private String numeroGiocatori;
    private Date dataRilascio;
    private String categoria;
    private int etaPegi;
    private String edizione;

    /*
        COSTRUTTORI
     */
    public VideogiocoBean() {
        super();
    }

    public VideogiocoBean(String barcode, String nome, float prezzo, int sconto, String piattaforma, String descrizione, String condizioni, String numeroGiocatori, Date dataRilascio,
                          String categoria,int etaPegi, String edizione) {
        super(barcode, nome, prezzo, sconto, "videogioco");
        this.piattaforma = piattaforma;
        this.descrizione = descrizione;
        this.condizioni = condizioni;
        this.numeroGiocatori = numeroGiocatori;
        this.dataRilascio = dataRilascio;
        this.categoria = categoria;
        this.etaPegi = etaPegi;
        this.edizione = edizione;
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

    public int getEtaPegi() {
        return etaPegi;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getEdizione() {
        return edizione;
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

    public void setEtaPegi(int etaPegi) {
        this.etaPegi = etaPegi;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEdizione(String edizione) {
        this.edizione = edizione;
    }
}
