package com.tsw.ecommerce.control;

import com.tsw.ecommerce.model.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private OrdineDAO ordineDAO = new OrdineDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if ("/prodotti".equals(path)) {
                req.setAttribute("prodotti", prodottoDAO.findAllIncludeInactive());
                req.getRequestDispatcher("/WEB-INF/jsp/admin/prodotti.jsp").forward(req, resp);
            } else if ("/prodotti/nuovo".equals(path)) {
                req.getRequestDispatcher("/WEB-INF/jsp/admin/form-prodotto.jsp").forward(req, resp);
            } else if ("/prodotti/modifica".equals(path)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("prodotto", prodottoDAO.findById(id));
                req.getRequestDispatcher("/WEB-INF/jsp/admin/form-prodotto.jsp").forward(req, resp);
            } else if ("/ordini".equals(path)) {
                String da = req.getParameter("da");
                String a = req.getParameter("a");
                String cliente = req.getParameter("cliente");
                List<Ordine> ordini;
                if (da != null && a != null && !da.isEmpty() && !a.isEmpty()) {
                    ordini = ordineDAO.findByDateRange(da, a);
                } else if (cliente != null && !cliente.isEmpty()) {
                    ordini = ordineDAO.findByCliente(cliente);
                } else {
                    ordini = ordineDAO.findAll();
                }
                for (Ordine o : ordini) {
                    o.setRighe(ordineDAO.findRigheByOrdine(o.getId()));
                }
                req.setAttribute("ordini", ordini);
                req.getRequestDispatcher("/WEB-INF/jsp/admin/ordini.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        try {
            if ("/prodotti/salva".equals(path)) {
                String idStr = req.getParameter("id");
                Prodotto p = new Prodotto();
                p.setNome(req.getParameter("nome"));
                p.setDescrizione(req.getParameter("descrizione"));
                p.setPrezzo(new BigDecimal(req.getParameter("prezzo")));
                p.setIva(new BigDecimal(req.getParameter("iva")));
                p.setQuantitaDisponibile(Integer.parseInt(req.getParameter("quantita")));
                p.setImmagine(req.getParameter("immagine"));

                if (idStr != null && !idStr.isEmpty()) {
                    p.setId(Integer.parseInt(idStr));
                    prodottoDAO.update(p);
                    req.getSession().setAttribute("messaggio", "Prodotto modificato con successo!");
                } else {
                    prodottoDAO.insert(p);
                    req.getSession().setAttribute("messaggio", "Prodotto inserito con successo!");
                }
                resp.sendRedirect(req.getContextPath() + "/admin/prodotti");
            } else if ("/prodotti/elimina".equals(path)) {
                int id = Integer.parseInt(req.getParameter("id"));
                prodottoDAO.delete(id);
                req.getSession().setAttribute("messaggio", "Prodotto eliminato con successo!");
                resp.sendRedirect(req.getContextPath() + "/admin/prodotti");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
