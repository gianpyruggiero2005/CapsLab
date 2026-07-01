package com.tsw.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RigaOrdine implements Serializable {
    private int id;
    private int ordineId;
    private Integer prodottoId;
    private String nomeProdotto;
    private BigDecimal prezzoUnitario;
    private BigDecimal iva;
    private int quantita;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrdineId() { return ordineId; }
    public void setOrdineId(int ordineId) { this.ordineId = ordineId; }
    public Integer getProdottoId() { return prodottoId; }
    public void setProdottoId(Integer prodottoId) { this.prodottoId = prodottoId; }
    public String getNomeProdotto() { return nomeProdotto; }
    public void setNomeProdotto(String nomeProdotto) { this.nomeProdotto = nomeProdotto; }
    public BigDecimal getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(BigDecimal prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }
    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }
    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public BigDecimal getSubtotale() {
        BigDecimal prezzoConIva = prezzoUnitario.add(prezzoUnitario.multiply(iva).divide(BigDecimal.valueOf(100)));
        return prezzoConIva.multiply(BigDecimal.valueOf(quantita)).setScale(2, java.math.RoundingMode.HALF_UP);
    }
}
