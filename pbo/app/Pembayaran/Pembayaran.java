package app.pembayaran;

/**
 * Interface Pembayaran digunakan sebagai standar untuk
 * berbagai metode pembayaran yang dapat dipilih oleh pelanggan.
 * Setiap metode harus dapat memproses pembayaran dan mengembalikan
 * nama metode yang digunakan.
 */
public interface Pembayaran {
    /**
     * Memproses pembayaran sejumlah nilai tertentu.
     */
    boolean prosesPembayaran(double amount);
    String getNamaMetode();
}
