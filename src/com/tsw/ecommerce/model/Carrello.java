package com.tsw.ecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrello implements Serializable {
    private Map<Integer, ItemCarrello> items = new LinkedHashMap<>();

    public void aggiungi(Prodotto prodotto, int quantita) {
        ItemCarrello item = items.get(prodotto.getId());
        if (item != null) {
            item.setQuantita(item.getQuantita() + quantita);
        } else {
            items.put(prodotto.getId(), new ItemCarrello(prodotto, quantita));
        }
    }

    public void aggiorna(int prodottoId, int quantita) {
        ItemCarrello item = items.get(prodottoId);
        if (item != null) {
            if (quantita <= 0) items.remove(prodottoId);
            else item.setQuantita(quantita);
        }
    }

    public void rimuovi(int prodottoId) { items.remove(prodottoId); }
    public void svuota() { items.clear(); }
    public Map<Integer, ItemCarrello> getItems() { return items; }
    public boolean isEmpty() { return items.isEmpty(); }
    public int getNumeroArticoli() { return items.values().stream().mapToInt(ItemCarrello::getQuantita).sum(); }

    public BigDecimal getTotale() {
        return items.values().stream()
                .map(i -> i.getProdotto().getPrezzo().multiply(BigDecimal.valueOf(i.getQuantita())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static class ItemCarrello implements Serializable {
        private Prodotto prodotto;
        private int quantita;

        public ItemCarrello(Prodotto prodotto, int quantita) {
            this.prodotto = prodotto;
            this.quantita = quantita;
        }

        public Prodotto getProdotto() { return prodotto; }
        public int getQuantita() { return quantita; }
        public void setQuantita(int quantita) { this.quantita = quantita; }
    }
}
