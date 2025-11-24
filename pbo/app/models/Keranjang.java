package app.models;

import java.util.HashMap;
import java.util.Map;

public class Keranjang {
    // Produk -> qty
    private HashMap<ProdukElektronik, Integer> items = new HashMap<>();

    public void addItem(ProdukElektronik p, int qty) {
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    public void removeItem(ProdukElektronik p) {
        items.remove(p);
    }

    public HashMap<ProdukElektronik, Integer> getItems() {
        return items;
    }

    public double getTotalHarga() {
        double total = 0.0;
        for (Map.Entry<ProdukElektronik, Integer> e : items.entrySet()) {
            total += e.getKey().getHargaJual() * e.getValue();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
