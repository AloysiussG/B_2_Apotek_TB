/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing.component.dashboard;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import swing.component.scrollbar.ScrollBarCustom;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import net.miginfocom.swing.MigLayout;
import swing.ColorPallete;
import swing.events.EventMenu;
import swing.events.EventMenuPanelResize;
import swing.events.EventMenuSelected;
import swing.model.DashboardModelMenu;

/**
 *
 * @author AG SETO GALIH D
 */
public class Menu extends javax.swing.JPanel {

    //kelas menu adalah dashboard panel di bagian kiri jframe
    private MigLayout layout;
    private EventMenuSelected eventSelected;

    private EventMenuPanelResize eventResize;
    private boolean enableMenu = true;
    private boolean showMenu = true;

    private static ColorPallete colorPallete = new ColorPallete();

    private Color color1;
    private Color color2;

    private final String iconDir = "img/icon/";

    public Menu(String title) {
        initComponents();

        //agar panel menjadi transaparan di settingan background colornya
        //supaya dapat ditimpa/dioverride dengan warna gradient buatan sendiri
        setOpaque(false);

        //mendefine variabel warna gradient
        color1 = colorPallete.getColor(1);
        color2 = colorPallete.getColor(0);

        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBar(new ScrollBarCustom());

        layout = new MigLayout("wrap, fillX, insets 0", "[fill]", "[]0[]");
        panelMenu.setLayout(layout);

        titleAndIcon.setIcon(new FlatSVGIcon("img/logo/logo-white.svg", 0.05f));
        titleAndIcon.setIconTextGap(12);
        titleAndIcon.setFont(new Font("Segoe UI", 1, 18));
        titleAndIcon.setText("Tumbuh Bersama");

        separator.setForeground(new Color(255, 255, 255, 150));
        separator.setVisible(false);
    }

    public void initMenuItemForSuperAdmin() {
        //untuk menambahkan beberapa panel menu item ke dalam panel menu
        int indexMenu = 0;

        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "manage_account.svg", 0.5f), "Kelola Data", new String[]{"Data Pengguna", "Data Staff"}));
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "logout-white.svg", 0.5f), "Logout", new String[]{}));
        //addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "home-white.svg", 0.5f), "User Login Credentials", new String[]{}));
    }

    public void initMenuItemForKasir() {
        int indexMenu = 0;
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "receipt.svg", 0.5f), "Kelola Data Transaksi", new String[]{}));
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "logout-white.svg", 0.5f), "Logout", new String[]{}));
    }

    public void initMenuItemForKepalaGudang() {
        int indexMenu = 0;
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "inventory.svg", 0.5f), "Kelola Pengadaan Obat", new String[]{}));
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "logout-white.svg", 0.5f), "Logout", new String[]{}));
    }

    public void initMenuItemForApoteker() {
        int indexMenu = 0;
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "medicine.svg", 0.5f), "Kelola Data Obat", new String[]{}));
        addMenuItem(indexMenu++, new DashboardModelMenu(new FlatSVGIcon(iconDir + "logout-white.svg", 0.5f), "Logout", new String[]{}));
    }

    private void addMenuItem(int indexMenu, DashboardModelMenu modelMenu) {
        //method untuk menambahkan panel menu item ke dalam panel menu, satu per satu
        panelMenu.add(new MenuItem(modelMenu, getEventMenu(), eventSelected, indexMenu), "h 40!");
    }

    private EventMenu getEventMenu() {
        return new EventMenu() {
            @Override
            public boolean menuPressed(Component c, boolean open) {
                if (enableMenu) {
                    if (showMenu) {
                        if (open) {
                            new MenuAnimation(layout, c).openMenu();
                        } else {
                            new MenuAnimation(layout, c).closeMenu();
                        }
                        return true;
                    } else {
                        if (open) {
                            new MenuAnimation(layout, c).openMenu();
                            eventResize.resizeMenuPanel();
                            return true;
                            //untuk meresize kembali panel dashboard di jframe
                        }
                        //System.out.println("Show pop up menu is close");
                    }
                }
                return false;
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {

        //mendefine variabel width dan height panel
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;

        //memberi efek antialiasing pada gradient yang akan dibuat
        //agar transisi warna gradient lebih smooth
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //membuat objek GradientPaint dan mengeset warna gradient ke panel
        //parameter: startX, startY, warna1, endX, endY, warna2
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, width, 0, color2);
        g2.setPaint(gradientPaint);

        //untuk memberi border radius
        //parameter: startX, startY, endX, endY, borderArcWidth, borderArcHeight
        int borderRadius = 0;
        g2.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);

        //menghapus border radius sebelah kanan dengan membuat rectangle baru yang menutupi
        //parameter: startX, startY, endX, endY
        //g2.fillRect(width - 105, 0, width, height);
        //g2.fillRect(width, 0, width - 105, height);
        //g2.dispose();
        super.paintComponent(g);
    }

    public void hideAllSubmenu() {
        for (Component com : panelMenu.getComponents()) {
            MenuItem item = (MenuItem) com;
            if (item.isOpen()) {
                new MenuAnimation(layout, com, 300).closeMenu();
                item.setOpen(false);
            }
        }
    }

    //getter
    public boolean isShowMenu() {
        return showMenu;
    }

    //setter
    public void addEventResize(EventMenuPanelResize eventResize) {
        this.eventResize = eventResize;
    }

    public void addEventSelected(EventMenuSelected eventSelected) {
        this.eventSelected = eventSelected;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }

    public void setShowMenu(boolean showMenu) {
        this.showMenu = showMenu;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profileHeaderPanel = new javax.swing.JPanel();
        titleAndIcon = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();
        scrollPane = new javax.swing.JScrollPane();
        panelMenu = new javax.swing.JPanel();

        profileHeaderPanel.setBackground(new java.awt.Color(0, 0, 0));
        profileHeaderPanel.setOpaque(false);

        titleAndIcon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        titleAndIcon.setForeground(new java.awt.Color(255, 255, 255));
        titleAndIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleAndIcon.setText("Super Admin");

        javax.swing.GroupLayout profileHeaderPanelLayout = new javax.swing.GroupLayout(profileHeaderPanel);
        profileHeaderPanel.setLayout(profileHeaderPanelLayout);
        profileHeaderPanelLayout.setHorizontalGroup(
            profileHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileHeaderPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(titleAndIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(72, 72, 72))
            .addComponent(separator)
        );
        profileHeaderPanelLayout.setVerticalGroup(
            profileHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileHeaderPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(titleAndIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);

        panelMenu.setOpaque(false);

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(panelMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
            .addComponent(profileHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(profileHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPane))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel profileHeaderPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSeparator separator;
    private javax.swing.JLabel titleAndIcon;
    // End of variables declaration//GEN-END:variables
}
