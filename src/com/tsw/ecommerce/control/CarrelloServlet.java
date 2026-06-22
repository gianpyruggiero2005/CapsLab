package com.tsw.ecommerce.control;

import com.tsw.ecommerce.model.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrello/*")
public class CarrelloServlet extends HttpServlet {
    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private OrdineDAO ordineDAO = new OrdineDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/carrello.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        HttpSession session = req.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) { carrello = new Carrello(); session.setAttribute("carrello", carrello); }

        try {
            if ("/aggiungi".equals(path)) {
                int prodottoId = Integer.parseInt(req.getParameter("prodottoId"));
                int qta = Integer.parseInt(req.getParameter("quantita") != null ? req.getParameter("quantita") : "1");
                Prodotto p = prodottoDAO.findById(prodottoId);
                if (p != null) {
                    carrello.aggiungi(p, qta);
                    session.setAttribute("messaggio", "Prodotto aggiunto al carrello!");
                }
                resp.sendRedirect(req.getContextPath() + "/prodotti");
            } else if ("/aggiorna".equals(path)) {
                int prodottoId = Integer.parseInt(req.getParameter("prodottoId"));
                int qta = Integer.parseInt(req.getParameter("quantita"));
                carrello.aggiorna(prodottoId, qta);
                resp.sendRedirect(req.getContextPath() + "/carrello");
            } else if ("/rimuovi".equals(path)) {
                int prodottoId = Integer.parseInt(req.getParameter("prodottoId"));
                carrello.rimuovi(prodottoId);
                resp.sendRedirect(req.getContextPath() + "/carrello");
            } else if ("/checkout".equals(path)) {
                Utente utente = (Utente) session.getAttribute("utente");
                if (utente == null) { resp.sendRedirect(req.getContextPath() + "/auth/login"); return; }
                if (carrello.isEmpty()) { resp.sendRedirect(req.getContextPath() + "/carrello"); return; }

                Ordine ordine = new Ordine();
                ordine.setUtenteId(utente.getId());
                ordine.setTotale(carrello.getTotale());

                List<RigaOrdine> righe = new ArrayList<>();
                for (Carrello.ItemCarrello item : carrello.getItems().values()) {
                    RigaOrdine r = new RigaOrdine();
                    r.setProdottoId(item.getProdotto().getId());
                    r.setNomeProdotto(item.getProdotto().getNome());
                    r.setPrezzoUnitario(item.getProdotto().getPrezzo());
                    r.setIva(item.getProdotto().getIva());
                    r.setQuantita(item.getQuantita());
                    righe.add(r);
                }

                ordineDAO.creaOrdine(ordine, righe);
                carrello.svuota();
                session.setAttribute("messaggio", "Ordine effettuato con successo!");
                resp.sendRedirect(req.getContextPath() + "/ordini");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
