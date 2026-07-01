package com.tsw.ecommerce.model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    public void creaOrdine(Ordine ordine, List<RigaOrdine> righe) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            con.setAutoCommit(false);

            String sqlOrdine = "INSERT INTO ordine (utente_id, totale) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sqlOrdine, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, ordine.getUtenteId());
                ps.setBigDecimal(2, ordine.getTotale());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) ordine.setId(keys.getInt(1));
                }
            }

            String sqlRiga = "INSERT INTO riga_ordine (ordine_id, prodotto_id, nome_prodotto, prezzo_unitario, iva, quantita) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlUpdateQta = "UPDATE prodotto SET quantita_disponibile = quantita_disponibile - ? WHERE id = ?";

            try (PreparedStatement psRiga = con.prepareStatement(sqlRiga);
                 PreparedStatement psUpdate = con.prepareStatement(sqlUpdateQta)) {
                
                for (RigaOrdine r : righe) {
                    // Inserisci riga ordine
                    psRiga.setInt(1, ordine.getId());
                    psRiga.setInt(2, r.getProdottoId());
                    psRiga.setString(3, r.getNomeProdotto());
                    psRiga.setBigDecimal(4, r.getPrezzoUnitario());
                    psRiga.setBigDecimal(5, r.getIva());
                    psRiga.setInt(6, r.getQuantita());
                    psRiga.addBatch();
                    
                    // Decrementa quantità disponibile del prodotto
                    psUpdate.setInt(1, r.getQuantita());
                    psUpdate.setInt(2, r.getProdottoId());
                    psUpdate.addBatch();
                }
                
                psRiga.executeBatch();
                psUpdate.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) { con.setAutoCommit(true); con.close(); }
        }
    }

    public List<Ordine> findByUtente(int utenteId) throws SQLException {
        String sql = "SELECT * FROM ordine WHERE utente_id = ? ORDER BY data_ordine DESC";
        List<Ordine> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapOrdine(rs));
            }
        }
        return list;
    }

    public List<Ordine> findAll() throws SQLException {
        String sql = "SELECT o.*, CONCAT(u.nome, ' ', u.cognome) AS nome_cliente FROM ordine o JOIN utente u ON o.utente_id = u.id ORDER BY o.data_ordine DESC";
        List<Ordine> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ordine o = mapOrdine(rs);
                o.setNomeCliente(rs.getString("nome_cliente"));
                list.add(o);
            }
        }
        return list;
    }

    public List<Ordine> findByDateRange(String da, String a) throws SQLException {
        String sql = "SELECT o.*, CONCAT(u.nome, ' ', u.cognome) AS nome_cliente FROM ordine o JOIN utente u ON o.utente_id = u.id WHERE DATE(o.data_ordine) BETWEEN ? AND ? ORDER BY o.data_ordine DESC";
        List<Ordine> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, da);
            ps.setString(2, a);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = mapOrdine(rs);
                    o.setNomeCliente(rs.getString("nome_cliente"));
                    list.add(o);
                }
            }
        }
        return list;
    }

    public List<Ordine> findByCliente(String nomeCliente) throws SQLException {
        String sql = "SELECT o.*, CONCAT(u.nome, ' ', u.cognome) AS nome_cliente FROM ordine o JOIN utente u ON o.utente_id = u.id WHERE CONCAT(u.nome, ' ', u.cognome) LIKE ? ORDER BY o.data_ordine DESC";
        List<Ordine> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nomeCliente + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = mapOrdine(rs);
                    o.setNomeCliente(rs.getString("nome_cliente"));
                    list.add(o);
                }
            }
        }
        return list;
    }

    public List<RigaOrdine> findRigheByOrdine(int ordineId) throws SQLException {
        String sql = "SELECT * FROM riga_ordine WHERE ordine_id = ?";
        List<RigaOrdine> list = new ArrayList<>();
        try (Connection con = ConnectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RigaOrdine r = new RigaOrdine();
                    r.setId(rs.getInt("id"));
                    r.setOrdineId(rs.getInt("ordine_id"));
                    r.setProdottoId(rs.getObject("prodotto_id") != null ? rs.getInt("prodotto_id") : null);
                    r.setNomeProdotto(rs.getString("nome_prodotto"));
                    r.setPrezzoUnitario(rs.getBigDecimal("prezzo_unitario"));
                    r.setIva(rs.getBigDecimal("iva"));
                    r.setQuantita(rs.getInt("quantita"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    private Ordine mapOrdine(ResultSet rs) throws SQLException {
        Ordine o = new Ordine();
        o.setId(rs.getInt("id"));
        o.setUtenteId(rs.getInt("utente_id"));
        o.setDataOrdine(rs.getTimestamp("data_ordine"));
        o.setTotale(rs.getBigDecimal("totale"));
        return o;
    }
}
