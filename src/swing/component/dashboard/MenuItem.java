/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package swing.component.dashboard;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;
import swing.ColorPallete;
import swing.events.EventMenu;
import swing.events.EventMenuSelected;
import swing.model.DashboardModelMenu;

/**
 *
 * @author AG SETO GALIH D
 */
public class MenuItem extends javax.swing.JPanel {

    //kelas untuk membuat setiap menu item beserta submenunya
    //yang akan ditempatkan di dashboard panel bagian kiri jframe
    private static ColorPallete cp = new ColorPallete();

    private float alpha;
    private DashboardModelMenu modelMenu;
    private boolean open;
    private EventMenuSelected eventSelected;
    private int index;

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public DashboardModelMenu getModelMenu() {
        return modelMenu;
    }

    public void setModelMenu(DashboardModelMenu modelMenu) {
        this.modelMenu = modelMenu;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public EventMenuSelected getEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(EventMenuSelected eventSelected) {
        this.eventSelected = eventSelected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /** Creates new form MenuItem */
    public MenuItem(DashboardModelMenu modelMenu, EventMenu event, EventMenuSelected eventSelected, int index) {
        initComponents();
        this.modelMenu = modelMenu;
        this.eventSelected = eventSelected;
        //menu index
        this.index = index;
        setOpaque(false);
        setLayout(new MigLayout("wrap, fillX, insets 0", "[fill]", "[fill, 40!]0[fill, 35!]"));
        MenuButton firstItem = new MenuButton(modelMenu.getIcon(), "    " + modelMenu.getMenuName());
        //action listener ketika menu dipencet untuk mengeluarkan submenu
        firstItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (modelMenu.getSubmenu().length > 0) {
                    //default sebelum dipencet adalah close/false (!open)
                    if (event.menuPressed(MenuItem.this, !open)) {
                        //untuk membalikkan kondisi open close ketika dipencet
                        open = !open;
                    }
                    System.out.println("Menampilkan/menghide sumenu pada menu index ke " + index);
                }
                eventSelected.menuSelected(index, -1);
            }
        });
        add(firstItem);
        //inisiasi submenu index dari sebuah menu index
        int submenuIndex = -1;

        for (String menuName : modelMenu.getSubmenu()) {
            MenuButton item = new MenuButton(menuName);
            item.setIndex(++submenuIndex);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    //kirim eventmenuselected ke main jframe view
                    eventSelected.menuSelected(index, item.getIndex());
                }
            });
            add(item);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getPreferredSize().height;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //mewarnai menu ketika dipencet
        g2.setColor(cp.getColor(0));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 0, width, 40);
        g2.setComposite(AlphaComposite.SrcOver);
        //mewarnai submenu + membuat garis submenu ketika menu utamanya dipencet
        g2.setColor(cp.getColor(1));
        g2.fillRect(0, 40, width, height - 40);
        g2.setColor(cp.getColor(0));
        g2.drawLine(30, 40, 30, height - 17);
        for (int i = 0; i < modelMenu.getSubmenu().length; i++) {
            int y = ((i + 1) * 35 + 40) - 17;
            g2.drawLine(30, y, 40, y);
        }
        //membuat arrow button untuk menu jika memiliki submenu dibawahnya
        if (modelMenu.getSubmenu().length > 0) {
            createArrowButton(g2);
        }
        super.paintComponent(g);
    }

    //fungsi untuk membuat arrow button menu berdasarkan alpha value
    private void createArrowButton(Graphics2D g2) {
        int size = 4;
        int y = 19;
        int x = 205;
        g2.setColor(new Color(230, 230, 230));
        float ay = alpha * size;
        float ay1 = (1f - alpha) * size;
        g2.drawLine(x, (int) (y + ay), x + 4, (int) (y + ay1));
        g2.drawLine(x + 4, (int) (y + ay1), x + 8, (int) (y + ay));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 213, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
