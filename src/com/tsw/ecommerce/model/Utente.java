package com.tsw.ecommerce.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Utente implements Serializable {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String passwordHash;
    private String ruolo;
    private Timestamp dataRegistrazione;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    public Timestamp getDataRegistrazione() { return dataRegistrazione; }
    public void setDataRegistrazione(Timestamp dataRegistrazione) { this.dataRegistrazione = dataRegistrazione; }
    public boolean isAdmin() { return "admin".equals(ruolo); }
}
