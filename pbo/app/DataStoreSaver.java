package app;

import java.io.*;
import java.util.*;

import app.models.*;
import app.pembayaran.*;

/**
 * Kelas DataStoreSaver menangani proses penyimpanan (save)
 * dan pemuatan (load) seluruh data aplikasi ke file eksternal,
 * termasuk akun, produk, transaksi, dan barang terjual.
 * File yang digunakan berupa file teks sederhana (txt).
 */
public class DataStoreSaver {

    private static final String FILE_AKUN = "akun.txt";
    private static final String FILE_PRODUK = "produk.txt";
    private static final String FILE_TRANSAKSI = "transaksi.txt";

    /**
     * Menyimpan seluruh akun (Admin dan Pelanggan)
     * ke dalam file akun.txt dalam format teks.
     */
    public static void saveAkun() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_AKUN))) {

            for (User u : DataStore.getListAkun()) {
                if (u instanceof Admin) {
                    Admin a = (Admin) u;
                    pw.println(
                            "ADMIN;" + a.getId() + ";" + a.getUsername() + ";" + a.getPassword() + ";" + a.getNama());
                } else if (u instanceof Pelanggan) {
                    Pelanggan p = (Pelanggan) u;
                    pw.println("PELANGGAN;" + p.getId() + ";" + p.getUsername() + ";" + p.getPassword() + ";" +
                            p.getNama() + ";" + p.getAlamat() + ";" + p.getNoTelepon());
                }
            }

        } catch (Exception e) {
            System.out.println("Error menyimpan akun: " + e.getMessage());
        }
    }

    /**Memuat ulang data akun dari akun.txt, kemudian
     * mengisi ulang daftar akun di DataStore.
     */
    public static void loadAkun() {
        File file = new File(FILE_AKUN);
        if (!file.exists())
            return;

        DataStore.getListAkun().clear(); 

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(";");
                if (d[0].equals("ADMIN")) {
                    DataStore.getListAkun().add(new Admin(d[1], d[2], d[3], d[4]));
                } else if (d[0].equals("PELANGGAN")) {
                    DataStore.getListAkun().add(new Pelanggan(d[1], d[2], d[3], d[4], d[5], d[6]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error load akun: " + e.getMessage());
        }
    }

    /**
     * Menyimpan daftar produk elektronik ke file produk.txt,
     * termasuk ID, nama, harga beli/jual, stok, dan deskripsi.
     */
    public static void saveProduk() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PRODUK))) {

            for (ProdukElektronik p : DataStore.getListProduk()) {
                pw.println(
                        p.getClass().getSimpleName() + ";" +
                                p.getIdProduk() + ";" +
                                p.getNama() + ";" +
                                p.getHargaBeli() + ";" +
                                p.getHargaJual() + ";" +
                                p.getStok() + ";" +
                                p.getDeskripsi());
            }

        } catch (Exception e) {
            System.out.println("Error menyimpan produk: " + e.getMessage());
        }
    }

    /**
     * Memuat data produk dari produk.txt dan membuat ulang
     * objek ProdukGeneric berdasarkan data tersebut.
     */
    public static void loadProduk() {
        File file = new File(FILE_PRODUK);
        if (!file.exists())
            return;

        DataStore.getListProduk().clear(); 

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(";");

                String id = d[1];
                String nama = d[2];
                double hb = Double.parseDouble(d[3]);
                double hj = Double.parseDouble(d[4]);
                int stok = Integer.parseInt(d[5]);
                String deskripsi = d.length > 6 ? d[6] : "Tidak ada deskripsi";

                ProdukGeneric produk = new ProdukGeneric(id, nama, hb, hj, stok, deskripsi);
                DataStore.getListProduk().add(produk);
            }
        } catch (Exception e) {
            System.out.println("Error load produk: " + e.getMessage());
        }
    }

    /**
     * Menyimpan data transaksi ke transaksi.txt, termasuk:
     * - ID transaksi
     * - ID pelanggan
     * - tanggal transaksi
     * - status
     * - total harga
     * - metode pembayaran
     * Serta data item transaksi (produk + jumlah).
     */
    public static void saveTransaksi() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_TRANSAKSI))) {

            for (Transaksi t : DataStore.getListTransaksi()) {
                pw.println(
                        t.getIdTransaksi() + ";" +
                                t.getPelanggan().getId() + ";" +
                                t.getTanggal().getTime() + ";" +
                                t.getStatus() + ";" +
                                t.getTotal() + ";" +
                                t.getMetodePembayaran().getNamaMetode());

                // Simpan items transaksi
                for (Map.Entry<ProdukElektronik, Integer> entry : t.getItems().entrySet()) {
                    pw.println(
                            "ITEM;" +
                                    t.getIdTransaksi() + ";" +
                                    entry.getKey().getIdProduk() + ";" +
                                    entry.getValue());
                }
            }

        } catch (Exception e) {
            System.out.println("Error menyimpan transaksi: " + e.getMessage());
        }
    }

    /* Memuat seluruh transaksi dari transaksi.txt.*/
    public static void loadTransaksi() {
        File file = new File(FILE_TRANSAKSI);
        if (!file.exists())
            return;

        DataStore.getListTransaksi().clear(); 

        try (Scanner sc = new Scanner(file)) {
            Map<String, TransaksiTemp> tempMap = new HashMap<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] d = line.split(";");

                if (d[0].equals("ITEM")) {
                    String idTransaksi = d[1];
                    String idProduk = d[2];
                    int qty = Integer.parseInt(d[3]);

                    ProdukElektronik produk = DataStore.findProdukById(idProduk);
                    if (produk != null && tempMap.containsKey(idTransaksi)) {
                        tempMap.get(idTransaksi).items.put(produk, qty);
                    }
                } else {
                    String idTransaksi = d[0];
                    String idPelanggan = d[1];
                    long timestamp = Long.parseLong(d[2]);
                    String status = d[3];
                    double total = Double.parseDouble(d[4]);
                    String namaMetode = d[5];

                    // Cari pelanggan
                    Pelanggan pelanggan = null;
                    for (User u : DataStore.getListAkun()) {
                        if (u instanceof Pelanggan && u.getUserID().equals(idPelanggan)) {
                            pelanggan = (Pelanggan) u;
                            break;
                        }
                    }

                    if (pelanggan != null) {
                        TransaksiTemp temp = new TransaksiTemp();
                        temp.idTransaksi = idTransaksi;
                        temp.pelanggan = pelanggan;
                        temp.timestamp = timestamp;
                        temp.status = status;
                        temp.total = total;
                        temp.namaMetode = namaMetode;
                        temp.items = new HashMap<>();

                        tempMap.put(idTransaksi, temp);
                    }
                }
            }

            // Buat transaksi dari data temp
            for (TransaksiTemp temp : tempMap.values()) {
                Keranjang keranjang = new Keranjang();
                for (Map.Entry<ProdukElektronik, Integer> entry : temp.items.entrySet()) {
                    keranjang.addItem(entry.getKey(), entry.getValue());
                }

                Pembayaran pembayaran = new PembayaranDummy(temp.namaMetode);

                Transaksi transaksi = new Transaksi(temp.idTransaksi, temp.pelanggan, keranjang, pembayaran);
                transaksi.setStatus(temp.status);
                transaksi.setTanggal(new Date(temp.timestamp));

                DataStore.addTransaksi(transaksi);
                temp.pelanggan.addToHistory(transaksi);
            }

        } catch (Exception e) {
            System.out.println("Error load transaksi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**Menyimpan daftar barang yang pernah terjual
     * (status transaksi tidak PENDING) ke file barang_terjual.txt
     * dalam format tabel sederhana.
     */
    public static void saveBarangTerjual() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("barang_terjual.txt"))) {

            pw.println("====================== DAFTAR BARANG TERJUAL ======================");
            pw.printf("| %-25s | %-10s |%n", "Nama Produk", "Jumlah");
            pw.println("-------------------------------------------------------------------");

            boolean ada = false;

            for (Transaksi t : DataStore.getListTransaksi()) {
                if (!t.getStatus().equals("PENDING")) {
                    for (Map.Entry<ProdukElektronik, Integer> e : t.getItems().entrySet()) {
                        pw.printf("| %-25s | %-10d |%n",
                                e.getKey().getNama(), e.getValue());
                        ada = true;
                    }
                }
            }

            pw.println("-------------------------------------------------------------------");

            if (!ada) {
                pw.println("BELUM ADA BARANG TERJUAL");
            }

        } catch (Exception e) {
            System.out.println("Error menyimpan barang terjual: " + e.getMessage());
        }
    }

    /**
     * Kelas helper untuk menampung data transaksi sementara
     * sebelum dibangun kembali menjadi objek Transaksi utuh.
     */
    private static class TransaksiTemp {
        String idTransaksi;
        Pelanggan pelanggan;
        long timestamp;
        String status;
        double total;
        String namaMetode;
        HashMap<ProdukElektronik, Integer> items;
    }
}
