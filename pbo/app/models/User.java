package app.models;

public abstract class User {
    private String userID;
    private String username;
    private String password;
    private String nama;

    public User(String userID, String username, String password, String nama) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.nama = nama;
    }

    public String getUserID() { return userID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; } // untuk demo CLI sederhana
    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public boolean checkPassword(String pw) {
        return this.password.equals(pw);
    }

    public abstract String getRole();
}
.
