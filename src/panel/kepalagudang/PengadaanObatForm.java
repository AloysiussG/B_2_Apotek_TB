/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package panel.kepalagudang;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import control.PengadaanObatControl;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import swing.component.dashboard.NoUserCard;
import control.PenggunaControl;
import control.RoleControl;
import control.StaffControl;
import control.UserControl;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.SwingUtilities;
import model.Obat;
import model.PengadaanObat;
import model.Pengguna;
import model.Role;
import model.Staff;
import swing.ColorPallete;
import swing.component.dashboard.PengadaanObatCard;
import table.PengadaanObatTable;
import Exception.InputKosongException;
import control.ObatControl;
import exception.InputNegatifException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author AG SETO GALIH D
 */
public class PengadaanObatForm extends javax.swing.JPanel {

    private Staff staffLogin;

    private PengadaanObat pengadaanObat;
    private PengadaanObatControl poc;

    //untuk menyimpan info Pengguna yang sedang dipilih
    private Pengguna pengguna;

    private PenggunaControl pc;
    private StaffControl sc;
    private UserControl uc;
    private RoleControl rc;
    private ObatControl oc;

    private static ColorPallete cp = new ColorPallete();
    private PengadaanObatCard pengadaanObatCard;
    private NoUserCard noUserCard = new NoUserCard();
    private String namaUserGroup;
    private CardLayout cardLayout;

    private String searchInput = "";

    private List<Obat> listObat;
    int selectedid = 0;

    //Konstruktor
    public PengadaanObatForm(Staff s) {

        if (s != null) {
            this.staffLogin = s;
        } else {
            JOptionPane.showMessageDialog(null, "[WARNING] tidak ada parameter staff / null! View ini harus diakses melalui view Login!");
            System.out.println("[WARNING] tidak ada parameter staff / null!");
        }

        this.namaUserGroup = "Pengadaan Obat";

        this.poc = new PengadaanObatControl();

        this.pc = new PenggunaControl();
        this.uc = new UserControl();
        this.rc = new RoleControl();
        this.sc = new StaffControl();
        this.oc = new ObatControl();

        initComponents();

        showUserCard(noUserCard);

        setTableModel(poc.showPengadaanObat(""));

        setJudulForm(namaUserGroup);
        fieldSearch.setHint("Cari berdasarkan nama, kategori, dan lain-lain");
        btnTambah.setIcon(new FlatSVGIcon("img/icon/addnote.svg", 0.7f));
        btnTambah.setText("");

        btnBack.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancel.setBackground(new Color(240, 240, 240));
        btnCancel.setForeground(cp.getColor(0));
        btnCancel.setColorEffectRGB(220, 220, 220);

        btnBackTbh.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancelTbh.setBackground(new Color(240, 240, 240));
        btnCancelTbh.setForeground(cp.getColor(0));
        btnCancelTbh.setColorEffectRGB(220, 220, 220);

        btnBackMS.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancelMS.setBackground(new Color(240, 240, 240));
        btnCancelMS.setForeground(cp.getColor(0));
        btnCancelMS.setColorEffectRGB(220, 220, 220);

        containerCreate.setBackground(new Color(255, 255, 255));

        cardLayout = (CardLayout) cardPanel.getLayout();

        //action listeners
        initTableListener();

        //other listeners
        //action listener pada read
        //btn tambah
        addBtnTambahActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setObatToDropdownCreate();
                cardLayout.show(cardPanel, "create");
            }
        });
        //action listener pada update
        //button back action listener pada panel update
        //fungsinya untuk mengembalikan panel UpdateDataPembeli ke ReadDataPembeli lagi
        addBtnBackActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //reset data dan pindah ke panel read
                resetUpdatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        //button save action listener untuk save update di database
        addBtnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //update data ke database
//                Pengguna penggunaNew = pengguna;
//                penggunaNew.setNama(inputNamaLengkap.getText());
//                penggunaNew.setAlamat(inputAlamat.getText());
//                penggunaNew.setNoTelp(inputNoTelp.getText());
//                pc.updateDataPengguna(penggunaNew);
//                //update table model dan user card dan kembalikan ke panel read
//                resetUpdatePanel();
//                resetReadPanel();
                try {
                    inputKosongExceptionUpdate();
                    inputNegatifExceptionUpdate();

                    PengadaanObat pengadaanObatNew = pengadaanObat;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String inputTglPengadaanObat = formatter.format(tanggalTransaksiDateChooser1.getDate());
                    pengadaanObatNew.setTanggalPengadaan(inputTglPengadaanObat);
                    Obat obatPilihan = (Obat) namaObatComboBox1.getModel().getSelectedItem();
                    pengadaanObatNew.setObat(obatPilihan);
                    pengadaanObatNew.setKuantitas(Integer.parseInt(inputKuantitas1.getText()));
                    pengadaanObatNew.setSupplier(inputSupplier1.getText());

                    poc.updatePengadaanObat(pengadaanObatNew);

                    resetUpdatePanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, "Data harus diisi terlebih dahulu!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Harus Angka");
                } catch (InputNegatifException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Tidak Boleh 0 Atau Kurang Dari 0");
                }
            }
        });
        //button cancel action listener fungsinya sama seperti button back
        addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //reset data dan pindah panel
                resetUpdatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        //action listener pada tambah panel
        //
        btnBackTbh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                resetCreatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        btnCancelTbh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                resetCreatePanel();
                resetReadPanel();
                cardLayout.show(cardPanel, "read");
            }
        });
        btnSaveTbh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //insert data transaksi ke database
//                //insert user data ke database
//                String username = inputUsernameTbh.getText();
//                User createUser = new User(-1, username, inputPasswordTbh.getText());
//                uc.insertDataUser(createUser);
//                createUser.setIdUser(uc.findIdByUsername(username));
//                //insert pengguna data ke database
//                Pengguna createPengguna = new Pengguna(-1, inputNamaLengkapTbh.getText(),
//                        inputNoTelpTbh.getText(), inputKuantitas.getText(), createUser);
//                pc.insertPengguna(createPengguna);
//                //update table model dan user card dan kembalikan ke panel read
//                resetUpdatePanel();
//                resetReadPanel();
                try {
                    inputKosongExceptionCreate();
                    inputNegatifExceptionCreate();
                    //dummy staff bertugas
                    //nanti diganti + janlup di updatenya juga
//                    Role r = new Role(6, 5000000.0, "Kasir");
//                    User u = new User(2, "Sampo", "Sam");
//                    Staff s = new Staff(124, "Jamuel Persuangan", "2023-06-05", "081327542234", "Apartement Panorama", r, u);

//                    PengadaanObat pengadaanObatNew = pengadaanObat;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String inputTglPengadaanObat = formatter.format(tanggalTransaksiDateChooser.getDate());
//                    pengadaanObatNew.setTanggalPengadaan(inputTglPengadaanObat);
                    Obat obatPilihan = (Obat) namaObatComboBox.getModel().getSelectedItem();
//                    pengadaanObatNew.setObat(obatPilihan);
//                    pengadaanObatNew.setKuantitas(Integer.parseInt(inputKuantitas.getText()));
//                    pengadaanObatNew.setSupplier(inputSupplier.getText());
//                    poc.insertPengadaanObat(pengadaanObat);
                    PengadaanObat PengadaanObatBaru = new PengadaanObat(-1, Integer.parseInt(inputKuantitas.getText()),
                            obatPilihan.getIdObat(), inputSupplier.getText(),
                            inputTglPengadaanObat, obatPilihan, staffLogin);//LoginRegisterView.sLogin);
                    poc.insertPengadaanObat(PengadaanObatBaru);
                    resetCreatePanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, "Data Harus Diisi Terlebih Dahulu!");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Harus Angka");
                } catch (InputNegatifException e) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Tidak Boleh 0 Atau Kurang Dari 0");
                }
            }
        });
        //action listener pada makestaff panel
        //
//        btnBackMS.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                resetMakeStaffPanel();
//                resetReadPanel();
//                cardLayout.show(cardPanel, "read");
//            }
//        });
//        btnCancelMS.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                resetMakeStaffPanel();
//                resetReadPanel();
//                cardLayout.show(cardPanel, "read");
//            }
//        });
//        btnSaveMS.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                //mengambil value dari jdatechooser dan combo box dropdown
//                //lalu melakukan insert staff dan mendelete pengguna/pembeli
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                if (tanggalMasukDateChooser.getDate() != null) {
//                    String inputTglMasuk = formatter.format(tanggalMasukDateChooser.getDate());
//                    Role rolePilihan = (Role) roleComboBox.getModel().getSelectedItem();
//                    //nip -1 karena pada akhirnya saat insert juga auto increment
//                    Staff staffInput = new Staff(-1, pengguna.getNama(), inputTglMasuk, pengguna.getNoTelp(), pengguna.getAlamat(), rolePilihan, pengguna.getUser());
//                    sc.insertDataStaff(staffInput);
//                    pc.deleteDataPengguna(pengguna.getIdPengguna());
//                    //
//                    System.out.println(inputTglMasuk);
//                    System.out.println(rolePilihan.getIdRole());
//                    System.out.println("Berhasil make staff!");
//                } else {
//                    System.out.println("[EXCEPTION] Tanggal pilihan null!");
//                }
//                resetMakeStaffPanel();
//                resetReadPanel();
//                cardLayout.show(cardPanel, "read");
//            }
//        });

        //action listener untuk search
        //tanpa button
        addFieldSearchActionListener();
    }

    public void inputNegatifExceptionCreate() throws InputNegatifException {
        if (Integer.parseInt(inputKuantitas.getText()) == 0 || Integer.parseInt(inputKuantitas.getText()) < 0) {
            throw new InputNegatifException();
        }
    }

    public void inputNegatifExceptionUpdate() throws InputNegatifException {
        if (Integer.parseInt(inputKuantitas1.getText()) == 0 || Integer.parseInt(inputKuantitas1.getText()) < 0) {
            throw new InputNegatifException();
        }
    }

    //Method-method pada Make Staff Panel
    private void resetMakeStaffPanel() {
        tanggalMasukDateChooser.setDate(null);
    }

    private void insertRoleToComboBox() {
        List<Role> listRole = rc.showRole();
        roleComboBox.removeAllItems();
        for (Role role : listRole) {
            roleComboBox.addItem(role);
        }
    }

    public void inputKosongExceptionCreate() throws InputKosongException {
        if (tanggalTransaksiDateChooser.getDate() == null || namaObatComboBox.getSelectedIndex() == -1 || inputKuantitas.getText().isEmpty() || inputSupplier.getText().isEmpty()) {
            throw new InputKosongException();
        }
    }

    public void inputKosongExceptionUpdate() throws InputKosongException {
        if (tanggalTransaksiDateChooser1.getDate() == null || namaObatComboBox1.getModel().getSelectedItem() == null || inputKuantitas1.getText().isEmpty() || inputSupplier1.getText().isEmpty()) {
            throw new InputKosongException();
        }
    }

    private void initTanggalMasuk() {
        LocalDate dateToday = LocalDate.now();
        LocalDate dateThreeMonthsBeforeToday = dateToday.minusMonths(3);
        //Maksimal input adalah hari ini, dan minimal input adalah 3 bulan sebelum hari ini
        //Fungsi dibawah adalah untuk konversi dari LocalDate ke Date
        tanggalMasukDateChooser.setMinSelectableDate(Date.from(dateThreeMonthsBeforeToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        Date today = Date.from(dateToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        tanggalMasukDateChooser.setMaxSelectableDate(today);
        //Default value awal untuk textField tanggal masuk adalah today alias hari ini
        //Sehingga disetting sebagai berikut:
        tanggalMasukDateChooser.setDate(today);
    }

    //Method-method pada Create/Tambah Panel
    private void resetCreatePanel() {
//        inputNamaLengkapTbh.setText("");
//        inputKuantitas.setText("");
//        inputNoTelpTbh.setText("");
//        inputUsernameTbh.setText("");
//        inputPasswordTbh.setText("");

        tanggalTransaksiDateChooser.setDate(null);
        namaObatComboBox.setSelectedIndex(0);
        inputKuantitas.setText("");
        inputSupplier.setText("");
    }

    private void setObatToDropdownCreate() {
        namaObatComboBox.removeAllItems();
        listObat = oc.showListObat();
        for (int i = 0; i < listObat.size(); i++) {
            namaObatComboBox.addItem(listObat.get(i));
        }
    }

    private void insertAndSetObatToComboBox(PengadaanObat po) {
        List<Obat> listObat = oc.showListObat();
        namaObatComboBox1.removeAllItems();
        for (Obat obat : listObat) {
            namaObatComboBox1.addItem(obat);
            if (obat.getNamaObat().equals(po.getObat().getNamaObat())) {
                namaObatComboBox1.setSelectedItem(obat);
            }
        }
    }

    //Method-method pada Update Panel
    private void setTextToComponent(int kuantitas, String supplier) {
//        inputNamaLengkap.setText(nama);
//        inputAlamat.setText(alamat);
//        inputNoTelp.setText(noTelp);

        Date tanggalPengadaan;
        try {
            tanggalPengadaan = new SimpleDateFormat("yyyy-MM-dd").parse(pengadaanObat.getTanggalPengadaan());
            tanggalTransaksiDateChooser1.setDate(tanggalPengadaan);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        inputKuantitas1.setText(String.valueOf(kuantitas));
        inputSupplier1.setText(supplier);
    }

    private void addBtnBackActionListener(ActionListener event) {
        btnBack.addActionListener(event);
    }

    private void addBtnSaveActionListener(ActionListener event) {
        btnSave.addActionListener(event);
    }

    private void addBtnCancelActionListener(ActionListener event) {
        btnCancel.addActionListener(event);
    }

    private void resetUpdatePanel() {
//        inputNamaLengkap.setText("");
//        inputAlamat.setText("");
//        inputNoTelp.setText("");
        tanggalTransaksiDateChooser1.setDate(null);
        namaObatComboBox1.setSelectedIndex(0);
        inputKuantitas1.setText("");
        inputSupplier1.setText("");
    }

    //Method-method pada Read Panel
    private void addBtnTambahActionListener(ActionListener event) {
        btnTambah.addActionListener(event);
    }

    private void resetReadPanel() {
        searchInput = "";
        fieldSearch.setText(searchInput);
        showUserCard(noUserCard);
        setTableModel(poc.showPengadaanObat(""));
    }

    //untuk melakukan search segera setelah keyboard direlease
    private void addFieldSearchActionListener() {
        fieldSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                searchInput = fieldSearch.getText();
                //asynchronous process
                Thread newThread = new Thread(() -> {
                    try {
                        Thread.sleep(200);
                        PengadaanObatTable poTable = poc.showPengadaanObat(searchInput);
                        SwingUtilities.invokeLater(() -> {
                            customTable.setModel(poTable);
                            customTable.revalidate();
                        });
                    } catch (Exception e) {
                    }
                });
                newThread.start();
            }
        });
    }

    private void setJudulForm(String text) {
        judulForm.setForeground(cp.getColor(0));
        judulForm.setText("Tabel Data " + text);
        judulDataPilihan.setForeground(cp.getColor(0));
        judulDataPilihan.setText("Kelola Data " + text);
        judulUpdate.setForeground(cp.getColor(0));
        judulUpdate.setText("Update Data " + text);
        judulCreate.setForeground(cp.getColor(0));
        judulCreate.setText("Tambah Data " + text);
    }

    private void setTableModel(AbstractTableModel tableModel) {
        this.customTable.setModel((PengadaanObatTable) tableModel);
    }

    private void showUserCard(Component panel) {
        panelCard.removeAll();
        panelCard.add(panel);
//        panelCard.repaint();
        panelCard.revalidate();
    }

    //listener untuk table clicked pada panel ReadDataPembeli 
    //serta memiliki listener untuk komponen2 pada panel selanjutnya
    private void initTableListener() {

        customTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                //get value dari table clicked
                if (namaUserGroup.equalsIgnoreCase("Pengadaan Obat")) {
                    int clickedRow = customTable.getSelectedRow();
                    TableModel tableModel = customTable.getModel();
                    //untuk reset pengguna
                    pengadaanObat = null;
                    //assign pengguna sesuai row yang di klik pada tabel
                    pengadaanObat = (PengadaanObat) tableModel.getValueAt(clickedRow, 6);
                    //show user card panel
                    pengadaanObatCard = new PengadaanObatCard(pengadaanObat);
                    showUserCard(pengadaanObatCard);

                    //user card button edit action listener
                    pengadaanObatCard.addBtnEditActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //melempar event untuk dieksekusi ke panel/frame diatasnya yaitu SuperAdminView
                            //fungsinya untuk mengganti panel ReadDataPembeli ke panel UpdateDataPembeli
                            //setTextToComponent(pengguna.getNama(), pengguna.getAlamat(), pengguna.getNoTelp());

                            setTextToComponent(pengadaanObat.getKuantitas(), pengadaanObat.getSupplier());
                            insertAndSetObatToComboBox(pengadaanObat);
                            cardLayout.show(cardPanel, "update");
                        }
                    });
                    //user card button delete action listener
                    pengadaanObatCard.addBtnDeleteActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //ketika btn delete di user card
                            //pc.deleteDataPengguna(pengguna.getIdPengguna());
                            //uc.deleteDataUser(pengguna.getUser().getIdUser());

                            poc.deletePengadaanObat(pengadaanObat.getIdPengadaan());

                            System.out.println("Berhasil delete");
                            showUserCard(noUserCard);
                            setTableModel(poc.showPengadaanObat(""));
                        }
                    });
//                    //user card button make staff action listener
//                    userCard.addBtnMakeStaffActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent arg0) {
//                            initTanggalMasuk();
//                            insertRoleToComboBox();
//                            judulMS.setText("<html>Jadikan " + pengguna.getNama() + " sebagai Staff?</html>");
//                            cardLayout.show(cardPanel, "makeStaff");
//                        }
//                    });
                }

            }
        });

//        customTable.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusLost(FocusEvent arg0) {
//                customTable.clearSelection();
//            }
//        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cardPanel = new javax.swing.JPanel();
        readPengadaanObat = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        customTable = new swing.component.CustomTable();
        jPanel3 = new javax.swing.JPanel();
        fieldSearch = new swing.component.TextFieldWithBackground();
        jPanel4 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        judulDataPilihan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new swing.component.ButtonRectangle();
        judulForm = new javax.swing.JLabel();
        updatePembeli = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnBack = new swing.component.ButtonOutLine();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSave = new swing.component.ButtonRound();
        btnCancel = new swing.component.ButtonRound();
        judulUpdate = new javax.swing.JLabel();
        labelTanggal1 = new javax.swing.JLabel();
        tanggalTransaksiDateChooser1 = new com.toedter.calendar.JDateChooser();
        labelNamaObat1 = new javax.swing.JLabel();
        namaObatComboBox1 = new javax.swing.JComboBox<>();
        labelKuantitas1 = new javax.swing.JLabel();
        inputKuantitas1 = new swing.component.TextFieldInput();
        labelSupplier1 = new javax.swing.JLabel();
        inputSupplier1 = new swing.component.TextFieldInput();
        jPanel8 = new javax.swing.JPanel();
        createPengadaanObat = new javax.swing.JPanel();
        scrollPaneCreate = new javax.swing.JScrollPane();
        containerCreate = new javax.swing.JPanel();
        labelTanggal = new javax.swing.JLabel();
        tanggalTransaksiDateChooser = new com.toedter.calendar.JDateChooser();
        labelNamaObat = new javax.swing.JLabel();
        namaObatComboBox = new javax.swing.JComboBox<>();
        labelKuantitas = new javax.swing.JLabel();
        inputKuantitas = new swing.component.TextFieldInput();
        labelSupplier = new javax.swing.JLabel();
        inputSupplier = new swing.component.TextFieldInput();
        judulCreate = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnSaveTbh = new swing.component.ButtonRound();
        btnCancelTbh = new swing.component.ButtonRound();
        btnBackTbh = new swing.component.ButtonOutLine();
        makeStaff = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnBackMS = new swing.component.ButtonOutLine();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnSaveMS = new swing.component.ButtonRound();
        btnCancelMS = new swing.component.ButtonRound();
        judulMS = new javax.swing.JLabel();
        tanggalMasukDateChooser = new com.toedter.calendar.JDateChooser();
        roleComboBox = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(684, 652));

        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new java.awt.Dimension(684, 652));
        cardPanel.setLayout(new java.awt.CardLayout());

        readPengadaanObat.setOpaque(false);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        customTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        customTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(customTable);

        jPanel3.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setOpaque(false);

        panelCard.setOpaque(false);
        panelCard.setPreferredSize(new java.awt.Dimension(407, 80));
        panelCard.setLayout(new java.awt.BorderLayout());

        judulDataPilihan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulDataPilihan.setForeground(new java.awt.Color(0, 0, 0));
        judulDataPilihan.setText("Judul Pilihan");

        jPanel1.setOpaque(false);

        btnTambah.setText("+");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(panelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(judulDataPilihan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(judulDataPilihan)
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCard, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        judulForm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulForm.setForeground(new java.awt.Color(0, 0, 0));
        judulForm.setText("Judul Form");

        javax.swing.GroupLayout readPengadaanObatLayout = new javax.swing.GroupLayout(readPengadaanObat);
        readPengadaanObat.setLayout(readPengadaanObatLayout);
        readPengadaanObatLayout.setHorizontalGroup(
            readPengadaanObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readPengadaanObatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(readPengadaanObatLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(readPengadaanObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(readPengadaanObatLayout.createSequentialGroup()
                        .addComponent(judulForm)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(readPengadaanObatLayout.createSequentialGroup()
                        .addGroup(readPengadaanObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                        .addGap(30, 30, 30))))
        );
        readPengadaanObatLayout.setVerticalGroup(
            readPengadaanObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readPengadaanObatLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judulForm)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        cardPanel.add(readPengadaanObat, "read");

        updatePembeli.setOpaque(false);

        jPanel5.setOpaque(false);

        btnBack.setForeground(cp.getColor(0)
        );
        btnBack.setText("Kembali");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel6.setOpaque(false);

        jPanel7.setOpaque(false);

        btnSave.setText("Save");

        btnCancel.setText("Cancel");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        judulUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulUpdate.setForeground(cp.getColor(0)
        );
        judulUpdate.setText("Update Data Pembeli");

        labelTanggal1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggal1.setForeground(cp.getColor(0)
        );
        labelTanggal1.setText("Tanggal Pengadaan Obat (Default: Hari Ini)");

        tanggalTransaksiDateChooser1.setForeground(new java.awt.Color(122, 140, 141));
        tanggalTransaksiDateChooser1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        labelNamaObat1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNamaObat1.setForeground(cp.getColor(0)
        );
        labelNamaObat1.setText("Nama Obat");

        namaObatComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        namaObatComboBox1.setForeground(new java.awt.Color(122, 140, 141));

        labelKuantitas1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas1.setForeground(cp.getColor(0)
        );
        labelKuantitas1.setText("Kuantitas");

        inputKuantitas1.setHint("25");

        labelSupplier1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelSupplier1.setForeground(cp.getColor(0)
        );
        labelSupplier1.setText("Supplier");

        inputSupplier1.setHint("Kalbe Farma");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelTanggal1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(namaObatComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tanggalTransaksiDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelKuantitas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelNamaObat1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputKuantitas1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSupplier1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputSupplier1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(judulUpdate))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulUpdate)
                .addGap(25, 25, 25)
                .addComponent(labelTanggal1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalTransaksiDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNamaObat1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaObatComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelKuantitas1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSupplier1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputSupplier1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout updatePembeliLayout = new javax.swing.GroupLayout(updatePembeli);
        updatePembeli.setLayout(updatePembeliLayout);
        updatePembeliLayout.setHorizontalGroup(
            updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePembeliLayout.createSequentialGroup()
                .addGroup(updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, updatePembeliLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        updatePembeliLayout.setVerticalGroup(
            updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePembeliLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(updatePembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        cardPanel.add(updatePembeli, "update");

        createPengadaanObat.setOpaque(false);

        scrollPaneCreate.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneCreate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrollPaneCreate.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCreate.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneCreate.setOpaque(false);

        labelTanggal.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggal.setForeground(cp.getColor(0)
        );
        labelTanggal.setText("Tanggal Pengadaan Obat (Default: Hari Ini)");

        tanggalTransaksiDateChooser.setForeground(new java.awt.Color(122, 140, 141));
        tanggalTransaksiDateChooser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        labelNamaObat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNamaObat.setForeground(cp.getColor(0)
        );
        labelNamaObat.setText("Nama Obat");

        namaObatComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        namaObatComboBox.setForeground(new java.awt.Color(122, 140, 141));

        labelKuantitas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas.setForeground(cp.getColor(0)
        );
        labelKuantitas.setText("Kuantitas");

        inputKuantitas.setHint("25");

        labelSupplier.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelSupplier.setForeground(cp.getColor(0)
        );
        labelSupplier.setText("Supplier");

        inputSupplier.setHint("Kalbe Farma");

        judulCreate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulCreate.setForeground(cp.getColor(0)
        );
        judulCreate.setText("Tambah Data");

        jPanel11.setOpaque(false);

        btnSaveTbh.setText("Save");

        btnCancelTbh.setText("Cancel");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSaveTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        btnBackTbh.setForeground(cp.getColor(0)
        );
        btnBackTbh.setText("Kembali");

        javax.swing.GroupLayout containerCreateLayout = new javax.swing.GroupLayout(containerCreate);
        containerCreate.setLayout(containerCreateLayout);
        containerCreateLayout.setHorizontalGroup(
            containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addComponent(judulCreate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelTanggal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(namaObatComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 378, Short.MAX_VALUE)
                            .addComponent(tanggalTransaksiDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelNamaObat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(inputKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputSupplier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(300, Short.MAX_VALUE))))
        );
        containerCreateLayout.setVerticalGroup(
            containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(judulCreate)
                .addGap(25, 25, 25)
                .addComponent(labelTanggal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalTransaksiDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNamaObat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaObatComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelKuantitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSupplier)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        scrollPaneCreate.setViewportView(containerCreate);

        javax.swing.GroupLayout createPengadaanObatLayout = new javax.swing.GroupLayout(createPengadaanObat);
        createPengadaanObat.setLayout(createPengadaanObatLayout);
        createPengadaanObatLayout.setHorizontalGroup(
            createPengadaanObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        createPengadaanObatLayout.setVerticalGroup(
            createPengadaanObatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );

        cardPanel.add(createPengadaanObat, "create");

        makeStaff.setOpaque(false);

        jPanel9.setOpaque(false);

        btnBackMS.setForeground(cp.getColor(0)
        );
        btnBackMS.setText("Kembali");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMS, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setForeground(cp.getColor(0)
        );
        jLabel10.setText("Tanggal Masuk");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel11.setForeground(cp.getColor(0)
        );
        jLabel11.setText("Role");

        jPanel12.setOpaque(false);

        btnSaveMS.setText("Save");

        btnCancelMS.setText("Cancel");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSaveMS, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelMS, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveMS, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelMS, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        judulMS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulMS.setForeground(cp.getColor(0)
        );
        judulMS.setText("Jadikan <Nama> Sebagai Staff?");

        tanggalMasukDateChooser.setBackground(new java.awt.Color(255, 255, 255));
        tanggalMasukDateChooser.setForeground(new java.awt.Color(0, 0, 0));
        tanggalMasukDateChooser.setDateFormatString("dd MMMM yyyy");
        tanggalMasukDateChooser.setMaxSelectableDate(new java.util.Date(253370743293000L));
        tanggalMasukDateChooser.setPreferredSize(new java.awt.Dimension(88, 42));

        roleComboBox.setPreferredSize(new java.awt.Dimension(72, 42));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(494, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(judulMS)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(roleComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tanggalMasukDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                        .addContainerGap(201, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulMS)
                .addGap(25, 25, 25)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalMasukDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        jPanel13.setOpaque(false);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout makeStaffLayout = new javax.swing.GroupLayout(makeStaff);
        makeStaff.setLayout(makeStaffLayout);
        makeStaffLayout.setHorizontalGroup(
            makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeStaffLayout.createSequentialGroup()
                .addGroup(makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, makeStaffLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        makeStaffLayout.setVerticalGroup(
            makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeStaffLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        cardPanel.add(makeStaff, "makeStaff");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.component.ButtonOutLine btnBack;
    private swing.component.ButtonOutLine btnBackMS;
    private swing.component.ButtonOutLine btnBackTbh;
    private swing.component.ButtonRound btnCancel;
    private swing.component.ButtonRound btnCancelMS;
    private swing.component.ButtonRound btnCancelTbh;
    private swing.component.ButtonRound btnSave;
    private swing.component.ButtonRound btnSaveMS;
    private swing.component.ButtonRound btnSaveTbh;
    private swing.component.ButtonRectangle btnTambah;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel containerCreate;
    private javax.swing.JPanel createPengadaanObat;
    private swing.component.CustomTable customTable;
    private swing.component.TextFieldWithBackground fieldSearch;
    private swing.component.TextFieldInput inputKuantitas;
    private swing.component.TextFieldInput inputKuantitas1;
    private swing.component.TextFieldInput inputSupplier;
    private swing.component.TextFieldInput inputSupplier1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judulCreate;
    private javax.swing.JLabel judulDataPilihan;
    private javax.swing.JLabel judulForm;
    private javax.swing.JLabel judulMS;
    private javax.swing.JLabel judulUpdate;
    private javax.swing.JLabel labelKuantitas;
    private javax.swing.JLabel labelKuantitas1;
    private javax.swing.JLabel labelNamaObat;
    private javax.swing.JLabel labelNamaObat1;
    private javax.swing.JLabel labelSupplier;
    private javax.swing.JLabel labelSupplier1;
    private javax.swing.JLabel labelTanggal;
    private javax.swing.JLabel labelTanggal1;
    private javax.swing.JPanel makeStaff;
    private javax.swing.JComboBox<Obat> namaObatComboBox;
    private javax.swing.JComboBox<Obat> namaObatComboBox1;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel readPengadaanObat;
    private javax.swing.JComboBox<Role> roleComboBox;
    private javax.swing.JScrollPane scrollPaneCreate;
    private com.toedter.calendar.JDateChooser tanggalMasukDateChooser;
    private com.toedter.calendar.JDateChooser tanggalTransaksiDateChooser;
    private com.toedter.calendar.JDateChooser tanggalTransaksiDateChooser1;
    private javax.swing.JPanel updatePembeli;
    // End of variables declaration//GEN-END:variables
}
