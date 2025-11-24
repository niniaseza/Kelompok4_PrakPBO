package app.pembayaran;

public class EWallet implements Pembayaran {
    private String provider;
    private String idAkun;

    public EWallet(String provider, String idAkun) {
        this.provider = provider;
        this.idAkun = idAkun;
    }

    @Override
    public boolean prosesPembayaran(double amount) {
        System.out.println("Memproses pembayaran e-wallet " + provider + " id " + idAkun + " sebesar " + amount);
        System.out.println("Pembayaran e-wallet sukses.");
        return true;
    }

    @Override
    public String getNamaMetode() {
        return "E-Wallet (" + provider + ")";
    }
}
