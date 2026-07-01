package com.tsw.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Prodotto implements Serializable {
    private int id;
    private String nome;
    private String descrizione;
    private BigDecimal prezzo;
    private BigDecimal iva;
    private int quantitaDisponibile;
    private String immagine;
    private boolean attivo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public BigDecimal getPrezzo() { return prezzo; }
    public void setPrezzo(BigDecimal prezzo) { this.prezzo = prezzo; }
    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }
    public int getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(int quantitaDisponibile) { this.quantitaDisponibile = quantitaDisponibile; }
    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
    public boolean isAttivo() { return attivo; }
    public void setAttivo(boolean attivo) { this.attivo = attivo; }

    public BigDecimal getPrezzoConIva() {
        return prezzo.add(prezzo.multiply(iva).divide(BigDecimal.valueOf(100))).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
