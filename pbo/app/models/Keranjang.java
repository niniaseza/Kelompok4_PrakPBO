package app.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Menyimpan daftar produk yang dipilih pelanggan sebelum checkout.
 * Keranjang berisi produk dan jumlahnya, serta menghitung total harga.
 */
public class Keranjang {
    // Produk -> qty
    private HashMap<ProdukElektronik, Integer> items = new HashMap<>();

    /**
     * Menambah jumlah produk ke dalam keranjang.
     */
    public void addItem(ProdukElektronik p, int qty) {
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    /**
     * Menghapus produk dari keranjang.
     */
    public void removeItem(ProdukElektronik p) {
        items.remove(p);
    }

    /**
     * Mengambil seluruh isi keranjang.
     */
    public HashMap<ProdukElektronik, Integer> getItems() {
        return items;
    }

    /**
     * Menghitung total harga semua produk dalam keranjang.
     */
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
