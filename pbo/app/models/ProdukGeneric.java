package app.models;

/**
 * Kelas ProdukGeneric merupakan implementasi konkret dari ProdukElektronik.
 * Digunakan untuk produk umum yang tidak memiliki spesifikasi khusus.
 */
public class ProdukGeneric extends ProdukElektronik {
    /**
     * Membuat objek produk generik dengan atribut dasar produk elektronik.
     */
    public ProdukGeneric(String id, String nama, double hargaBeli, double hargaJual, int stok, String deskripsi) {
        super(id, nama, hargaBeli, hargaJual, stok, deskripsi);
    }

    /**
     * Menampilkan detail spesifikasi produk secara sederhana di console.
     */

    @Override
    public void displaySpesifikasi() {
        System.out.println("\n=== DETAIL PRODUK ===");
        System.out.println("ID       : " + getIdProduk());
        System.out.println("Nama     : " + getNama());
        System.out.println("Deskripsi: " + getDeskripsi());
        System.out.println("Harga    : Rp " + String.format("%.0f", getHargaJual()));
        System.out.println("Stok     : " + getStok());
        System.out.println("=====================");
    }
}
