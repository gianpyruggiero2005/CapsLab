package com.tsw.ecommerce.control;

import com.tsw.ecommerce.model.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/ordini")
public class OrdineServlet extends HttpServlet {
    private OrdineDAO ordineDAO = new OrdineDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }

        try {
            List<Ordine> ordini = ordineDAO.findByUtente(utente.getId());
            for (Ordine o : ordini) {
                o.setRighe(ordineDAO.findRigheByOrdine(o.getId()));
            }
            req.setAttribute("ordini", ordini);
            req.getRequestDispatcher("/WEB-INF/jsp/ordini.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
