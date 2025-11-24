package app;

import app.models.*;
import app.pembayaran.*;
import app.exceptions.*;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Entry point utama untuk aplikasi Digitala Store versi GUI (Graphical User
 * Interface).
 * Menggunakan library Java Swing untuk menampilkan antarmuka visual yang
 * modern,
 * mencakup form login, dashboard admin, dan katalog belanja pelanggan.
 *
 * @author Rajabi, Teuku Al, Azira, M Rayyanta
 * @version 1.0
 */
public class MainAppGUI extends JFrame {

    // --- PALET WARNA (HIJAU TOSCA) ---
    public static final Color PRIMARY_COLOR = new Color(0, 160, 120);
    public static final Color PRIMARY_DARK = new Color(0, 130, 100);
    public static final Color LOGIN_BTN = new Color(30, 35, 45);

    public static final Color BG_COLOR = new Color(248, 249, 250);
    public static final Color WHITE = Color.WHITE;
    public static final Color DANGER_COLOR = new Color(220, 53, 69);
    public static final Color GREY_BTN = new Color(108, 117, 125);
    public static final Color INFO_BTN = new Color(23, 162, 184);

    // --- FONTS ---
    public static Font FONT_INTER_BOLD;
    public static Font FONT_INTER_PLAIN;
    public static Font FONT_INTER_TITLE;

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private User currentUser;
    private Keranjang currentKeranjang;

    /**
     * Konstruktor utama untuk menginisialisasi frame, memuat data, dan mengatur
     * layout panel.
     */
    public MainAppGUI() {
        // Setup Font
        FONT_INTER_BOLD = new Font("Inter", Font.BOLD, 14);
        FONT_INTER_PLAIN = new Font("Inter", Font.PLAIN, 14);
        FONT_INTER_TITLE = new Font("Inter", Font.BOLD, 28);

        if (!FONT_INTER_BOLD.getFamily().equals("Inter")) {
            FONT_INTER_BOLD = new Font("SansSerif", Font.BOLD, 14);
            FONT_INTER_PLAIN = new Font("SansSerif", Font.PLAIN, 14);
            FONT_INTER_TITLE = new Font("SansSerif", Font.BOLD, 28);
        }

        // LOAD DATA
        DataStoreSaver.loadAkun();
        DataStoreSaver.loadProduk();
        DataStoreSaver.loadTransaksi();

        // SETUP FRAME
        setTitle("Digitala Store - Sistem Belanja");
        setSize(1280, 850);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // SETUP PANELS
        mainPanel.add(new LoginPanel(), "LOGIN");
        mainPanel.add(new RegisterPanel(), "REGISTER");
        mainPanel.add(new AdminPanel(), "ADMIN");
        mainPanel.add(new CustomerPanel(), "CUSTOMER");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");

        // Auto Save saat Close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Keluar aplikasi dan simpan data?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DataStoreSaver.saveAkun();
                    DataStoreSaver.saveProduk();
                    DataStoreSaver.saveTransaksi();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Method utama untuk menjalankan aplikasi GUI dalam Event Dispatch Thread.
     *
     * @param args Argumen baris perintah.
     */
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(() -> new MainAppGUI().setVisible(true));
    }

    // ==========================================
    // CUSTOM COMPONENT: HEADER LENGKUNG
    // ==========================================
    class CurvedHeaderPanel extends JPanel {
        private String title;

        public CurvedHeaderPanel(String title) {
            this.title = title;
            setOpaque(false);
            setPreferredSize(new Dimension(1280, 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            g2.setColor(PRIMARY_COLOR);
            GeneralPath path = new GeneralPath();
            path.moveTo(0, 0);
            path.lineTo(0, h - 80);
            path.curveTo(w * 0.2, h, w * 0.6, h - 100, w, h - 20);
            path.lineTo(w, 0);
            path.closePath();
            g2.fill(path);
        }
    }

    // ==========================================
    // PANEL LOGIN
    // ==========================================
    class LoginPanel extends JPanel {
        JTextField userField = createRoundedTextField();
        JPasswordField passField = createRoundedPasswordField();

        public LoginPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JPanel headerContainer = new CurvedHeaderPanel("Login");
            headerContainer.setLayout(new GridBagLayout());

            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 0, 10, 0);
            gbc.gridx = 0;
            gbc.gridy = 0;

            JLabel lblTitle = new JLabel("Login");
            lblTitle.setFont(new Font("Inter", Font.BOLD, 40));
            lblTitle.setForeground(Color.BLACK);
            formPanel.add(lblTitle, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(30, 0, 5, 0);
            formPanel.add(createLabel("Username"), gbc);
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 15, 0);
            formPanel.add(userField, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 5, 0);
            formPanel.add(createLabel("Password"), gbc);
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 30, 0);
            formPanel.add(passField, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(10, 0, 10, 0);
            JButton btnLogin = createDarkButton("Login Masuk");
            formPanel.add(btnLogin, gbc);

            gbc.gridy++;
            JLabel lblReg = new JLabel("Belum punya akun? Daftar disini");
            lblReg.setFont(FONT_INTER_PLAIN);
            lblReg.setForeground(PRIMARY_COLOR);
            lblReg.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lblReg.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    userField.setText("");
                    passField.setText("");
                    cardLayout.show(mainPanel, "REGISTER");
                }
            });
            formPanel.add(lblReg, gbc);

            add(headerContainer, BorderLayout.NORTH);
            add(formPanel, BorderLayout.CENTER);

            btnLogin.addActionListener(e -> processLogin());
        }

        private JLabel createLabel(String text) {
            JLabel l = new JLabel(text);
            l.setFont(new Font("Inter", Font.PLAIN, 16));
            l.setForeground(Color.GRAY);
            l.setPreferredSize(new Dimension(350, 20));
            return l;
        }

        private void processLogin() {
            if (userField.getText().trim().isEmpty() || new String(passField.getPassword()).trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid: Username atau Password tidak boleh kosong!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                User user = DataStore.findUserByUsername(userField.getText());
                if (user != null && user.checkPassword(new String(passField.getPassword()))) {
                    currentUser = user;
                    userField.setText("");
                    passField.setText("");
                    if (user instanceof Admin) {
                        ((AdminPanel) mainPanel.getComponent(2)).refreshData();
                        cardLayout.show(mainPanel, "ADMIN");
                    } else {
                        currentKeranjang = new Keranjang();
                        ((CustomerPanel) mainPanel.getComponent(3)).refreshData();
                        cardLayout.show(mainPanel, "CUSTOMER");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid: Username/Password Salah!", "Gagal",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    // ==========================================
    // PANEL REGISTER
    // ==========================================
    class RegisterPanel extends JPanel {
        JTextField txtNama = createRoundedTextField();
        JTextField txtUser = createRoundedTextField();
        JPasswordField txtPass = createRoundedPasswordField();
        JTextField txtAlamat = createRoundedTextField();
        JTextField txtHp = createRoundedTextField();

        public RegisterPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JPanel header = new CurvedHeaderPanel("Sign Up");
            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 0, 5, 0);
            gbc.gridx = 0;
            gbc.gridy = 0;

            JLabel title = new JLabel("Sign Up");
            title.setFont(new Font("Inter", Font.BOLD, 40));
            title.setForeground(Color.BLACK);
            form.add(title, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(20, 0, 2, 0);
            form.add(createLabel("Nama Lengkap"), gbc);
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 10, 0);
            form.add(txtNama, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 2, 0);
            form.add(createLabel("Username"), gbc);
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 10, 0);
            form.add(txtUser, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 2, 0);
            form.add(createLabel("Password"), gbc);
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 10, 0);
            form.add(txtPass, gbc);

            JPanel subForm = new JPanel(new GridLayout(1, 2, 10, 0));
            subForm.setBackground(Color.WHITE);
            subForm.setPreferredSize(new Dimension(350, 60));
            JPanel p1 = new JPanel(new BorderLayout());
            p1.setBackground(Color.WHITE);
            p1.add(createLabel("Alamat"), BorderLayout.NORTH);
            p1.add(txtAlamat, BorderLayout.CENTER);
            JPanel p2 = new JPanel(new BorderLayout());
            p2.setBackground(Color.WHITE);
            p2.add(createLabel("No Telepon"), BorderLayout.NORTH);
            p2.add(txtHp, BorderLayout.CENTER);
            subForm.add(p1);
            subForm.add(p2);

            gbc.gridy++;
            form.add(subForm, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(20, 0, 10, 0);
            JButton btnSign = createDarkButton("Sign Up");
            form.add(btnSign, gbc);

            gbc.gridy++;
            JLabel lblBack = new JLabel("Kembali ke Login");
            lblBack.setFont(FONT_INTER_PLAIN);
            lblBack.setForeground(Color.GRAY);
            lblBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lblBack.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    clearForm();
                    cardLayout.show(mainPanel, "LOGIN");
                }
            });
            form.add(lblBack, gbc);

            add(header, BorderLayout.NORTH);
            add(new JScrollPane(form), BorderLayout.CENTER);

            btnSign.addActionListener(e -> processRegister());
        }

        private JLabel createLabel(String text) {
            JLabel l = new JLabel(text);
            l.setFont(new Font("Inter", Font.PLAIN, 14));
            l.setForeground(Color.GRAY);
            l.setPreferredSize(new Dimension(350, 20));
            return l;
        }

        private void clearForm() {
            txtNama.setText("");
            txtUser.setText("");
            txtPass.setText("");
            txtAlamat.setText("");
            txtHp.setText("");
        }

        private void processRegister() {
            if (txtNama.getText().trim().isEmpty() || txtUser.getText().trim().isEmpty() ||
                    new String(txtPass.getPassword()).trim().isEmpty() || txtAlamat.getText().trim().isEmpty() ||
                    txtHp.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid: Semua data harus diisi!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!txtHp.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Invalid: No Telepon harus angka!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (DataStore.findUserByUsername(txtUser.getText()) != null) {
                JOptionPane.showMessageDialog(this, "Invalid: Username sudah terpakai!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newId = "U" + (DataStore.getListAkun().size() + 100);
            Pelanggan p = new Pelanggan(newId, txtUser.getText(), new String(txtPass.getPassword()),
                    txtNama.getText(), txtAlamat.getText(), txtHp.getText());
            DataStore.getListAkun().add(p);
            DataStoreSaver.saveAkun();

            JOptionPane.showMessageDialog(this, "Registrasi Berhasil! Silakan Login.");
            clearForm();
            cardLayout.show(mainPanel, "LOGIN");
        }
    }

    // ==========================================
    // PANEL ADMIN
    // ==========================================
    class AdminPanel extends JPanel {
        DefaultTableModel modelProduk, modelTrans, modelUser;
        JTable tableProduk, tableTrans, tableUser;
        JTextField idF, namaF, hbF, hjF, stokF, descF;
        String selectedId = null;

        public AdminPanel() {
            setLayout(new BorderLayout());
            setBackground(BG_COLOR);

            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(PRIMARY_COLOR);
            header.setPreferredSize(new Dimension(1280, 60));
            header.setBorder(new EmptyBorder(0, 20, 0, 20));

            JLabel title = new JLabel("Dashboard Admin");
            title.setFont(new Font("Inter", Font.BOLD, 20));
            title.setForeground(WHITE);
            header.add(title, BorderLayout.WEST);

            JButton btnLogout = new JButton("LOGOUT");
            btnLogout.setFont(new Font("Inter", Font.BOLD, 12));
            btnLogout.setForeground(WHITE);
            btnLogout.setBackground(new Color(0, 0, 0, 0));
            btnLogout.setBorderPainted(false);
            btnLogout.setFocusPainted(false);
            btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnLogout.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btnLogout.setOpaque(true);
                    btnLogout.setBackground(new Color(255, 255, 255, 50));
                }

                public void mouseExited(MouseEvent e) {
                    btnLogout.setOpaque(false);
                    btnLogout.setBackground(new Color(0, 0, 0, 0));
                }
            });
            btnLogout.addActionListener(e -> {
                currentUser = null;
                cardLayout.show(mainPanel, "LOGIN");
            });
            header.add(btnLogout, BorderLayout.EAST);

            add(header, BorderLayout.NORTH);

            JTabbedPane tabs = new JTabbedPane();
            tabs.setFont(FONT_INTER_BOLD);
            tabs.setBackground(WHITE);

            // TAB 1: Produk
            JPanel pProduk = new JPanel(new BorderLayout(20, 20));
            pProduk.setBorder(new EmptyBorder(20, 20, 20, 20));
            pProduk.setBackground(BG_COLOR);

            JPanel formBox = new JPanel(new GridBagLayout());
            formBox.setBackground(WHITE);
            formBox.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            idF = createSimpleField();
            namaF = createSimpleField();
            hbF = createSimpleField();
            hjF = createSimpleField();
            stokF = createSimpleField();
            descF = createSimpleField();

            addInput(formBox, gbc, 0, 0, "ID Produk", idF);
            addInput(formBox, gbc, 1, 0, "Nama Produk", namaF);
            addInput(formBox, gbc, 0, 1, "Harga Beli", hbF);
            addInput(formBox, gbc, 1, 1, "Harga Jual", hjF);
            addInput(formBox, gbc, 0, 2, "Stok", stokF);
            addInput(formBox, gbc, 1, 2, "Deskripsi", descF);

            JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnBox.setBackground(WHITE);

            JButton btnClear = createAdminButton("Reset", GREY_BTN);
            JButton btnDel = createAdminButton("Hapus", DANGER_COLOR);
            JButton btnAdd = createAdminButton("Simpan", PRIMARY_COLOR);

            btnBox.add(btnClear);
            btnBox.add(btnDel);
            btnBox.add(btnAdd);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 4;
            formBox.add(btnBox, gbc);

            modelProduk = new DefaultTableModel(new String[] { "ID", "Nama", "Beli", "Jual", "Stok", "Deskripsi" }, 0);
            tableProduk = createCleanTable(modelProduk);
            pProduk.add(formBox, BorderLayout.NORTH);
            pProduk.add(new JScrollPane(tableProduk), BorderLayout.CENTER);

            // TAB 2: Transaksi
            JPanel pTrans = new JPanel(new BorderLayout(20, 20));
            pTrans.setBackground(BG_COLOR);
            pTrans.setBorder(new EmptyBorder(20, 20, 20, 20));
            modelTrans = new DefaultTableModel(new String[] { "ID", "Pelanggan", "Total", "Status", "Metode" }, 0);
            tableTrans = createCleanTable(modelTrans);

            JPanel transBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            transBtnPanel.setBackground(BG_COLOR);
            JButton btnTerima = createAdminButton("Terima Transaksi", PRIMARY_COLOR);
            JButton btnHistory = createAdminButton("Lihat History Penjualan", INFO_BTN);
            transBtnPanel.add(btnHistory);
            transBtnPanel.add(btnTerima);

            pTrans.add(new JScrollPane(tableTrans), BorderLayout.CENTER);
            pTrans.add(transBtnPanel, BorderLayout.SOUTH);

            // TAB 3: User
            JPanel pUser = new JPanel(new BorderLayout(20, 20));
            pUser.setBackground(BG_COLOR);
            pUser.setBorder(new EmptyBorder(20, 20, 20, 20));
            modelUser = new DefaultTableModel(new String[] { "Role", "ID", "Username", "Nama" }, 0);
            tableUser = createCleanTable(modelUser);
            JPanel userBtnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnAddAdmin = createAdminButton("Tambah Admin", PRIMARY_COLOR);
            JButton btnDelUser = createAdminButton("Hapus User", DANGER_COLOR);
            userBtnBox.add(btnAddAdmin);
            userBtnBox.add(btnDelUser);
            pUser.add(new JScrollPane(tableUser), BorderLayout.CENTER);
            pUser.add(userBtnBox, BorderLayout.SOUTH);

            tabs.addTab("Manajemen Produk", pProduk);
            tabs.addTab("Daftar Transaksi", pTrans);
            tabs.addTab("Kelola Pengguna", pUser);
            add(tabs, BorderLayout.CENTER);

            // --- LOGIC ADMIN ---
            tableProduk.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int r = tableProduk.getSelectedRow();
                    if (r != -1) {
                        selectedId = (String) modelProduk.getValueAt(r, 0);
                        ProdukElektronik p = DataStore.findProdukById(selectedId);
                        if (p != null) {
                            idF.setText(p.getIdProduk());
                            idF.setEditable(false); // LOCK ID SAAT EDIT
                            namaF.setText(p.getNama());
                            hbF.setText("" + (long) p.getHargaBeli());
                            hjF.setText("" + (long) p.getHargaJual());
                            stokF.setText("" + p.getStok());
                            descF.setText(p.getDeskripsi());
                        }
                    }
                }
            });

            // ADD / EDIT Logic
            btnAdd.addActionListener(e -> {
                try {
                    String id = idF.getText().trim();
                    if (id.isEmpty()) {
                        id = "P" + System.currentTimeMillis() % 1000;
                    }

                    if (selectedId == null) { // Mode Tambah
                        if (DataStore.findProdukById(id) != null) {
                            JOptionPane.showMessageDialog(this, "Invalid: ID Barang sudah ada!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    String nm = namaF.getText();
                    double hb = Double.parseDouble(hbF.getText());
                    double hj = Double.parseDouble(hjF.getText());
                    int st = Integer.parseInt(stokF.getText());
                    String ds = descF.getText();

                    if (hb <= 0 || hj <= 0 || st < 0)
                        throw new Exception("Angka tidak valid");

                    if (selectedId == null) {
                        ((Admin) currentUser).tambahProduk(new ProdukGeneric(id, nm, hb, hj, st, ds));
                        JOptionPane.showMessageDialog(this, "Berhasil Menambah Barang!");
                    } else {
                        ProdukElektronik p = DataStore.findProdukById(selectedId);
                        if (p != null) {
                            p.setNama(nm);
                            p.setHargaBeli(hb);
                            p.setHargaJual(hj);
                            p.setStok(st);
                            p.setDeskripsi(ds);
                            JOptionPane.showMessageDialog(this, "Berhasil Mengedit Barang!");
                        }
                    }
                    DataStoreSaver.saveProduk();
                    refreshData();
                    clear();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Input Error: " + ex.getMessage());
                }
            });

            btnDel.addActionListener(e -> {
                if (selectedId != null) {
                    if (JOptionPane.showConfirmDialog(this, "Hapus barang terpilih?", "Konfirmasi",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        ((Admin) currentUser).hapusProduk(selectedId);
                        DataStoreSaver.saveProduk();
                        refreshData();
                        clear();
                    }
                } else
                    JOptionPane.showMessageDialog(this, "Pilih barang dulu!");
            });

            btnClear.addActionListener(e -> clear());

            btnTerima.addActionListener(e -> {
                int r = tableTrans.getSelectedRow();
                if (r != -1 && ((Admin) currentUser).terimaTransaksi((String) modelTrans.getValueAt(r, 0))) {
                    DataStoreSaver.saveTransaksi();
                    DataStoreSaver.saveProduk();
                    refreshData();
                    JOptionPane.showMessageDialog(this, "Sukses!");
                }
            });

            btnHistory.addActionListener(e -> showHistoryDialog());

            btnAddAdmin.addActionListener(e -> {
                JTextField u = new JTextField();
                JTextField p = new JTextField();
                JTextField n = new JTextField();
                if (JOptionPane.showConfirmDialog(this, new Object[] { "User:", u, "Pass:", p, "Nama:", n },
                        "Admin Baru", JOptionPane.OK_CANCEL_OPTION) == 0) {
                    DataStore.getListAkun()
                            .add(new Admin("A" + System.currentTimeMillis(), u.getText(), p.getText(), n.getText()));
                    DataStoreSaver.saveAkun();
                    refreshData();
                }
            });

            btnDelUser.addActionListener(e -> {
                int r = tableUser.getSelectedRow();
                if (r != -1) {
                    User t = DataStore.findUserByUsername((String) modelUser.getValueAt(r, 2));
                    if (t instanceof Pelanggan) {
                        DataStore.getListAkun().remove(t);
                        DataStoreSaver.saveAkun();
                        refreshData();
                    } else
                        JOptionPane.showMessageDialog(this, "Admin tidak bisa dihapus");
                }
            });
        }

        private void showHistoryDialog() {
            JDialog d = new JDialog(MainAppGUI.this, "History Penjualan Barang", true);
            d.setSize(600, 400);
            d.setLocationRelativeTo(this);
            d.setLayout(new BorderLayout());

            Map<String, Integer> counts = new HashMap<>();
            Map<String, Double> revenue = new HashMap<>();

            for (Transaksi t : DataStore.getListTransaksi()) {
                if (!t.getStatus().equals("PENDING")) {
                    for (Map.Entry<ProdukElektronik, Integer> entry : t.getItems().entrySet()) {
                        String nama = entry.getKey().getNama();
                        counts.put(nama, counts.getOrDefault(nama, 0) + entry.getValue());
                        revenue.put(nama,
                                revenue.getOrDefault(nama, 0.0) + (entry.getKey().getHargaJual() * entry.getValue()));
                    }
                }
            }

            DefaultTableModel m = new DefaultTableModel(new String[] { "Nama Barang", "Total Terjual", "Pendapatan" },
                    0);
            for (String nama : counts.keySet()) {
                m.addRow(new Object[] { nama, counts.get(nama), "Rp " + String.format("%.0f", revenue.get(nama)) });
            }

            JTable t = createCleanTable(m);
            d.add(new JScrollPane(t), BorderLayout.CENTER);
            JButton close = new JButton("Tutup");
            close.addActionListener(ev -> d.dispose());
            JPanel p = new JPanel();
            p.add(close);
            d.add(p, BorderLayout.SOUTH);
            d.setVisible(true);
        }

        private void addInput(JPanel p, GridBagConstraints gbc, int col, int row, String lbl, JComponent f) {
            gbc.gridx = col * 2;
            gbc.gridy = row;
            JLabel l = new JLabel(lbl);
            l.setFont(FONT_INTER_PLAIN);
            p.add(l, gbc);
            gbc.gridx = col * 2 + 1;
            p.add(f, gbc);
        }

        private void clear() {
            idF.setText("");
            idF.setEditable(true); // UNLOCK ID SAAT RESET
            namaF.setText("");
            hbF.setText("");
            hjF.setText("");
            stokF.setText("");
            descF.setText("");
            selectedId = null;
        }

        public void refreshData() {
            modelProduk.setRowCount(0);
            DataStore.getListProduk().forEach(p -> modelProduk.addRow(new Object[] { p.getIdProduk(), p.getNama(),
                    p.getHargaBeli(), p.getHargaJual(), p.getStok(), p.getDeskripsi() }));
            modelTrans.setRowCount(0);
            DataStore.getListTransaksi()
                    .forEach(t -> modelTrans.addRow(new Object[] { t.getIdTransaksi(), t.getPelanggan().getNama(),
                            t.getTotal(), t.getStatus(), t.getMetodePembayaran().getNamaMetode() }));
            modelUser.setRowCount(0);
            DataStore.getListAkun().forEach(
                    u -> modelUser.addRow(new Object[] { u.getRole(), u.getUserID(), u.getUsername(), u.getNama() }));
        }
    }

    // ==========================================
    // PANEL CUSTOMER (VALIDASI PEMBAYARAN)
    // ==========================================
    class CustomerPanel extends JPanel {
        DefaultTableModel modelShop, modelCart, modelHistory; // Added modelHistory
        JTable tableShop, tableCart, tableHistory; // Added tableHistory
        JLabel lblTotal;
        JComboBox<String> cbPay;

        public CustomerPanel() {
            setLayout(new BorderLayout());
            setBackground(BG_COLOR);

            // Header
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(PRIMARY_COLOR);
            header.setPreferredSize(new Dimension(1280, 60));
            header.setBorder(new EmptyBorder(0, 20, 0, 20));
            JLabel t = new JLabel("Digitala Shop");
            t.setFont(new Font("Inter", Font.BOLD, 20));
            t.setForeground(WHITE);
            header.add(t, BorderLayout.WEST);
            JButton lo = new JButton("LOGOUT");
            lo.addActionListener(e -> {
                currentUser = null;
                cardLayout.show(mainPanel, "LOGIN");
            });
            header.add(lo, BorderLayout.EAST);
            add(header, BorderLayout.NORTH);

            JTabbedPane tabs = new JTabbedPane();
            tabs.setFont(FONT_INTER_BOLD);

            // Shop
            JPanel pShop = new JPanel(new BorderLayout(10, 10));
            modelShop = new DefaultTableModel(new String[] { "ID", "Nama", "Harga", "Stok", "Deskripsi" }, 0);
            tableShop = createCleanTable(modelShop);
            tableShop.getColumnModel().getColumn(4).setPreferredWidth(300);

            JButton btnAdd = createAdminButton("Tambah ke Keranjang", PRIMARY_COLOR);
            pShop.add(new JScrollPane(tableShop), BorderLayout.CENTER);
            pShop.add(btnAdd, BorderLayout.SOUTH);

            // Cart
            JPanel pCart = new JPanel(new BorderLayout(10, 10));
            modelCart = new DefaultTableModel(new String[] { "Item", "Qty", "Subtotal" }, 0);
            tableCart = createCleanTable(modelCart);
            JPanel pCheck = new JPanel();
            lblTotal = new JLabel("Total: 0");
            lblTotal.setFont(FONT_INTER_BOLD);
            cbPay = new JComboBox<>(new String[] { "Transfer Bank", "COD", "E-Wallet", "QRIS", "Kartu Kredit" });
            JButton btnCheck = createAdminButton("Checkout", new Color(255, 152, 0));
            pCheck.add(lblTotal);
            pCheck.add(cbPay);
            pCheck.add(btnCheck);
            pCart.add(new JScrollPane(tableCart), BorderLayout.CENTER);
            pCart.add(pCheck, BorderLayout.SOUTH);

            // History Tab (FITUR BARU)
            JPanel pHistory = new JPanel(new BorderLayout(10, 10));
            pHistory.setBackground(BG_COLOR);
            pHistory.setBorder(new EmptyBorder(20, 20, 20, 20));
            modelHistory = new DefaultTableModel(
                    new String[] { "ID Transaksi", "Tanggal", "Total", "Status", "Metode" }, 0);
            tableHistory = createCleanTable(modelHistory);
            pHistory.add(new JScrollPane(tableHistory), BorderLayout.CENTER);

            tabs.addTab("Katalog", pShop);
            tabs.addTab("Keranjang", pCart);
            tabs.addTab("Riwayat Pembelian", pHistory); // Menambahkan tab baru

            add(tabs, BorderLayout.CENTER);

            btnAdd.addActionListener(e -> {
                int r = tableShop.getSelectedRow();
                if (r != -1) {
                    ProdukElektronik p = DataStore.findProdukById((String) modelShop.getValueAt(r, 0));
                    String q = JOptionPane.showInputDialog("Jumlah (Stok: " + p.getStok() + ")");
                    if (q != null) {
                        try {
                            int qty = Integer.parseInt(q);
                            if (qty <= 0) {
                                JOptionPane.showMessageDialog(this, "Invalid: Jumlah harus lebih dari 0!");
                            } else if (qty > p.getStok()) {
                                JOptionPane.showMessageDialog(this, "Invalid: Stok tidak mencukupi!", "Warning",
                                        JOptionPane.WARNING_MESSAGE);
                            } else {
                                currentKeranjang.addItem(p, qty);
                                refreshData();
                                JOptionPane.showMessageDialog(this, "Berhasil masuk keranjang!");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Input harus angka!");
                        }
                    }
                } else
                    JOptionPane.showMessageDialog(this, "Pilih barang dulu!");
            });

            btnCheck.addActionListener(e -> {
                if (currentKeranjang.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Keranjang Kosong!");
                    return;
                }

                String[] kurirOptions = { "JNE", "J&T", "SiCepat", "AnterAja" };
                String kurir = (String) JOptionPane.showInputDialog(this, "Pilih Kurir Pengiriman:",
                        "Pilih Kurir", JOptionPane.QUESTION_MESSAGE, null, kurirOptions, kurirOptions[0]);

                if (kurir == null)
                    return;

                String method = (String) cbPay.getSelectedItem();
                Pembayaran pay = null;

                // VALIDASI PEMBAYARAN
                try {
                    if (method.equals("COD")) {
                        pay = new COD(((Pelanggan) currentUser).getAlamat(), kurir);
                    } else if (method.equals("Transfer Bank")) {
                        String bank = JOptionPane.showInputDialog("Masukkan Nama Bank:");
                        if (bank == null)
                            return;
                        String rekStr = JOptionPane.showInputDialog("Masukkan No Rekening (Angka):");
                        if (rekStr == null)
                            return;

                        if (!rekStr.matches("\\d+")) {
                            JOptionPane.showMessageDialog(this, "Invalid: No Rekening harus angka!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        pay = new TransferBank(bank, Long.parseLong(rekStr));
                    } else if (method.equals("E-Wallet")) {
                        String prov = JOptionPane.showInputDialog("Provider:");
                        if (prov == null)
                            return;
                        String hpStr = JOptionPane.showInputDialog("No HP (Angka):");
                        if (hpStr == null)
                            return;

                        if (!hpStr.matches("\\d+")) {
                            JOptionPane.showMessageDialog(this, "Invalid: No HP harus angka!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        pay = new EWallet(prov, hpStr);
                    } else if (method.equals("Kartu Kredit")) {
                        String nm = JOptionPane.showInputDialog("Nama di Kartu:");
                        String noStr = JOptionPane.showInputDialog("Nomor Kartu (Angka):");
                        if (nm == null || noStr == null)
                            return;

                        if (!noStr.matches("\\d+")) {
                            JOptionPane.showMessageDialog(this, "Invalid: Nomor Kartu harus angka!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        pay = new KartuKredit(nm, noStr);
                    } else {
                        pay = new QRIS();
                    }

                    if (pay != null && pay.prosesPembayaran(currentKeranjang.getTotalHarga())) {
                        Transaksi tr = new Transaksi("T" + System.currentTimeMillis(), (Pelanggan) currentUser,
                                currentKeranjang, pay);
                        DataStore.addTransaksi(tr);
                        ((Pelanggan) currentUser).addToHistory(tr);
                        DataStoreSaver.saveTransaksi();

                        currentKeranjang = new Keranjang();
                        refreshData();
                        JOptionPane.showMessageDialog(this, "Checkout Berhasil!\nPengiriman via: " + kurir);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Terjadi kesalahan input: " + ex.getMessage());
                }
            });
        }

        public void refreshData() {
            modelShop.setRowCount(0);
            DataStore.getListProduk().forEach(p -> modelShop.addRow(new Object[] {
                    p.getIdProduk(), p.getNama(), p.getHargaJual(), p.getStok(), p.getDeskripsi()
            }));

            modelCart.setRowCount(0);
            if (currentKeranjang != null) {
                currentKeranjang.getItems()
                        .forEach((k, v) -> modelCart.addRow(new Object[] { k.getNama(), v, k.getHargaJual() * v }));
                lblTotal.setText("Total: " + currentKeranjang.getTotalHarga());
            }

            // UPDATE TABEL HISTORY
            modelHistory.setRowCount(0);
            if (currentUser != null) {
                DataStore.getListTransaksi().stream()
                        .filter(t -> t.getPelanggan().getUsername().equals(currentUser.getUsername()))
                        .forEach(t -> modelHistory.addRow(new Object[] {
                                t.getIdTransaksi(),
                                t.getTanggal(),
                                "Rp " + String.format("%.0f", t.getTotal()),
                                t.getStatus(),
                                t.getMetodePembayaran().getNamaMetode()
                        }));
            }
        }
    }

    // ==========================================
    // UI HELPER METHODS
    // ==========================================

    private JTextField createRoundedTextField() {
        JTextField tf = new JTextField(20);
        tf.setFont(FONT_INTER_PLAIN);
        tf.setPreferredSize(new Dimension(350, 40));
        tf.setBorder(new RoundedBorder(PRIMARY_COLOR, 20));
        return tf;
    }

    private JPasswordField createRoundedPasswordField() {
        JPasswordField pf = new JPasswordField(20);
        pf.setFont(FONT_INTER_PLAIN);
        pf.setPreferredSize(new Dimension(350, 40));
        pf.setBorder(new RoundedBorder(PRIMARY_COLOR, 20));
        return pf;
    }

    private JTextField createSimpleField() {
        JTextField t = new JTextField(15);
        t.setFont(FONT_INTER_PLAIN);
        return t;
    }

    private JButton createDarkButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(LOGIN_BTN);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Inter", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(350, 45));
        return btn;
    }

    private JButton createAdminButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(WHITE);
        b.setFont(new Font("Inter", Font.BOLD, 12));
        b.setFocusPainted(false);
        return b;
    }

    private JTable createCleanTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_INTER_PLAIN);
        table.setRowHeight(30);
        table.getTableHeader().setFont(FONT_INTER_BOLD);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(WHITE);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        return table;
    }

    private static class RoundedBorder extends AbstractBorder {
        private Color color;
        private int radius;

        RoundedBorder(Color c, int r) {
            this.color = c;
            this.radius = r;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 15, 5, 15);
        }
    }
}
