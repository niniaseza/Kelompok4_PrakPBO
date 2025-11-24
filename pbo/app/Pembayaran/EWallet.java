package app.pembayaran;

/**
 * Kelas EWallet merupakan implementasi metode pembayaran
 * menggunakan dompet digital seperti Dana, OVO, atau GoPay.
 */
public class EWallet implements Pembayaran {
    private String provider;
    private String idAkun;

    /**
     * Membuat metode pembayaran e-wallet dengan provider dan ID akun tertentu.
     */
    public EWallet(String provider, String idAkun) {
        this.provider = provider;
        this.idAkun = idAkun;
    }

    /**
     * Memproses pembayaran menggunakan e-wallet.
     *
     * @param amount jumlah yang harus dibayar
     * @return true jika pembayaran berhasil
     */
    @Override
    public boolean prosesPembayaran(double amount) {
        System.out.println("Memproses pembayaran e-wallet " + provider + " id " + idAkun + " sebesar " + amount);
        System.out.println("Pembayaran e-wallet sukses.");
        return true;
    }

    /**
     * Mengembalikan nama metode pembayaran.
     *
     * @return nama metode berupa "E-Wallet (provider)"
     */
    @Override
    public String getNamaMetode() {
        return "E-Wallet (" + provider + ")";
    }
}
