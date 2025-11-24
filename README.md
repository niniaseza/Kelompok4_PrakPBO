# Digitala (Online Tech Store)
Anggota Kelompok 4:
1. M.Rayyanta Adha Barus (2308107010053)
2. Teuku Al Jumanatul Ali (2408107010016)
3. Muhammad Riskan Rajabi (2408107010110)
4. Azira Kania (24080107010025)
   
DIGITALA adalah aplikasi berbasis Java yang dirancang untuk membantu pengelolaan toko elektronik secara lebih efisien. Sistem ini menyediakan fitur lengkap mulai dari pengelolaan produk, pencatatan transaksi, manajemen pengguna (admin & pelanggan), hingga berbagai metode pembayaran digital. DIGITALA dibuat sebagai proyek kolaborasi untuk menerapkan konsep Object-Oriented Programming (OOP) secara nyata dan terstruktur.

---
##  Fitur

### 1. Admin
* **Manajemen Produk:** Tambah, Edit, Hapus, dan Lihat daftar produk elektronik.
* **Manajemen Transaksi:** Melihat transaksi status 'PENDING' dan melakukan konfirmasi (Terima Transaksi).
* **Laporan:** Melihat riwayat barang yang terjual.
* **Manajemen User:** Menambah Admin baru dan menghapus User.

### 2. Pelanggan
* **Katalog:** Melihat daftar produk beserta spesifikasi dan stok.
* **Keranjang Belanja:** Menambah barang ke keranjang dan melihat total harga.
* **Checkout & Pembayaran:** Melakukan pemesanan dengan berbagai metode pembayaran (Transfer Bank, COD, E-Wallet, Kartu Kredit, QRIS).
* **Riwayat:** Melihat riwayat pembelian status pesanan.

### 3. Sistem
* **Persistensi Data:** Data akun, produk, dan transaksi disimpan secara permanen dalam file `.txt` (Auto-save & Load).
* **GUI & CLI:** Tersedia dalam dua mode antarmuka: Command Line Interface dan Graphical User Interface (Swing).

### Menjalankan Kode
```bash
javac app/*.java app/models/*.java app/pembayaran/*.java app/exceptions/*.java
java app.MainApp (CLI)
java app.MainAppGUI (GUI)

