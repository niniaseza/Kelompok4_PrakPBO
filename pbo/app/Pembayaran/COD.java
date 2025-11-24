package app.pembayaran;

/**
 * Kelas COD merupakan implementasi metode pembayaran Cash On Delivery,
 * dimana pembayaran dilakukan saat barang diterima pelanggan.
 */
public class COD implements Pembayaran {
    private String alamatPengiriman;
    private String kurir;

    /**
     * Membuat metode pembayaran COD dengan alamat dan kurir tertentu.
     */
    public COD(String alamatPengiriman, String kurir) {
        this.alamatPengiriman = alamatPengiriman;
        this.kurir = kurir;
    }

    /**
     * Memproses pembayaran COD.
     * Pembeli akan membayar saat barang sampai ke alamat.
     */
    @Override
    public boolean prosesPembayaran(double amount) {
        System.out.println("Memproses pesanan COD...");
        System.out.println("Alamat pengiriman: " + alamatPengiriman);
        System.out.println("Kurir: " + kurir);
        System.out.println("Total yang harus dibayar saat barang diterima: " + amount);
        System.out.println("Pesanan COD berhasil dibuat");
        return true;
    }

    /**
     * Mengembalikan nama metode pembayaran.
     *
     * @return nama metode: COD + kurir
     */
    @Override
    public String getNamaMetode() {
        return "COD (" + kurir + ")";
    }
}
