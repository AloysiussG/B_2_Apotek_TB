/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import control.PenggunaControl;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import panel.DashboardMainPanel;
import panel.superadmin.PembeliForm;
import panel.superadmin.ReadDataPembeli;
import panel.superadmin.UpdateDataPembeli;
import swing.component.dashboard.Header;
import swing.component.dashboard.Menu;
import swing.events.EventMenuPanelResize;
import swing.events.EventMenuSelected;

/**
 *
 * @author AG SETO GALIH D
 */
public class SuperAdminView extends javax.swing.JFrame {

    private PenggunaControl penggunaControl;

    private ReadDataPembeli read;
    private UpdateDataPembeli update;

    private MigLayout layout;
    private Menu menu;
    private Header header;
    private DashboardMainPanel mainPanel;
    private Animator animator;

    /** Creates new form SuperAdminView */
    public SuperAdminView() {
        //inisialisasi variabel (disarankan berada diatas init components)
        penggunaControl = new PenggunaControl();

        //inisialisasi komponen
        initComponents();
        initSuperAdminView();

    }

    private void initSuperAdminView() {

        //membuat layout bertipe miglayout
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0");
        background.setLayout(layout);
        //buat objek komponen lain
        menu = new Menu();
        header = new Header();
        mainPanel = new DashboardMainPanel();
        menu.addEventSelected(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int submenuIndex) {
                System.out.println("Menu index " + menuIndex + " | Submenu index " + submenuIndex);
                if (menuIndex == 0) {
                    if (submenuIndex == 0) {
                        //show pembeli
                        //masih laggy karena select dari database ga berjalan di background
                        //mainPanel.showForm(new ReadDataPembeli(penggunaControl.showDataPengguna("")));
                    }
                }
            }
        });
        //set menu panel kiri dengan beberapa menu item khusus untuk superadmin
        menu.initMenuItemForSuperAdmin();
        //add component ke background
        background.add(menu, "w 230!, spanY 2"); //span Y == 2 cells
        background.add(header, "h 50!, wrap");
        background.add(mainPanel, "w 100%, h 100%");
        //animasi untuk hide show dashboard menu di kiri
        TimingTarget target = new TimingTargetAdapter() {

            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 62 + (170 * (1f - fraction));
                } else {
                    width = 62 + (170 * (fraction));
                }
                menu.revalidate();
                layout.setComponentConstraints(menu, "w " + width + "!, spanY 2");
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addHamburgerMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                menu.setEnableMenu(false);
                if (menu.isShowMenu()) {
                    menu.hideAllSubmenu();
                }
            }
        });
        menu.addEventResize(new EventMenuPanelResize() {
            @Override
            public void resizeMenuPanel() {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
        //saat app dijalankan, mulai dengan menampilkan panel form ini
        //view read data pembeli (view table dll)
        mainPanel.showForm(new PembeliForm());
        //
//        //update = new UpdateDataPembeli(pengguna);
//        read = new ReadDataPembeli(penggunaControl.showDataPengguna(""), "Pembeli");
//        read.callEvent(new EventUserCard() {
//            @Override
//            //untuk pengguna/pembeli
//            public void runEvent(Pengguna pengguna) {
//                setPengguna(pengguna);
//                //jika menekan tombol edit di user card, akan diarahkan ke panel edit/update
////                update = new UpdateDataPembeli(pengguna);
////                update.addBtnBackActionListener(new ActionListener() {
////                    @Override
////                    public void actionPerformed(ActionEvent arg0) {
////                        //balik ke read lagi
////                    }
////                });
////                mainPanel.showForm(update);
//                System.out.println("Run");
//            }
//
//            //untuk staff
//            @Override
//            public void runEvent(Staff staff) {
//            }
//
//            @Override
//            public void runEvent() {
//            }
//
//        });

//        update.addBtnBackActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                //balik ke read lagi
//            }
//        });
        //view update data pembeli
//        mainPanel.showForm(new UpdateDataPembeli());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setOpaque(true);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 967, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 629, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SuperAdminView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane background;
    // End of variables declaration//GEN-END:variables
}
