/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing.component;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import swing.ColorPallete;

/**
 *
 * @author AG SETO GALIH D
 */
public class CustomTable extends JTable {

    public static ColorPallete cp = new ColorPallete();

    public CustomTable() {
        setShowHorizontalLines(true);
        setGridColor(new Color(210, 210, 210));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                CustomTableHeader header = new CustomTableHeader(value + "");
                header.setHorizontalAlignment(JLabel.CENTER);
//                header.setBackground(cp.getColor(3));
                return header;
            }

        });
    }

}
