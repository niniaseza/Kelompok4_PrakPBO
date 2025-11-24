package app.models;

/**
 * Kelas abstrak User merepresentasikan akun dasar dalam sistem,
 * berisi informasi umum seperti ID, username, password, dan nama.
 * Kelas lain seperti Admin dan Pelanggan akan mewarisi kelas ini.
 */
public abstract class User {
    private String userID;
    private String username;
    private String password;
    private String nama;

     /**
     * Membuat objek User dengan data dasar akun.
     *
     * @param userID    ID unik pengguna
     * @param username  nama pengguna untuk login
     * @param password  kata sandi untuk login
     * @param nama      nama lengkap pengguna
     */
    public User(String userID, String username, String password, String nama) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.nama = nama;
    }

    public String getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; } 
    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    /**
     * Mengecek kecocokan password saat proses login.
     *
     * @param pw password yang dimasukkan pengguna
     * @return true jika password cocok
     */
    public boolean checkPassword(String pw) {
        return this.password.equals(pw);
    }

    /**
     * Mengembalikan peran pengguna (ADMIN atau PELANGGAN).
     * Diimplementasikan oleh subclass.
     *
     * @return role pengguna dalam sistem
     */
    public abstract String getRole();
}
