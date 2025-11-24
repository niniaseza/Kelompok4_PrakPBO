package app.pembayaran;

/**
 * Metode pembayaran QRIS untuk melakukan transaksi non-tunai.
 * Kelas ini mensimulasikan proses pembayaran dengan QR code
 * menggunakan merchant DigitalA.
 */
public class QRIS implements Pembayaran {
    private String merchantName;
    private String qrisId;

    /**
     * Constructor default yang menetapkan merchant dan ID QRIS
     * milik toko DigitalA.
     */
    public QRIS() {
        this.merchantName = "DIGITALA STORE";
        this.qrisId = "QRIS-DIGITALA-001";
    }

    /**
     * Mensimulasikan proses pembayaran melalui QRIS.*/
    @Override
    public boolean prosesPembayaran(double amount) {
        System.out.println("Memproses pembayaran QRIS...");
        System.out.println("Merchant: " + merchantName);
        System.out.println("QRIS ID: " + qrisId);
        System.out.println("Amount: Rp " + amount);
        System.out.println("Silakan scan QR code untuk melakukan pembayaran");
        System.out.println("Pembayaran QRIS berhasil");
        return true;
    }

    @Override
    public String getNamaMetode() {
        return "QRIS";
    }
}
