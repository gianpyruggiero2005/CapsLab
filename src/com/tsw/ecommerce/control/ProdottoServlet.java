package com.tsw.ecommerce.control;

import com.tsw.ecommerce.model.*;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/prodotti/*")
public class ProdottoServlet extends HttpServlet {
    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if (path == null || "/".equals(path)) {
                List<Prodotto> prodotti = prodottoDAO.findAll();
                req.setAttribute("prodotti", prodotti);
                req.getRequestDispatcher("/WEB-INF/jsp/catalogo.jsp").forward(req, resp);
            } else if ("/dettaglio".equals(path)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Prodotto p = prodottoDAO.findById(id);
                if (p == null) { resp.sendError(404); return; }
                req.setAttribute("prodotto", p);
                req.getRequestDispatcher("/WEB-INF/jsp/dettaglio-prodotto.jsp").forward(req, resp);
            } else if ("/search".equals(path)) {
                String q = req.getParameter("q");
                List<Prodotto> risultati = prodottoDAO.search(q != null ? q : "");
                resp.setContentType("application/json");
                resp.getWriter().write(gson.toJson(risultati));
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
