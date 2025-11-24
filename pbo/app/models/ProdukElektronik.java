package app.models;

public abstract class ProdukElektronik {
    private String idProduk;
    private String nama;
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private String deskripsi;

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

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    } // ← TAMBAH INI

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void reduceStok(int qty) {
        this.stok = this.stok - qty;
    }

    public abstract void displaySpesifikasi();
}
