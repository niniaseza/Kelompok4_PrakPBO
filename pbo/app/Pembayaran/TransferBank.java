package app.pembayaran;

/**
 * Metode pembayaran menggunakan transfer bank.
 * Kelas ini mensimulasikan proses pembayaran melalui bank
 * dengan nomor rekening dan nama bank yang diberikan.
 */
public class TransferBank implements Pembayaran {
    private String namaBank;
    private long noRek;

    /**
     * Membuat objek TransferBank dengan informasi bank dan nomor rekening.
     */
    public TransferBank(String namaBank, long noRek) {
        this.namaBank = namaBank;
        this.noRek = noRek;
    }

    /**
     * Mensimulasikan proses pembayaran melalui transfer bank.
     */
    @Override
    public boolean prosesPembayaran(double amount) {
        System.out.println("Proses transfer ke " + namaBank + " no rek " + noRek + " sebesar " + amount);
        System.out.println("Transfer berhasil");
        return true;
    }

    /**
     * Mengembalikan nama metode pembayaran lengkap dengan bank dan nomor rekening.
     */
    @Override
    public String getNamaMetode() {
        return "Transfer Bank (" + namaBank + ") - Rek: " + noRek;
    }
}
