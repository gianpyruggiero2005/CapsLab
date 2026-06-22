package com.tsw.ecommerce.control;

import com.tsw.ecommerce.model.*;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UtenteDAO utenteDAO = new UtenteDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/login".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        } else if ("/registrazione".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/jsp/registrazione.jsp").forward(req, resp);
        } else if ("/logout".equals(path)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/prodotti");
        } else if ("/check-email".equals(path)) {
            String email = req.getParameter("email");
            resp.setContentType("application/json");
            try {
                boolean exists = utenteDAO.existsByEmail(email);
                resp.getWriter().write(gson.toJson(new EmailCheckResult(exists)));
            } catch (Exception e) {
                resp.setStatus(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if ("/login".equals(path)) {
                doLogin(req, resp);
            } else if ("/registrazione".equals(path)) {
                doRegistrazione(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Utente u = utenteDAO.findByEmail(email);
        String hashedPassword = utenteDAO.toHash(password);
        if (u != null && u.getPasswordHash().equals(hashedPassword)) {
            HttpSession session = req.getSession();
            session.setAttribute("utente", u);
            if (u.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/prodotti");
            } else {
                resp.sendRedirect(req.getContextPath() + "/prodotti");
            }
        } else {
            req.setAttribute("errore", "Email o password non validi");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }

    private void doRegistrazione(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (utenteDAO.existsByEmail(email)) {
            req.setAttribute("errore", "Email già registrata");
            req.getRequestDispatcher("/WEB-INF/jsp/registrazione.jsp").forward(req, resp);
            return;
        }

        Utente u = new Utente();
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPasswordHash(utenteDAO.toHash(password));
        u.setRuolo("cliente");
        utenteDAO.insert(u);

        req.getSession().setAttribute("utente", u);
        req.getSession().setAttribute("messaggio", "Registrazione completata con successo!");
        resp.sendRedirect(req.getContextPath() + "/prodotti");
    }

    private static class EmailCheckResult {
        boolean exists;
        EmailCheckResult(boolean exists) { this.exists = exists; }
    }
}
