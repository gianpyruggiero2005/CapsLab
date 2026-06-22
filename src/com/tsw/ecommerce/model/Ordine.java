package com.tsw.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Ordine implements Serializable {
    private int id;
    private int utenteId;
    private Timestamp dataOrdine;
    private BigDecimal totale;
    private List<RigaOrdine> righe;
    private String nomeCliente;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
    public Timestamp getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Timestamp dataOrdine) { this.dataOrdine = dataOrdine; }
    public BigDecimal getTotale() { return totale; }
    public void setTotale(BigDecimal totale) { this.totale = totale; }
    public List<RigaOrdine> getRighe() { return righe; }
    public void setRighe(List<RigaOrdine> righe) { this.righe = righe; }
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
}
