package app.exceptions;
/**
 * Exception khusus untuk menangani ketika akun tidak ditemukan
 * saat proses login.
 */
public class AkunTidakDitemukanException extends Exception {
    public AkunTidakDitemukanException(String message) {
        super(message);
    }
}
