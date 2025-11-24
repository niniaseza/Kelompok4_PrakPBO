package app.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.pembayaran.Pembayaran;

public class Transaksi {
    private String idTransaksi;
    private Pelanggan pelanggan;
    private HashMap<ProdukElektronik, Integer> items;
    private Date tanggal;
    private String status;
    private Pembayaran metodePembayaran;
    private double total;

    public Transaksi(String idTransaksi, Pelanggan pelanggan, Keranjang keranjang, Pembayaran metodePembayaran) {
        this.idTransaksi = idTransaksi;
        this.pelanggan = pelanggan;
        this.items = new HashMap<>(keranjang.getItems());
        this.tanggal = new Date();
        this.status = "PENDING";
        this.metodePembayaran = metodePembayaran;
        this.total = keranjang.getTotalHarga();
    }

    public String getIdTransaksi() { return idTransaksi; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public HashMap<ProdukElektronik, Integer> getItems() { return items; }
    public Date getTanggal() { return tanggal; }
    public String getStatus() { return status; }
    public Pembayaran getMetodePembayaran() { return metodePembayaran; }
    public double getTotal() { return total; }

    public void setStatus(String status) { this.status = status; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; } // ← TAMBAHKAN INI

    // saat transaksi diterima, kurangi stok barang
    public void reduceStock() {
        for (Map.Entry<ProdukElektronik, Integer> e : items.entrySet()) {
            ProdukElektronik p = e.getKey();
            int qty = e.getValue();
            p.reduceStok(qty);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n╔════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ ID Transaksi : %-40s║\n", idTransaksi));
        sb.append(String.format("║ Pelanggan    : %-40s║\n", pelanggan.getNama()));
        sb.append(String.format("║ Tanggal      : %-40s║\n", sdf.format(tanggal)));
        sb.append(String.format("║ Status       : %-40s║\n", status));
        sb.append(String.format("║ Pembayaran   : %-40s║\n", metodePembayaran.getNamaMetode()));
        sb.append("╠════════════════════════════════════════════════════════╣\n");
        sb.append("║ ITEMS:                                                 ║\n");
        
        for (Map.Entry<ProdukElektronik, Integer> e : items.entrySet()) {
            String itemInfo = String.format("  - %s x%d = Rp %.0f", 
                e.getKey().getNama(), 
                e.getValue(), 
                e.getKey().getHargaJual() * e.getValue());
            sb.append(String.format("║ %-54s ║\n", itemInfo));
        }
        
        sb.append("╠════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ TOTAL        : Rp %-37.0f║\n", total));
        sb.append("╚════════════════════════════════════════════════════════╝");
        
        return sb.toString();
    }

    public void printInvoice() {
        System.out.println("\n====== INVOICE ======");
        System.out.println("ID: " + idTransaksi);
        System.out.println("Nama: " + pelanggan.getNama());
        System.out.println("Tanggal: " + tanggal);
        System.out.println("Items: ");
        for (Map.Entry<ProdukElektronik, Integer> e : items.entrySet()) {
            System.out.println("- " + e.getKey().getNama() + " x " + e.getValue() + " = " + (e.getKey().getHargaJual() * e.getValue()));
        }
        System.out.println("Total: " + total);
        System.out.println("Status: " + status);
        System.out.println("=====================");
    }
}
