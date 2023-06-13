package panel.apoteker;

import Exception.InputKosongException;
import Exception.TglProduksiException;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import control.ObatControl;
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
import control.TransaksiControl;
import control.UserControl;
import exception.DeleteObatException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Obat;
import model.Pengguna;
import swing.ColorPallete;
import swing.component.dashboard.ObatCard;
import table.ObatTable;

/**
 *
 * @author AG SETO GALIH D
 */
public class ObatForm extends javax.swing.JPanel {

    private Obat obat;
    private ObatControl oc;

    //untuk menyimpan info Pengguna yang sedang dipilih
    private Pengguna pengguna;

    private PenggunaControl pc;
    private StaffControl sc;
    private UserControl uc;
    private RoleControl rc;
    private TransaksiControl tc;

    private static ColorPallete cp = new ColorPallete();
    private ObatCard obatCard;
    private NoUserCard noUserCard = new NoUserCard();
    private String namaUserGroup;
    private CardLayout cardLayout;

    private String searchInput = "";

    //Konstruktor
    public ObatForm() {
        this.namaUserGroup = "Obat";

        this.oc = new ObatControl();

        this.pc = new PenggunaControl();
        this.uc = new UserControl();
        this.rc = new RoleControl();
        this.sc = new StaffControl();
        this.tc = new TransaksiControl();

        initComponents();

        showUserCard(noUserCard);

        setTableModel(oc.showDataObat(""));

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

//        btnBackMS.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
//        btnCancelMS.setBackground(new Color(240, 240, 240));
//        btnCancelMS.setForeground(cp.getColor(0));
//        btnCancelMS.setColorEffectRGB(220, 220, 220);
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
                try {
                    inputKosongUpdateException();
                    tglProduksiException(tanggalKadaluarsaChooser1.getDate(), tanggalProduksiChooser1.getDate());

                    //update data ke database
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String TanggalProduksi = formatter.format(tanggalProduksiChooser1.getDate());
                    String TanggalKadaluarsa = formatter.format(tanggalKadaluarsaChooser1.getDate());
                    Obat obatNew = new Obat(obat.getIdObat(), Integer.parseInt(inputKuantitas2.getText()), inputNama1.getText(), TanggalKadaluarsa, TanggalProduksi, Double.parseDouble(inputHarga1.getText()));
                    oc.updateDataObat(obat.getIdObat(), obatNew);
                    setTableModel(oc.showDataObat(""));
//                pc.updateDataPengguna(penggunaNew);
//                //update table model dan user card dan kembalikan ke panel read
//                resetUpdatePanel();
//                resetReadPanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, e.message());
                } catch (NumberFormatException ec) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Dan Harga harus angka");
                } catch (TglProduksiException ex) {
                    JOptionPane.showMessageDialog(null, ex.message());
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
                try {
                    inputKosongException();
                    tglProduksiException(tanggalKadaluarsaChooser.getDate(), tanggalProduksiChooser.getDate());
                    String namaObat = inputNama.getText();
                    double hargaObat = Double.parseDouble(inputHarga.getText());
                    int kuantitasObat = Integer.parseInt(inputKuantitas.getText());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String TanggalProduksi = formatter.format(tanggalProduksiChooser.getDate());
                    String TanggalKadaluarsa = formatter.format(tanggalKadaluarsaChooser.getDate());
                    Obat Ob = new Obat(0, kuantitasObat, namaObat, TanggalKadaluarsa, TanggalProduksi, hargaObat);
                    oc.insertDataObat(Ob);
//            RekamMedis rm = new RekamMedis(diagnosaInput.getText(), Float.parseFloat(totalBiayaInput.getText()),tindakan, selectedTM, selectedPasien);
//            rmc.insertDataRekamMedis(rm);
                    //setTableModel(oc.showDataObat(""));
                    resetCreatePanel();
                    resetReadPanel();
                    cardLayout.show(cardPanel, "read");
                    //setComponents();
                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, e.message());
                } catch (NumberFormatException ec) {
                    JOptionPane.showMessageDialog(null, "Kuantitas Dan Harga harus angka");
                } catch (TglProduksiException ex) {
                    JOptionPane.showMessageDialog(null, ex.message());
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

    private void deleteObatException(int idObat) throws DeleteObatException {
        if (tc.cekNullObat(idObat) == 1) {
            throw new DeleteObatException();
        }
    }

    //Method-method pada Create/Tambah Panel
    private void resetCreatePanel() {
        inputNama.setText("");
        inputKuantitas.setText("");
        inputHarga.setText("");
        ResetTanggal();
    }

    private void ResetTanggal() {
        tanggalKadaluarsaChooser.setDate(null);
        tanggalKadaluarsaChooser1.setDate(null);
        tanggalProduksiChooser.setDate(null);
        tanggalProduksiChooser1.setDate(null);
    }

    //Method-method pada Update Panel
    private void setTextToComponent(Obat o) {
        try {
            inputHarga1.setText(String.valueOf(o.getHarga()));
            inputKuantitas2.setText(String.valueOf(o.getKuantitas()));
            inputNama1.setText(o.getNamaObat());
            Date kadal = new SimpleDateFormat("yyyy-MM-dd").parse(o.getTanggalKadaluarsa());
            Date prod = new SimpleDateFormat("yyyy-MM-dd").parse(o.getTanggalProduksi());
            tanggalKadaluarsaChooser1.setDate(kadal);
            tanggalProduksiChooser1.setDate(prod);
        } catch (Exception e) {

        }
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
        inputNama.setText("");
        inputKuantitas.setText("");
        inputHarga.setText("");
        ResetTanggal();
    }

    //Method-method pada Read Panel
    private void addBtnTambahActionListener(ActionListener event) {
        btnTambah.addActionListener(event);
    }

    private void resetReadPanel() {
        searchInput = "";
        fieldSearch.setText(searchInput);
        showUserCard(noUserCard);
        setTableModel(oc.showDataObat(""));
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
                        ObatTable oTable = oc.showDataObat(searchInput);
                        SwingUtilities.invokeLater(() -> {
                            customTable.setModel(oTable);
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
        this.customTable.setModel((ObatTable) tableModel);
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
                if (namaUserGroup.equalsIgnoreCase("Obat")) {
                    int clickedRow = customTable.getSelectedRow();
                    TableModel tableModel = customTable.getModel();
                    //untuk reset pengguna
                    obat = null;
                    //assign pengguna sesuai row yang di klik pada tabel
                    obat = (Obat) tableModel.getValueAt(clickedRow, 6);
                    //show user card panel
                    obatCard = new ObatCard(obat);
                    showUserCard(obatCard);

                    //user card button edit action listener
                    obatCard.addBtnEditActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //melempar event untuk dieksekusi ke panel/frame diatasnya yaitu SuperAdminView
                            //fungsinya untuk mengganti panel ReadDataPembeli ke panel UpdateDataPembeli
                            setTextToComponent(obat);
                            cardLayout.show(cardPanel, "update");
                        }
                    });
                    //user card button delete action listener
                    obatCard.addBtnDeleteActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            try {
                                //ketika btn delete di user card
                                deleteObatException(obat.getIdObat());
                                oc.deleteDataObat(obat.getIdObat());
                                //uc.deleteDataUser(pengguna.getUser().getIdUser());
                                System.out.println("Berhasil delete");
                                showUserCard(noUserCard);
                                setTableModel(oc.showDataObat(""));

                            } catch (DeleteObatException e) {
                                JOptionPane.showMessageDialog(null, e.message());
                            }
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
        labelNama1 = new javax.swing.JLabel();
        inputNama1 = new swing.component.TextFieldInput();
        labelHarga1 = new javax.swing.JLabel();
        inputHarga1 = new swing.component.TextFieldInput();
        labelKuantitas2 = new javax.swing.JLabel();
        inputKuantitas2 = new swing.component.TextFieldInput();
        labelTanggalProd1 = new javax.swing.JLabel();
        tanggalProduksiChooser1 = new com.toedter.calendar.JDateChooser();
        labelTanggalKada1 = new javax.swing.JLabel();
        tanggalKadaluarsaChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        createPengadaanObat = new javax.swing.JPanel();
        scrollPaneCreate = new javax.swing.JScrollPane();
        containerCreate = new javax.swing.JPanel();
        labelNama = new javax.swing.JLabel();
        inputNama = new swing.component.TextFieldInput();
        labelHarga = new javax.swing.JLabel();
        inputHarga = new swing.component.TextFieldInput();
        labelKuantitas = new javax.swing.JLabel();
        inputKuantitas = new swing.component.TextFieldInput();
        labelTanggalProd = new javax.swing.JLabel();
        tanggalProduksiChooser = new com.toedter.calendar.JDateChooser();
        labelTanggalKada = new javax.swing.JLabel();
        tanggalKadaluarsaChooser = new com.toedter.calendar.JDateChooser();
        judulCreate = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnSaveTbh = new swing.component.ButtonRound();
        btnCancelTbh = new swing.component.ButtonRound();
        btnBackTbh = new swing.component.ButtonOutLine();

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
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        cardPanel.add(readPengadaanObat, "read");

        updatePembeli.setOpaque(false);

        jPanel5.setOpaque(false);

        btnBack.setForeground(cp.getColor(0)
        );
        btnBack.setText("Kembali");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

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
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

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

        labelNama1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNama1.setForeground(cp.getColor(0)
        );
        labelNama1.setText("Nama Obat");

        inputNama1.setHint("25");

        labelHarga1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelHarga1.setForeground(cp.getColor(0)
        );
        labelHarga1.setText("Harga");

        inputHarga1.setHint("25000");

        labelKuantitas2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas2.setForeground(cp.getColor(0)
        );
        labelKuantitas2.setText("Kuantitas");

        inputKuantitas2.setHint("25");

        labelTanggalProd1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggalProd1.setForeground(cp.getColor(0)
        );
        labelTanggalProd1.setText("Tanggal Produksi");

        tanggalProduksiChooser1.setForeground(new java.awt.Color(122, 140, 141));
        tanggalProduksiChooser1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        labelTanggalKada1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggalKada1.setForeground(cp.getColor(0)
        );
        labelTanggalKada1.setText("Tanggal Kadaluarsa");

        tanggalKadaluarsaChooser1.setForeground(new java.awt.Color(122, 140, 141));
        tanggalKadaluarsaChooser1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(labelNama1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tanggalProduksiChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelKuantitas2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelHarga1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputKuantitas2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputHarga1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTanggalProd1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputNama1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelTanggalKada1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tanggalKadaluarsaChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(judulUpdate))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulUpdate)
                .addGap(25, 25, 25)
                .addComponent(labelNama1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNama1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(labelHarga1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputHarga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelKuantitas2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelTanggalProd1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalProduksiChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelTanggalKada1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalKadaluarsaChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        labelNama.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNama.setForeground(cp.getColor(0)
        );
        labelNama.setText("Nama Obat");

        inputNama.setHint("25");

        labelHarga.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelHarga.setForeground(cp.getColor(0)
        );
        labelHarga.setText("Harga");

        inputHarga.setHint("25000");

        labelKuantitas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelKuantitas.setForeground(cp.getColor(0)
        );
        labelKuantitas.setText("Kuantitas");

        inputKuantitas.setHint("25");

        labelTanggalProd.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggalProd.setForeground(cp.getColor(0)
        );
        labelTanggalProd.setText("Tanggal Produksi");

        tanggalProduksiChooser.setForeground(new java.awt.Color(122, 140, 141));
        tanggalProduksiChooser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        labelTanggalKada.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelTanggalKada.setForeground(cp.getColor(0)
        );
        labelTanggalKada.setText("Tanggal Kadaluarsa");

        tanggalKadaluarsaChooser.setForeground(new java.awt.Color(122, 140, 141));
        tanggalKadaluarsaChooser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        judulCreate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulCreate.setForeground(cp.getColor(0)
        );
        judulCreate.setText("Tambah Data");

        jPanel11.setOpaque(false);

        btnSaveTbh.setText("Save");
        btnSaveTbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTbhActionPerformed(evt);
            }
        });

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
        btnBackTbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackTbhActionPerformed(evt);
            }
        });

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
                            .addComponent(labelNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tanggalProduksiChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(inputKuantitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTanggalProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputNama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTanggalKada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tanggalKadaluarsaChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(labelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelHarga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelKuantitas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputKuantitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelTanggalProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalProduksiChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(labelTanggalKada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalKadaluarsaChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );

        cardPanel.add(createPengadaanObat, "create");

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

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahActionPerformed

    public void inputKosongException() throws InputKosongException {
        if (inputNama.getText().isBlank() || inputHarga.getText().isBlank() || inputKuantitas.getText().isBlank() || tanggalProduksiChooser.getDate() == null || tanggalKadaluarsaChooser.getDate() == null) {
            throw new InputKosongException();
        }
    }

    public void inputKosongUpdateException() throws InputKosongException {
        if (inputNama1.getText().isBlank() || inputHarga1.getText().isBlank() || inputKuantitas2.getText().isBlank() || tanggalProduksiChooser1.getDate() == null || tanggalKadaluarsaChooser1.getDate() == null) {
            throw new InputKosongException();
        }
    }

    public void tglProduksiException(Date date1, Date date2) throws TglProduksiException {
        if (date1.compareTo(date2) < 0) {
            throw new TglProduksiException();
        }
    }

    private void btnSaveTbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTbhActionPerformed
//        try{
//            inputKosongException();
//            tglProduksiException(tanggalKadaluarsaChooser.getDate(), tanggalProduksiChooser.getDate());
//            String namaObat = inputNama.getText();
//            double hargaObat = Double.parseDouble(inputHarga.getText());
//            int kuantitasObat = Integer.parseInt(inputKuantitas.getText());
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String TanggalProduksi= formatter.format(tanggalProduksiChooser.getDate());
//            String TanggalKadaluarsa = formatter.format(tanggalKadaluarsaChooser.getDate());
//            Obat Ob = new Obat(0,kuantitasObat, namaObat, TanggalKadaluarsa, TanggalProduksi, hargaObat);
//            oc.insertDataObat(Ob);
////            RekamMedis rm = new RekamMedis(diagnosaInput.getText(), Float.parseFloat(totalBiayaInput.getText()),tindakan, selectedTM, selectedPasien);
////            rmc.insertDataRekamMedis(rm);
//            setTableModel(oc.showDataObat(""));
//        //setComponents();
//        }catch(InputKosongException e){
//            JOptionPane.showMessageDialog(this, e.message());
//        }
//        catch(NumberFormatException ec){
//            JOptionPane.showMessageDialog(this, ec.getMessage());
//        }
//        catch(TglProduksiException ex){
//            JOptionPane.showMessageDialog(this, ex.message());
//        }
    }//GEN-LAST:event_btnSaveTbhActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        resetCreatePanel();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnBackTbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackTbhActionPerformed
        resetUpdatePanel();
    }//GEN-LAST:event_btnBackTbhActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.component.ButtonOutLine btnBack;
    private swing.component.ButtonOutLine btnBackTbh;
    private swing.component.ButtonRound btnCancel;
    private swing.component.ButtonRound btnCancelTbh;
    private swing.component.ButtonRound btnSave;
    private swing.component.ButtonRound btnSaveTbh;
    private swing.component.ButtonRectangle btnTambah;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel containerCreate;
    private javax.swing.JPanel createPengadaanObat;
    private swing.component.CustomTable customTable;
    private swing.component.TextFieldWithBackground fieldSearch;
    private swing.component.TextFieldInput inputHarga;
    private swing.component.TextFieldInput inputHarga1;
    private swing.component.TextFieldInput inputKuantitas;
    private swing.component.TextFieldInput inputKuantitas2;
    private swing.component.TextFieldInput inputNama;
    private swing.component.TextFieldInput inputNama1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judulCreate;
    private javax.swing.JLabel judulDataPilihan;
    private javax.swing.JLabel judulForm;
    private javax.swing.JLabel judulUpdate;
    private javax.swing.JLabel labelHarga;
    private javax.swing.JLabel labelHarga1;
    private javax.swing.JLabel labelKuantitas;
    private javax.swing.JLabel labelKuantitas2;
    private javax.swing.JLabel labelNama;
    private javax.swing.JLabel labelNama1;
    private javax.swing.JLabel labelTanggalKada;
    private javax.swing.JLabel labelTanggalKada1;
    private javax.swing.JLabel labelTanggalProd;
    private javax.swing.JLabel labelTanggalProd1;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel readPengadaanObat;
    private javax.swing.JScrollPane scrollPaneCreate;
    private com.toedter.calendar.JDateChooser tanggalKadaluarsaChooser;
    private com.toedter.calendar.JDateChooser tanggalKadaluarsaChooser1;
    private com.toedter.calendar.JDateChooser tanggalProduksiChooser;
    private com.toedter.calendar.JDateChooser tanggalProduksiChooser1;
    private javax.swing.JPanel updatePembeli;
    // End of variables declaration//GEN-END:variables
}
