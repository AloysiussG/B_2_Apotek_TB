/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dao.RoleDAO;
import java.util.List;
import model.Role;

/**
 *
 * @author AG SETO GALIH D
 */
public class RoleControl {

    private RoleDAO pm = new RoleDAO();

    public List<Role> showRole() {
        return pm.showRole();
    }
}
