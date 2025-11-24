package app;

import java.util.ArrayList;
import java.util.List;

import app.models.*;

/**
 * DataStore berfungsi sebagai penyimpanan data utama aplikasi DigitalA.
 * Kelas ini menampung daftar akun, produk, dan transaksi yang digunakan
 * selama program berjalan. Data default (admin, user, dan produk) juga
 * diinisialisasi di sini.
 */
public class DataStore {
    /** Menyimpan daftar seluruh akun (Admin & Pelanggan). */
    private static List<User> listAkun = new ArrayList<>();
    /** Menyimpan daftar seluruh produk yang tersedia. */
    private static List<ProdukElektronik> listProduk = new ArrayList<>();
    /** Menyimpan riwayat transaksi dalam sistem. */
    private static List<Transaksi> listTransaksi = new ArrayList<>();

    static {
        // default admin & user
        Admin admin1 = new Admin("A001", "rayyanta", "rayyanta123", "Rayyanta");
        Admin admin2 = new Admin("A002", "abi", "abi123", "Abi");
        Admin admin3 = new Admin("A003", "al", "al123", "Al");
        Admin admin4 = new Admin("A004", "nia", "nia123", "Nia");

        listAkun.add(admin1);
        listAkun.add(admin2);
        listAkun.add(admin3);
        listAkun.add(admin4);

        Pelanggan user1 = new Pelanggan("U001", "mira", "4567", "Mira", "Rumah", "7890");
        Pelanggan user2 = new Pelanggan("U002", "aldi", "1234", "Aldi", "Kantor", "1234");
        Pelanggan user3 = new Pelanggan("U003", "Gemini", "5678", "Gemi", "Rumah", "3456");

        listAkun.add(user1);
        listAkun.add(user2);
        listAkun.add(user3);

        // === PRODUK ELEKTRONIK ===
        listProduk.add(new ProdukGeneric("P001", "Lenovo ThinkPad X1", 11000000, 14500000, 5, "Laptop bisnis premium dengan Intel i7, RAM 16GB, SSD 512GB"));
        listProduk.add(new ProdukGeneric("P002", "Acer Aspire 5", 5000000, 6800000, 8, "Laptop affordable dengan Intel i5, RAM 8GB, SSD 512GB"));
        listProduk.add(new ProdukGeneric("P003", "ASUS ROG Strix G15", 17000000, 21000000, 4, "Gaming laptop dengan Ryzen 7, RAM 16GB, SSD 1TB"));
        listProduk.add(new ProdukGeneric("P004", "HP Pavilion 14", 7200000, 9200000, 6, "Laptop multimedia Intel i5, RAM 8GB, SSD 512GB"));
        listProduk.add(new ProdukGeneric("P005", "MacBook Air M1", 11000000, 13500000, 7, "Laptop premium Apple M1, RAM 8GB, SSD 256GB"));
        listProduk.add(new ProdukGeneric("P006", "Dell XPS 13", 14000000, 17000000, 3, "Ultrabook premium Intel i7, RAM 16GB, SSD 512GB"));

        listProduk.add(new ProdukGeneric("P007", "iPhone 12", 7500000, 9000000, 5, "Smartphone layar 6.1 inch, kamera 12MP, baterai 2815 mAh"));
        listProduk.add(new ProdukGeneric("P008", "iPhone 14 Pro", 12500000, 15500000, 3, "Smartphone premium layar 6.1 inch, kamera 48MP, baterai 3200 mAh"));
        listProduk.add(new ProdukGeneric("P009", "Samsung Galaxy S21", 7000000, 8500000, 6, "Smartphone layar 6.2 inch, kamera 64MP, baterai 4000 mAh"));
        listProduk.add(new ProdukGeneric("P010", "Xiaomi Redmi Note 12", 1800000, 2400000, 12, "Smartphone budget layar 6.7 inch, kamera 50MP, baterai 5000 mAh"));
        listProduk.add(new ProdukGeneric("P011", "Oppo Reno 8", 3500000, 4500000, 10, "Smartphone mid-range layar 6.4 inch, kamera 64MP, baterai 4500 mAh"));
        listProduk.add(new ProdukGeneric("P012", "Vivo V27 Pro", 4200000, 5200000, 9, "Smartphone layar 6.78 inch, kamera 50MP, baterai 4600 mAh"));

        listProduk.add(new ProdukGeneric("P013", "Xiaomi Mi Band 7", 350000, 550000, 12, "Smartwatch layar 1.62 inch, Heart Rate & Sleep Tracking, 180 mAh"));
        listProduk.add(new ProdukGeneric("P014", "Ezviz C6N 360", 250000, 350000, 10, "CCTV 1080p dengan night vision dan WiFi"));
        listProduk.add(new ProdukGeneric("P015", "Sony WF-1000XM4", 1800000, 2300000, 7, "TWS Bluetooth 5.2, battery 8 jam, noise cancelling"));
    }

    /** Mengembalikan seluruh akun yang terdaftar. */
    public static List<User> getListAkun() { return listAkun; }
    /** Mengembalikan daftar produk yang tersedia. */
    public static List<ProdukElektronik> getListProduk() { return listProduk; }
    /** Mengembalikan daftar transaksi yang pernah dibuat. */
    public static List<Transaksi> getListTransaksi() { return listTransaksi; }

    public static User findUserByUsername(String username) {
        return listAkun.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    public static ProdukElektronik findProdukById(String id) {
        return listProduk.stream().filter(p -> p.getIdProduk().equals(id)).findFirst().orElse(null);
    }

    /** Menambahkan transaksi baru ke dalam daftar transaksi. */
    public static void addTransaksi(Transaksi t) { listTransaksi.add(t); }
}
