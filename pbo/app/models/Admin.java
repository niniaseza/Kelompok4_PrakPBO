package app.models;

import java.util.List;
import app.models.ProdukElektronik;
import app.models.Transaksi;
import app.DataStore;

/**
 * Kelas Admin mewakili pengguna dengan hak akses tinggi.
 * Admin dapat mengelola produk dan transaksi dalam sistem.
 * Menggunakan konsep inheritance dari User.
 */

public class Admin extends User {
/**
     * Membuat objek Admin baru.
     */
    public Admin(String userID, String username, String password, String nama) {
        super(userID, username, password, nama);
    }

    public String getId() {
    return super.getUserID();
}

    @Override
    public String getRole() { return "ADMIN"; }

     /** Menambah produk ke sistem. */
    public void tambahProduk(ProdukElektronik p) {
        DataStore.getListProduk().add(p);
    }

    /**
     * Menghapus produk berdasarkan ID.
     *
     * @return true jika berhasil dihapus
     */
    public boolean hapusProduk(String idProduk) {
        return DataStore.getListProduk().removeIf(p -> p.getIdProduk().equals(idProduk));
    }

    public ProdukElektronik cariProdukById(String id) {
        return DataStore.getListProduk().stream().filter(p -> p.getIdProduk().equals(id)).findFirst().orElse(null);
    }

    public List<Transaksi> lihatTransaksi() {
        return DataStore.getListTransaksi();
    }

    public boolean terimaTransaksi(String idTransaksi) {
        for (Transaksi t : DataStore.getListTransaksi()) {
            if (t.getIdTransaksi().equals(idTransaksi) && t.getStatus().equals("PENDING")) {
                t.setStatus("DITERIMA");
                // kurangi stok setiap item
                t.reduceStock();
                return true;
            }
        }
        return false;
    }
}
