/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing.component.dashboard;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionListener;
import swing.ColorPallete;

/**
 *
 * @author AG SETO GALIH D
 */
public class Header extends javax.swing.JPanel {

    private static ColorPallete cp = new ColorPallete();

    //header adalah bagian atas pada body main panel
    public Header(String username, String role) {
        initComponents();
        lblSelamatDatang.setForeground(cp.getColor(0));
        lblSelamatDatang.setText("Welcome, " + username);
        lblRole.setForeground(cp.getColor(0));
        lblRole.setText(role);

        lblIcon.setText("");
        lblIcon.setIcon(new FlatSVGIcon("img/icon/account.svg", 0.9f));
        hamburgerMenuBtn.setIcon(new FlatSVGIcon("img/icon/hamburgermenu.svg", 0.5f));
    }

    public void addHamburgerMenuEvent(ActionListener event) {
        hamburgerMenuBtn.addActionListener(event);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hamburgerMenuBtn = new swing.component.ButtonRectangle();
        lblSelamatDatang = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 240));

        lblSelamatDatang.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lblSelamatDatang.setText("Selamat datang, <nama>");

        lblRole.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblRole.setForeground(new java.awt.Color(200, 200, 200));
        lblRole.setText("<Role>");

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setText("aaa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hamburgerMenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelamatDatang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRole, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(13, 13, 13)
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hamburgerMenuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSelamatDatang)
                        .addGap(0, 0, 0)
                        .addComponent(lblRole))
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.component.ButtonRectangle hamburgerMenuBtn;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSelamatDatang;
    // End of variables declaration//GEN-END:variables
}
