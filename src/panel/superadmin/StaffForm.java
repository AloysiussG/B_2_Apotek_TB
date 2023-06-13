/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package panel.superadmin;

import Exception.InputKosongException;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import swing.component.dashboard.NoUserCard;
import swing.component.dashboard.UserCard;
import control.PenggunaControl;
import control.RoleControl;
import control.StaffControl;
import control.TransaksiControl;
import control.UserControl;
import exception.DeleteStaffException;
import exception.NoHpNumericException;
import exception.NoTelpException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Role;
import model.Staff;
import swing.ColorPallete;
import table.StaffTable;

/**
 *
 * @author AG SETO GALIH D
 */
public class StaffForm extends javax.swing.JPanel {

    //untuk menyimpan info Staff yang sedang dipilih
    private Staff staff;

    private StaffControl sc;
    private PenggunaControl pc;
    private UserControl uc;
    private RoleControl rc;
    private TransaksiControl tc;

    private static ColorPallete cp = new ColorPallete();
    private UserCard userCard;
    private NoUserCard noUserCard = new NoUserCard();
    private String namaUserGroup;
    private CardLayout cardLayout;

    private String searchInput = "";

    //Konstruktor
    public StaffForm() {
        this.namaUserGroup = "Staff";
        this.sc = new StaffControl();
        this.pc = new PenggunaControl();
        this.uc = new UserControl();
        this.rc = new RoleControl();
        this.tc = new TransaksiControl();

        initComponents();

        btnTambah.setVisible(false);

        showUserCard(noUserCard);

        setTableModel(sc.showDataStaff(""));
        setJudulForm(namaUserGroup);
        fieldSearch.setHint("Cari berdasarkan nama, kategori, dan lain-lain");
//
//        btnTambah.setIcon(new FlatSVGIcon("img/icon/addperson.svg", 1.5f));
//        btnTambah.setText("");
//
        btnBack.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
        btnCancel.setBackground(new Color(240, 240, 240));
        btnCancel.setForeground(cp.getColor(0));
        btnCancel.setColorEffectRGB(220, 220, 220);
//
//        btnBackTbh.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
//        btnCancelTbh.setBackground(new Color(240, 240, 240));
//        btnCancelTbh.setForeground(cp.getColor(0));
//        btnCancelTbh.setColorEffectRGB(220, 220, 220);
//
//        btnBackMS.setIcon(new FlatSVGIcon("img/icon/back.svg", 0.2f));
//        btnCancelMS.setBackground(new Color(240, 240, 240));
//        btnCancelMS.setForeground(cp.getColor(0));
//        btnCancelMS.setColorEffectRGB(220, 220, 220);
//
//        containerCreate.setBackground(new Color(255, 255, 255));
//
        cardLayout = (CardLayout) cardPanel.getLayout();

        //action listeners
        initTableListener();

        //other listeners
        //action listener pada read
        //btn tambah
//        addBtnTambahActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                cardLayout.show(cardPanel, "create");
//            }
//        });
        //
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
                    exceptionUpdatePanel();
                    noTelpExceptionEdit();
                    noNumericExceptionUpdate();

                    //update data ke database
                    Staff staffNew = staff;
                    staffNew.setNama(inputNamaLengkap.getText());
                    staffNew.setAlamat(inputAlamat.getText());
                    staffNew.setNoTelp(inputNoTelp.getText());

                    //mengambil value dari jdatechooser dan combo box dropdown
                    //lalu melakukan insert staff dan mendelete pengguna/pembeli
                    if (tanggalMasukDateChooser.getDate() != null) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String inputTglMasuk = formatter.format(tanggalMasukDateChooser.getDate());
                        staffNew.setTahunMasuk(inputTglMasuk);

                        Role rolePilihan = (Role) roleComboBox.getModel().getSelectedItem();
                        staffNew.setRole(rolePilihan);

                        sc.updateDataStaff(staffNew);

                        System.out.println(inputTglMasuk);
                        System.out.println(rolePilihan.getIdRole());
                        System.out.println("Berhasil update staff!");
                    } else {
                        System.out.println("[EXCEPTION] Tanggal pilihan null!");
                    }

                    //update table model dan user card dan kembalikan ke panel read
                    resetUpdatePanel();
                    resetReadPanel();
                    JOptionPane.showMessageDialog(null, "Berhasil update staff");
                    cardLayout.show(cardPanel, "read");

                } catch (InputKosongException e) {
                    JOptionPane.showMessageDialog(null, e.message());
                } catch (NoTelpException e) {
                    JOptionPane.showMessageDialog(null, e.message());
                } catch (NoHpNumericException e) {
                    JOptionPane.showMessageDialog(null, e.message());
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
//        btnBackTbh.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                resetCreatePanel();
//                resetReadPanel();
//                cardLayout.show(cardPanel, "read");
//            }
//        });
//        btnCancelTbh.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                resetCreatePanel();
//                resetReadPanel();
//                cardLayout.show(cardPanel, "read");
//            }
//        });
//        btnSaveTbh.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                //insert user data ke database
//                String username = inputUsernameTbh.getText();
//                User createUser = new User(-1, username, inputPasswordTbh.getText());
//                uc.insertDataUser(createUser);
//                createUser.setIdUser(uc.findIdByUsername(username));
//                //insert pengguna data ke database
//                Pengguna createPengguna = new Pengguna(-1, inputNamaLengkapTbh.getText(),
//                        inputNoTelpTbh.getText(), inputAlamatTbh.getText(), createUser);
//                pc.insertPengguna(createPengguna);
//                //update table model dan user card dan kembalikan ke panel read
//                resetUpdatePanel();
//                resetReadPanel();
//                cardLayout.show(cardPanel, "read");
//            }
//        });
        //
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
//            }
//        });
        //
        //action listener untuk search
        //tanpa button
        addFieldSearchActionListener();
    }

    private void noTelpExceptionEdit() throws NoTelpException {
        if (inputNoTelp.getText().length() < 10 || inputNoTelp.getText().length() > 13) {
            throw new NoTelpException();
        }
    }

    public void noNumericExceptionUpdate() throws NumberFormatException, NoHpNumericException {
        try {
            long temp = Long.parseLong(inputNoTelp.getText());
        } catch (NumberFormatException e) {
            throw new NoHpNumericException();
        }
    }

    private void exceptionUpdatePanel() throws InputKosongException {
        if (inputNamaLengkap.getText().isBlank()
                || inputAlamat.getText().isBlank()
                || inputNoTelp.getText().isBlank()
                || tanggalMasukDateChooser.getDate() == null
                || roleComboBox.getModel().getSelectedItem() == null) {
            throw new InputKosongException();
        }
    }

    private void resetTanggalMasuk() {
        tanggalMasukDateChooser.setDate(null);
    }

    private void insertAndSetRoleToComboBox(Staff s) {
        List<Role> listRole = rc.showRole();
        roleComboBox.removeAllItems();
        for (Role role : listRole) {
            roleComboBox.addItem(role);
            if (role.getNamaRole().equals(s.getRole().getNamaRole())) {
                roleComboBox.setSelectedItem(role);
            }
        }
    }

    private void initTanggalMasuk(Staff s) {
        try {
            LocalDate dateToday = LocalDate.now();
            LocalDate dateThreeMonthsBeforeToday = dateToday.minusMonths(3);
            //Maksimal input adalah hari ini, dan minimal input adalah 3 bulan sebelum hari ini atau tanggal masuk dari inputan terakhir (dicari yg paling kecil)
            //Fungsi dibawah adalah untuk konversi dari LocalDate ke Date
            Date threeMonthsBefore = Date.from(dateThreeMonthsBeforeToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            tanggalMasukDateChooser.setMaxSelectableDate(Date.from(dateToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

            String stringTglMasuk = s.getTahunMasuk();
            Date dateTglMasuk = new SimpleDateFormat("yyyy-MM-dd").parse(stringTglMasuk);
            if (dateTglMasuk != null) {
                tanggalMasukDateChooser.setDate(dateTglMasuk);
                if (dateTglMasuk.compareTo(threeMonthsBefore) > 0) {
                    tanggalMasukDateChooser.setMinSelectableDate(threeMonthsBefore);
                } else if (dateTglMasuk.compareTo(threeMonthsBefore) < 0) {
                    tanggalMasukDateChooser.setMinSelectableDate(dateTglMasuk);
                } else {
                    tanggalMasukDateChooser.setMinSelectableDate(threeMonthsBefore);
                }
            } else {
                System.out.println("tanggal masuk gagal dikonversi!");
            }
        } catch (Exception e) {
            System.out.println("tanggal masuk gagal dikonversi!");
        }

    }
    //
    //Method-method pada Create/Tambah Panel
//    private void resetCreatePanel() {
//        inputNamaLengkapTbh.setText("");
//        inputAlamatTbh.setText("");
//        inputNoTelpTbh.setText("");
//        inputUsernameTbh.setText("");
//        inputPasswordTbh.setText("");
//    }
    //
    //Method-method pada Update Panel

    private void setTextToComponent(String nama, String alamat, String noTelp) {
        inputNamaLengkap.setText(nama);
        inputAlamat.setText(alamat);
        inputNoTelp.setText(noTelp);
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
        inputNamaLengkap.setText("");
        inputAlamat.setText("");
        inputNoTelp.setText("");
        resetTanggalMasuk();
    }

    //Method-method pada Read Panel
    private void addBtnTambahActionListener(ActionListener event) {
        btnTambah.addActionListener(event);
    }

    private void resetReadPanel() {
        searchInput = "";
        fieldSearch.setText(searchInput);
        showUserCard(noUserCard);
        setTableModel(sc.showDataStaff(""));
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
                        StaffTable st = sc.showDataStaff(searchInput);
                        SwingUtilities.invokeLater(() -> {
                            customTable.setModel(st);
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
    }

    private void setTableModel(AbstractTableModel tableModel) {
        this.customTable.setModel((StaffTable) tableModel);
    }

    private void showUserCard(Component panel) {
        panelCard.removeAll();
        panelCard.add(panel);
        panelCard.repaint();
        panelCard.revalidate();
    }

    private void deleteStaffException(int nip) throws DeleteStaffException {
        if (tc.cekNullStaff(nip) == 1) {
            throw new DeleteStaffException();
        }
    }

    //listener untuk table clicked pada panel ReadDataPembeli 
    //serta memiliki listener untuk komponen2 pada panel selanjutnya
    private void initTableListener() {

        customTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {

                //get value dari table clicked
                if (namaUserGroup.equalsIgnoreCase("Staff")) {
                    int clickedRow = customTable.getSelectedRow();
                    TableModel tableModel = customTable.getModel();
                    //untuk reset staff
                    staff = null;
                    //assign staff sesuai row yang di klik pada tabel
                    staff = (Staff) tableModel.getValueAt(clickedRow, 8);
                    //show user card panel
                    userCard = new UserCard(staff);
                    showUserCard(userCard);

                    //user card button edit action listener
                    userCard.addBtnEditActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            //melempar event untuk dieksekusi ke panel/frame diatasnya yaitu SuperAdminView
                            //fungsinya untuk mengganti panel ReadDataPembeli ke panel UpdateDataPembeli
                            setTextToComponent(staff.getNama(), staff.getAlamat(), staff.getNoTelp());
                            insertAndSetRoleToComboBox(staff);
                            initTanggalMasuk(staff);
                            cardLayout.show(cardPanel, "update");
                        }
                    });
                    //user card button delete action listener
                    userCard.addBtnDeleteActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            try {
                                deleteStaffException(staff.getNIP());
                                //ketika btn delete di user card
                                sc.deleteDataStaff(staff.getNIP());
                                uc.deleteDataUser(staff.getUser().getIdUser());
                                System.out.println("Berhasil delete");
                                showUserCard(noUserCard);
                                setTableModel(sc.showDataStaff(""));
                            } catch (DeleteStaffException e) {
                                JOptionPane.showMessageDialog(null, e.message());
                            }
                        }
                    });
                    //user card button make staff action listener
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
        readStaff = new javax.swing.JPanel();
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
        updateStaff = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnBack = new swing.component.ButtonOutLine();
        panelComponentUpdate = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        inputNamaLengkap = new swing.component.TextFieldInput();
        jLabel3 = new javax.swing.JLabel();
        inputNoTelp = new swing.component.TextFieldInput();
        jLabel4 = new javax.swing.JLabel();
        inputAlamat = new swing.component.TextFieldInput();
        jPanel7 = new javax.swing.JPanel();
        btnSave = new swing.component.ButtonRound();
        btnCancel = new swing.component.ButtonRound();
        judulUpdate = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tanggalMasukDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        roleComboBox = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        createPembeli = new javax.swing.JPanel();
        scrollPaneCreate = new javax.swing.JScrollPane();
        containerCreate = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        inputNamaLengkapTbh = new swing.component.TextFieldInput();
        jLabel6 = new javax.swing.JLabel();
        inputNoTelpTbh = new swing.component.TextFieldInput();
        jLabel7 = new javax.swing.JLabel();
        inputAlamatTbh = new swing.component.TextFieldInput();
        jLabel8 = new javax.swing.JLabel();
        inputUsernameTbh = new swing.component.TextFieldInput();
        jLabel9 = new javax.swing.JLabel();
        inputPasswordTbh = new swing.component.TextFieldInput();
        judulCreate = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnSaveTbh = new swing.component.ButtonRound();
        btnCancelTbh = new swing.component.ButtonRound();
        btnBackTbh = new swing.component.ButtonOutLine();
        makeStaff = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnBackMS = new swing.component.ButtonOutLine();
        jPanel13 = new javax.swing.JPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(684, 652));

        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new java.awt.Dimension(684, 652));
        cardPanel.setLayout(new java.awt.CardLayout());

        readStaff.setOpaque(false);

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

        javax.swing.GroupLayout readStaffLayout = new javax.swing.GroupLayout(readStaff);
        readStaff.setLayout(readStaffLayout);
        readStaffLayout.setHorizontalGroup(
            readStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readStaffLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(readStaffLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(readStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(readStaffLayout.createSequentialGroup()
                        .addComponent(judulForm)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(readStaffLayout.createSequentialGroup()
                        .addGroup(readStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                        .addGap(30, 30, 30))))
        );
        readStaffLayout.setVerticalGroup(
            readStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readStaffLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(judulForm)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        cardPanel.add(readStaff, "read");

        updateStaff.setOpaque(false);

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

        panelComponentUpdate.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setForeground(cp.getColor(0)
        );
        jLabel2.setText("Nama Lengkap");

        inputNamaLengkap.setHint("John Doe");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setForeground(cp.getColor(0)
        );
        jLabel3.setText("Nomor Telepon");

        inputNoTelp.setHint("081201389442");
        inputNoTelp.setName(""); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setForeground(cp.getColor(0)
        );
        jLabel4.setText("Alamat");

        inputAlamat.setHint("Jl. Babarsari No.19");

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
                .addContainerGap(17, Short.MAX_VALUE))
        );

        judulUpdate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulUpdate.setForeground(cp.getColor(0)
        );
        judulUpdate.setText("Update Data Pembeli");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel12.setForeground(cp.getColor(0)
        );
        jLabel12.setText("Role");

        tanggalMasukDateChooser.setBackground(new java.awt.Color(255, 255, 255));
        tanggalMasukDateChooser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tanggalMasukDateChooser.setForeground(new java.awt.Color(0, 0, 0));
        tanggalMasukDateChooser.setDateFormatString("dd MMMM yyyy");
        tanggalMasukDateChooser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tanggalMasukDateChooser.setMaxSelectableDate(new java.util.Date(253370743293000L));
        tanggalMasukDateChooser.setOpaque(false);
        tanggalMasukDateChooser.setPreferredSize(new java.awt.Dimension(88, 42));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel13.setForeground(cp.getColor(0)
        );
        jLabel13.setText("Tanggal Masuk");

        roleComboBox.setBackground(new java.awt.Color(240, 240, 240));
        roleComboBox.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        roleComboBox.setForeground(new java.awt.Color(122, 140, 141));
        roleComboBox.setBorder(null);
        roleComboBox.setPreferredSize(new java.awt.Dimension(72, 42));

        javax.swing.GroupLayout panelComponentUpdateLayout = new javax.swing.GroupLayout(panelComponentUpdate);
        panelComponentUpdate.setLayout(panelComponentUpdateLayout);
        panelComponentUpdateLayout.setHorizontalGroup(
            panelComponentUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelComponentUpdateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelComponentUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComponentUpdateLayout.createSequentialGroup()
                        .addGroup(panelComponentUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(491, Short.MAX_VALUE))
                    .addGroup(panelComponentUpdateLayout.createSequentialGroup()
                        .addGroup(panelComponentUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputNamaLengkap, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(inputNoTelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelComponentUpdateLayout.createSequentialGroup()
                        .addGroup(panelComponentUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tanggalMasukDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(judulUpdate)
                            .addComponent(roleComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelComponentUpdateLayout.setVerticalGroup(
            panelComponentUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComponentUpdateLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(judulUpdate)
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNamaLengkap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(1, 1, 1)
                .addComponent(tanggalMasukDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
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

        javax.swing.GroupLayout updateStaffLayout = new javax.swing.GroupLayout(updateStaff);
        updateStaff.setLayout(updateStaffLayout);
        updateStaffLayout.setHorizontalGroup(
            updateStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateStaffLayout.createSequentialGroup()
                .addGroup(updateStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, updateStaffLayout.createSequentialGroup()
                        .addComponent(panelComponentUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        updateStaffLayout.setVerticalGroup(
            updateStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateStaffLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(updateStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelComponentUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        cardPanel.add(updateStaff, "update");

        createPembeli.setOpaque(false);

        scrollPaneCreate.setBackground(new java.awt.Color(255, 255, 255));
        scrollPaneCreate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        scrollPaneCreate.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCreate.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneCreate.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setForeground(cp.getColor(0)
        );
        jLabel5.setText("Nama Lengkap");

        inputNamaLengkapTbh.setHint("John Doe");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setForeground(cp.getColor(0)
        );
        jLabel6.setText("Nomor Telepon");

        inputNoTelpTbh.setHint("081201389442");
        inputNoTelpTbh.setName(""); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setForeground(cp.getColor(0)
        );
        jLabel7.setText("Alamat");

        inputAlamatTbh.setHint("Jl. Babarsari No.19");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setForeground(cp.getColor(0)
        );
        jLabel8.setText("Username");

        inputUsernameTbh.setHint("akuganteng123");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel9.setForeground(cp.getColor(0)
        );
        jLabel9.setText("Password");

        inputPasswordTbh.setHint("4TM@JAY^");

        judulCreate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        judulCreate.setForeground(cp.getColor(0)
        );
        judulCreate.setText("Tambah Data Pembeli");

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
                .addContainerGap(461, Short.MAX_VALUE))
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
                .addGap(25, 25, 25)
                .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addComponent(judulCreate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(containerCreateLayout.createSequentialGroup()
                        .addGroup(containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputNamaLengkapTbh, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                            .addComponent(inputNoTelpTbh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputAlamatTbh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputUsernameTbh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputPasswordTbh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackTbh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        containerCreateLayout.setVerticalGroup(
            containerCreateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerCreateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(judulCreate)
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNamaLengkapTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNoTelpTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputAlamatTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputUsernameTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPasswordTbh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        scrollPaneCreate.setViewportView(containerCreate);

        javax.swing.GroupLayout createPembeliLayout = new javax.swing.GroupLayout(createPembeli);
        createPembeli.setLayout(createPembeliLayout);
        createPembeliLayout.setHorizontalGroup(
            createPembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        createPembeliLayout.setVerticalGroup(
            createPembeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
        );

        cardPanel.add(createPembeli, "create");

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
                        .addGap(616, 616, 616)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        makeStaffLayout.setVerticalGroup(
            makeStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makeStaffLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private swing.component.ButtonRound btnCancelTbh;
    private swing.component.ButtonRound btnSave;
    private swing.component.ButtonRound btnSaveTbh;
    private swing.component.ButtonRectangle btnTambah;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel containerCreate;
    private javax.swing.JPanel createPembeli;
    private swing.component.CustomTable customTable;
    private swing.component.TextFieldWithBackground fieldSearch;
    private swing.component.TextFieldInput inputAlamat;
    private swing.component.TextFieldInput inputAlamatTbh;
    private swing.component.TextFieldInput inputNamaLengkap;
    private swing.component.TextFieldInput inputNamaLengkapTbh;
    private swing.component.TextFieldInput inputNoTelp;
    private swing.component.TextFieldInput inputNoTelpTbh;
    private swing.component.TextFieldInput inputPasswordTbh;
    private swing.component.TextFieldInput inputUsernameTbh;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel judulCreate;
    private javax.swing.JLabel judulDataPilihan;
    private javax.swing.JLabel judulForm;
    private javax.swing.JLabel judulUpdate;
    private javax.swing.JPanel makeStaff;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelComponentUpdate;
    private javax.swing.JPanel readStaff;
    private javax.swing.JComboBox<Role> roleComboBox;
    private javax.swing.JScrollPane scrollPaneCreate;
    private com.toedter.calendar.JDateChooser tanggalMasukDateChooser;
    private javax.swing.JPanel updateStaff;
    // End of variables declaration//GEN-END:variables
}
