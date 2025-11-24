package app.exceptions;

/**
 * Exception khusus yang digunakan ketika pelanggan mencoba
 * membeli atau menambahkan produk ke keranjang, tetapi stok
 * produk tersebut sudah habis (0).
 *
 * Exception ini membantu proses validasi agar admin maupun
 * pelanggan mendapatkan pesan yang lebih jelas dan terarah
 * ketika terjadi masalah terkait stok.
 */
public class StokKosongException extends Exception {
    /**
     * Membuat instance baru dari StokKosongException dengan pesan
     * kesalahan khusus yang menjelaskan produk mana yang bermasalah.
     *
     * @param message pesan penjelasan mengenai stok produk yang habis
     */
    public StokKosongException(String message) {
        super(message);
    }
}
