package app.models;

/**
 * Kelas abstrak yang merepresentasikan produk elektronik di dalam sistem.
 * Menyimpan informasi dasar seperti ID, nama, harga, stok, dan deskripsi.
 * Subclass wajib mengimplementasikan metode displaySpesifikasi().
 */
public abstract class ProdukElektronik {
    private String idProduk;
    private String nama;
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private String deskripsi;

    /**
     * Konstruktor untuk membuat produk elektronik baru.
     */
    public ProdukElektronik(String idProduk, String nama, double hargaBeli, double hargaJual, int stok,
            String deskripsi) {
        this.idProduk = idProduk;
        this.nama = nama;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.deskripsi = deskripsi;
    }

    // Getters
    public String getIdProduk() {
        return idProduk;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public int getStok() {
        return stok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    // Setters
    public void setNama(String nama) {
        this.nama = nama;
    }

    /** Mengubah harga beli produk. */
    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    } 

    /** Mengubah harga jual produk. */
    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

     /** Mengubah jumlah stok produk. */
    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    /**
     * Mengurangi stok produk berdasarkan jumlah yang terjual.
     */
    public void reduceStok(int qty) {
        this.stok = this.stok - qty;
    }

    /**
     * Menampilkan spesifikasi detail produk (harus diimplementasikan oleh subclass).
     */
    public abstract void displaySpesifikasi();
}
