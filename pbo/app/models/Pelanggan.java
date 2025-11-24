package app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Representasi pelanggan dalam sistem. 
 * Menyimpan informasi akun serta riwayat transaksi.
 */
public class Pelanggan extends User {
    private String alamat;
    private String noTelepon;
    private List<Transaksi> historyTransaksi;

    /**
     * Membuat objek pelanggan baru*/
    public Pelanggan(String userID, String username, String password, String nama, String alamat, String noTelepon) {
        super(userID, username, password, nama);
        this.alamat = alamat;
        this.noTelepon = noTelepon;
        this.historyTransaksi = new ArrayList<>();
    }

    public String getAlamat() { return alamat; }
    public String getNoTelepon() { return noTelepon; }
    public List<Transaksi> getHistoryTransaksi() { return historyTransaksi; }

    /**
     * Menambah transaksi ke history pelanggan.
     */
    public void addToHistory(Transaksi t) { this.historyTransaksi.add(t); }

    public String getId() {
    return super.getUserID();
}

    @Override
    public String getRole() { return "PELANGGAN"; }
}
