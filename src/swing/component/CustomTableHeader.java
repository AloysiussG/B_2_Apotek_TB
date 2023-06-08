/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import swing.ColorPallete;

/**
 *
 * @author AG SETO GALIH D
 */
public class CustomTableHeader extends JLabel {

    public static ColorPallete cp = new ColorPallete();

    public CustomTableHeader(String text) {
        super(text);
        setOpaque(true);
        setBackground(cp.getWhite());
        setFont(new Font("sansserif", 1, 13));
        setForeground(cp.getColor(0));
        setBorder(new EmptyBorder(10, 5, 10, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.gray);
        g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

}
