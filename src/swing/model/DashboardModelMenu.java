/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package swing.model;

import javax.swing.Icon;

/**
 *
 * @author AG SETO GALIH D
 */
public class DashboardModelMenu {

    //kelas modelmenu untuk menu item di dashboard panel
    //berisi informasi/model dari tiap menu item
    private Icon icon;
    private String menuName;
    private String[] submenu;

    public DashboardModelMenu(Icon icon, String menuName, String[] submenu) {
        this.icon = icon;
        this.menuName = menuName;
        this.submenu = submenu;
    }

    public DashboardModelMenu() {
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String[] getSubmenu() {
        return submenu;
    }

    public void setSubmenu(String[] submenu) {
        this.submenu = submenu;
    }

}
