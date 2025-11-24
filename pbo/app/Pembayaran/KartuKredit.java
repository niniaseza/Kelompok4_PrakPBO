package app.pembayaran;

/**
 * Kelas KartuKredit merupakan implementasi metode pembayaran
 * menggunakan kartu kredit. Pembayaran hanya disimulasikan.
 */
public class KartuKredit implements Pembayaran {
    private String namaPemilik;
    private String noKartu;

    /**
     * Membuat metode pembayaran kartu kredit.
     */
    public KartuKredit(String namaPemilik, String noKartu) {
        this.namaPemilik = namaPemilik;
        this.noKartu = noKartu;
    }

    /**
     * Mensimulasikan proses pembayaran dengan kartu kredit.
     */
    @Override
    public boolean prosesPembayaran(double amount) {
        System.out.println("Memproses pembayaran kartu kredit " + noKartu + " sebesar " + amount);
        System.out.println("Pembayaran kartu sukses");
        return true;
    }

    /**
     * Mengembalikan nama metode pembayaran.
     *
     * @return string "Kartu Kredit"
     */
    @Override
    public String getNamaMetode() {
        return "Kartu Kredit";
    }
}

