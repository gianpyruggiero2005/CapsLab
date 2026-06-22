package com.tsw.ecommerce.model;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

public class ConnectionPool {
    private static DataSource ds;

    static {
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/ecommerce");
        } catch (NamingException e) {
            throw new RuntimeException("Errore inizializzazione DataSource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
