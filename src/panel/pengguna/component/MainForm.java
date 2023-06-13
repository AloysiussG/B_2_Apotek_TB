/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package panel.pengguna.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import control.ObatControl;
import control.TransaksiControl;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import panel.pengguna.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Obat;
import model.Pengguna;
import model.Role;
import model.Staff;
import model.Transaksi;
import model.User;
import swing.ColorPallete;
import table.ObatTable;

/**
 *
 * @author AG SETO GALIH D
 */
public class MainForm extends javax.swing.JPanel {

    private Pengguna penggunaLogin;

    private static final ColorPallete cp = new ColorPallete();
    private ItemEvent event;

    private Obat obatSelected;
    private List<Obat> obatSearched;
    private CardLayout cardLayout;
    private ObatControl oc;
    private int jumlahBeli;
    private double subtotal;

    private String searchInput = "";

    private Pengguna pengguna;

    private TransaksiControl tc;

    public void setEvent(ItemEvent event) {
        this.event = event;
    }

    /** Creates new form MainForm */
    public MainForm(Pengguna pLogin) {
        tc = new TransaksiControl();

        String namaPenggunaLogin;

        if (pLogin != null) {
            this.penggunaLogin = pLogin;
            namaPenggunaLogin = pLogin.getNama();
        } else {
            namaPenggunaLogin = "PenggunaTheGod";
        }

        this.oc = new ObatControl();
        initComponents();
        showItem("");
        cardLayout = (CardLayout) cardLayoutPanel.getLayout();

        lblLogo.setIcon(new FlatSVGIcon("img/logo/logo-green.svg", 0.04f));
        lblLogo.setIconTextGap(12);
        lblLogo.setFont(new Font("sansserif", 1, 18));
        lblLogo.setForeground(cp.getColor(1));
        lblLogo.setText("Tumbuh Bersama");

        btnLogout.setText("");
        btnLogout.setIcon(new FlatSVGIcon("img/icon/logout-green.svg", 0.4f));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblProfile.setText("");
        lblUsername.setText(namaPenggunaLogin);
        lblUsername.setForeground(cp.getColor(0));

        lblProfile.setIcon(new FlatSVGIcon("img/icon/account.svg", 0.9f));

        btnBack.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                showItem("");
                cardLayout.show(cardLayoutPanel, "view");
            }
        });

        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (jumlahBeli < obatSelected.getKuantitas()) {
                    jumlahBeli += 1;
                    System.out.println(jumlahBeli);
                    inputJumlah.setText(String.valueOf(jumlahBeli));
                    subtotal = obatSelected.getHarga() * jumlahBeli;
                    lblSubtotal.setText(String.valueOf(subtotal));
                }
            }
        });

        btnMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (jumlahBeli > 1) {
                    jumlahBeli -= 1;
                    System.out.println(jumlahBeli);
                    inputJumlah.setText(String.valueOf(jumlahBeli));
                    subtotal = obatSelected.getHarga() * jumlahBeli;
                    lblSubtotal.setText(String.valueOf(subtotal));
                }
            }
        });

        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Date dateToday = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String stringDateToday = formatter.format(dateToday);

                Role r = new Role(6, 5000000.0, "Kasir");
                User u = new User(-1, "Pembelian", "pembelian");
                Staff s = new Staff(-1, "Self-Service", "2023-06-04", "777-7777", "Online", r, u);

                System.out.println("Bayar dulu ");

                Transaksi t = new Transaksi(jumlahBeli, stringDateToday, s, obatSelected, penggunaLogin);
                tc.insertDataTransaksi(t);

                obatSelected.setKuantitas(obatSelected.getKuantitas() - jumlahBeli);
                oc.updateDataObat(obatSelected.getIdObat(), obatSelected);

                JOptionPane.showMessageDialog(null, "Berhasil membeli obat!");

                //pindah view
                showItem("");
                cardLayout.show(cardLayoutPanel, "view");
            }
        });

        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchInput = "";
                searchInput = searchField.getText();

                //asynchronous process
                Thread newThread = new Thread(() -> {
                    try {
                        Thread.sleep(400);
                        searchInput = searchField.getText();
                        obatSearched = oc.showDataObatAsList(searchInput);
                        if (obatSearched != null) {
                            SwingUtilities.invokeLater(() -> {
                                itemPanel.removeAll();
                                for (Obat obat : obatSearched) {
                                    if (obat.getKuantitas() > 0) {
                                        addItem(obat);
                                    }
                                }
                                itemPanel.revalidate();
                            });
                        }
                    } catch (InterruptedException ex) {
                    }
                });
                newThread.start();

            }

            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }

        });
    }

    public void addBtnLogoutActionListener(MouseAdapter event) {
        btnLogout.addMouseListener(event);
    }

    private void initCheckout(Obat obat) {
        lblImage.setIcon(new FlatSVGIcon("img/icon/medicine-img.svg", 0.4f));
        lblNama.setText(obat.getNamaObat());
        lblHarga.setText("Rp " + String.valueOf(obat.getHarga()));
        lblStok.setText(String.valueOf(obat.getKuantitas()) + " pcs");
        lblSubtotal.setText(String.valueOf(obat.getHarga()));

        inputJumlah.setEditable(false);

        jumlahBeli = 1;
        inputJumlah.setText(String.valueOf(jumlahBeli));
    }

    public void showItem(String query) {
        //Tambahin param List hasil show query
        itemPanel.removeAll();

        ObatTable to = (ObatTable) oc.showDataObat(query);
        Obat obat;
        for (int i = 0; i < to.getRowCount(); i++) {
            obat = (Obat) to.getValueAt(i, 6);
            if (obat.getKuantitas() > 0) {
                addItem(obat);
            }
        }

        //For List Item, add Item
//        addItem(new Obat(1, 3, "Paracetamol 30 m mg 111 11 21e 23 aaaaaaaaaaaaaaaaaaaaaaaaa ", "", "", 35000));
//        addItem(new Obat(1, 3, "Pamolzzz 30 mg", "", "", 30000));
//        addItem(new Obat(1, 3, "Pamol123 30 mg", "", "", 30000));
//        addItem(new Obat(1, 3, "Pamol1 30 mg", "", "", 30000));
//        addItem(new Obat(1, 3, "Pamol3 30 mg", "", "", 30000));
//        addItem(new Obat(1, 3, "Pamol8 30 mg", "", "", 30000));
//        addItem(new Obat(1, 3, "Pamol9 30 mg", "", "", 30000));
    }

    private void addItem(Obat obat) {
        Item item = new Item();
        item.setData(obat);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    //event.itemClicked(item, obat);
                    System.out.println(obat.getNamaObat());
                    //pindah card panel, siapin data dari obat yang dipencet
                    obatSelected = obat;
                    initCheckout(obatSelected);
                    cardLayout.show(cardLayoutPanel, "checkout");
                }
            }

        });
        itemPanel.add(item);
        itemPanel.repaint();
        itemPanel.revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardLayoutPanel = new javax.swing.JPanel();
        viewItemCard = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        logo = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        searchField = new swing.component.TextFieldInput();
        lblProfile = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        btnLogout = new javax.swing.JLabel();
        body = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        itemPanel = new panel.pengguna.component.ItemPanel();
        checkoutItemCard = new javax.swing.JPanel();
        bodyCheckout = new javax.swing.JPanel();
        btnBack = new swing.component.ButtonOutLine();
        panelDisplay = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        panelDesc = new javax.swing.JPanel();
        lblNama = new javax.swing.JLabel();
        lblHarga = new javax.swing.JLabel();
        lblStok = new javax.swing.JLabel();
        panelDesc2 = new javax.swing.JPanel();
        btnMinus = new swing.component.ButtonRectangle();
        inputJumlah = new swing.component.TextFieldInput();
        btnPlus = new swing.component.ButtonRectangle();
        jLabel3 = new javax.swing.JLabel();
        lblRp = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        btnBayar = new swing.component.ButtonRound();

        setOpaque(false);

        cardLayoutPanel.setOpaque(false);
        cardLayoutPanel.setLayout(new java.awt.CardLayout());

        viewItemCard.setName(""); // NOI18N
        viewItemCard.setOpaque(false);

        header.setBackground(cp.getColor(1)
        );
        header.setOpaque(false);

        logo.setBackground(new java.awt.Color(240, 240, 240));
        logo.setForeground(new java.awt.Color(0, 0, 0));
        logo.setOpaque(false);

        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("jLabel1");

        searchField.setHint("Cari berdasarkan nama, kategori, dan lain-lain");

        javax.swing.GroupLayout logoLayout = new javax.swing.GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addGap(62, 62, 62))
        );
        logoLayout.setVerticalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        lblProfile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfile.setText("jLabel1");

        lblUsername.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsername.setText("dddddddddddddddddddddddd");

        btnLogout.setText("log");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(10, 10, 10)
                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(lblProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        body.setBackground(new java.awt.Color(255, 102, 102));
        body.setOpaque(false);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(itemPanel);

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewItemCardLayout = new javax.swing.GroupLayout(viewItemCard);
        viewItemCard.setLayout(viewItemCardLayout);
        viewItemCardLayout.setHorizontalGroup(
            viewItemCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewItemCardLayout.setVerticalGroup(
            viewItemCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewItemCardLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cardLayoutPanel.add(viewItemCard, "view");

        checkoutItemCard.setOpaque(false);

        bodyCheckout.setOpaque(false);

        btnBack.setForeground(cp.getColor(0)
        );
        btnBack.setText("Kembali");

        panelDisplay.setOpaque(false);

        lblImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        panelDesc.setOpaque(false);

        lblNama.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNama.setForeground(cp.getColor(0)
        );
        lblNama.setText("Nama Obat");
        lblNama.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblHarga.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHarga.setForeground(cp.getColor(0)
        );
        lblHarga.setText("Harga");

        lblStok.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblStok.setForeground(cp.getColor(0)
        );
        lblStok.setText("Stok");

        javax.swing.GroupLayout panelDescLayout = new javax.swing.GroupLayout(panelDesc);
        panelDesc.setLayout(panelDescLayout);
        panelDescLayout.setHorizontalGroup(
            panelDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDescLayout.createSequentialGroup()
                        .addGroup(panelDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        panelDescLayout.setVerticalGroup(
            panelDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDescLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHarga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStok)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        panelDesc2.setOpaque(false);

        btnMinus.setText("-");
        btnMinus.setPreferredSize(new java.awt.Dimension(15, 15));

        inputJumlah.setForeground(cp.getColor(0)
        );
        inputJumlah.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputJumlah.setText("0");
        inputJumlah.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N

        btnPlus.setText("+");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(cp.getColor(0)
        );
        jLabel3.setText("Subtotal:");

        lblRp.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRp.setForeground(cp.getColor(1)
        );
        lblRp.setText("Rp");

        lblSubtotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSubtotal.setForeground(cp.getColor(1)
        );
        lblSubtotal.setText("9999");

        btnBayar.setText("Bayar");

        javax.swing.GroupLayout panelDesc2Layout = new javax.swing.GroupLayout(panelDesc2);
        panelDesc2.setLayout(panelDesc2Layout);
        panelDesc2Layout.setHorizontalGroup(
            panelDesc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDesc2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDesc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDesc2Layout.createSequentialGroup()
                        .addComponent(btnMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)
                    .addGroup(panelDesc2Layout.createSequentialGroup()
                        .addComponent(lblRp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(207, Short.MAX_VALUE))
        );
        panelDesc2Layout.setVerticalGroup(
            panelDesc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDesc2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDesc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDesc2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotal)
                    .addComponent(lblRp))
                .addGap(30, 30, 30)
                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout panelDisplayLayout = new javax.swing.GroupLayout(panelDisplay);
        panelDisplay.setLayout(panelDisplayLayout);
        panelDisplayLayout.setHorizontalGroup(
            panelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDisplayLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(panelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDesc2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDisplayLayout.setVerticalGroup(
            panelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDisplayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDisplayLayout.createSequentialGroup()
                        .addComponent(panelDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelDesc2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout bodyCheckoutLayout = new javax.swing.GroupLayout(bodyCheckout);
        bodyCheckout.setLayout(bodyCheckoutLayout);
        bodyCheckoutLayout.setHorizontalGroup(
            bodyCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyCheckoutLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(bodyCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bodyCheckoutLayout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bodyCheckoutLayout.createSequentialGroup()
                        .addComponent(panelDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
        );
        bodyCheckoutLayout.setVerticalGroup(
            bodyCheckoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyCheckoutLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(panelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout checkoutItemCardLayout = new javax.swing.GroupLayout(checkoutItemCard);
        checkoutItemCard.setLayout(checkoutItemCardLayout);
        checkoutItemCardLayout.setHorizontalGroup(
            checkoutItemCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        checkoutItemCardLayout.setVerticalGroup(
            checkoutItemCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(checkoutItemCardLayout.createSequentialGroup()
                .addComponent(bodyCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        cardLayoutPanel.add(checkoutItemCard, "checkout");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardLayoutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardLayoutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JPanel bodyCheckout;
    private swing.component.ButtonOutLine btnBack;
    private swing.component.ButtonRound btnBayar;
    private javax.swing.JLabel btnLogout;
    private swing.component.ButtonRectangle btnMinus;
    private swing.component.ButtonRectangle btnPlus;
    private javax.swing.JPanel cardLayoutPanel;
    private javax.swing.JPanel checkoutItemCard;
    private javax.swing.JPanel header;
    private swing.component.TextFieldInput inputJumlah;
    private panel.pengguna.component.ItemPanel itemPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblProfile;
    private javax.swing.JLabel lblRp;
    private javax.swing.JLabel lblStok;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel logo;
    private javax.swing.JPanel panelDesc;
    private javax.swing.JPanel panelDesc2;
    private javax.swing.JPanel panelDisplay;
    private javax.swing.JScrollPane scrollPane;
    private swing.component.TextFieldInput searchField;
    private javax.swing.JPanel viewItemCard;
    // End of variables declaration//GEN-END:variables
}
