package app.pembayaran;

/**
 * Kelas PembayaranDummy digunakan saat melakukan load data transaksi
 * dari file. Karena metode pembayaran asli tidak bisa direkonstruksi
 * sepenuhnya, maka dummy ini menjadi placeholder yang tetap memenuhi
 * interface Pembayaran.
 */
public class PembayaranDummy implements Pembayaran {
    /** Nama metode pembayaran yang direkonstruksi dari file. */
    private String namaMetode;

    /**
     * Membuat objek PembayaranDummy dengan nama metode tertentu.
     */
    public PembayaranDummy(String namaMetode) {
        this.namaMetode = namaMetode;
    }

    @Override
    public boolean prosesPembayaran(double amount) {
        return true; 
    }

    @Override
    public String getNamaMetode() {
        return namaMetode;
    }
}
