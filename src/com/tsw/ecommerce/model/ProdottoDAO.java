package com.tsw.ecommerce.model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public List<Prodotto> findAll() throws SQLException {
        String sql = "SELECT * FROM prodotto WHERE attivo = TRUE ORDER BY nome";
        List<Prodotto> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Prodotto> findAllIncludeInactive() throws SQLException {
        String sql = "SELECT * FROM prodotto ORDER BY nome";
        List<Prodotto> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Prodotto findById(int id) throws SQLException {
        String sql = "SELECT * FROM prodotto WHERE id = ?";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Prodotto> search(String query) throws SQLException {
        String sql = "SELECT * FROM prodotto WHERE attivo = TRUE AND nome LIKE ? LIMIT 10";
        List<Prodotto> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public void insert(Prodotto p) throws SQLException {
        String sql = "INSERT INTO prodotto (nome, descrizione, prezzo, iva, quantita_disponibile, immagine) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setBigDecimal(3, p.getPrezzo());
            ps.setBigDecimal(4, p.getIva());
            ps.setInt(5, p.getQuantitaDisponibile());
            ps.setString(6, p.getImmagine());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) p.setId(keys.getInt(1));
            }
        }
    }

    public void update(Prodotto p) throws SQLException {
        String sql = "UPDATE prodotto SET nome=?, descrizione=?, prezzo=?, iva=?, quantita_disponibile=?, immagine=? WHERE id=?";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setBigDecimal(3, p.getPrezzo());
            ps.setBigDecimal(4, p.getIva());
            ps.setInt(5, p.getQuantitaDisponibile());
            ps.setString(6, p.getImmagine());
            ps.setInt(7, p.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "UPDATE prodotto SET attivo = FALSE WHERE id = ?";
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Prodotto map(ResultSet rs) throws SQLException {
        Prodotto p = new Prodotto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setPrezzo(rs.getBigDecimal("prezzo"));
        p.setIva(rs.getBigDecimal("iva"));
        p.setQuantitaDisponibile(rs.getInt("quantita_disponibile"));
        p.setImmagine(rs.getString("immagine"));
        p.setAttivo(rs.getBoolean("attivo"));
        return p;
    }
}
