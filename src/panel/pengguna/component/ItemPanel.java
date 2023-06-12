/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panel.pengguna.component;

import java.awt.Color;
import javax.swing.JPanel;
import panel.pengguna.layout.WrapLayout;

/**
 *
 * @author AG SETO GALIH D
 */
public class ItemPanel extends JPanel {

    public ItemPanel() {
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
    }
}
