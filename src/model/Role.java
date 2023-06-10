/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gregory Wilson
 */
public class Role {

    private int idRole;
    private double gaji;
    private String namaRole;

    public Role(int idRole, double gaji, String namaRole) {
        this.idRole = idRole;
        this.gaji = gaji;
        this.namaRole = namaRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public double getGaji() {
        return gaji;
    }

    public String getNamaRole() {
        return namaRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setGaji(double gaji) {
        this.gaji = gaji;
    }

    public void setNamaRole(String namaRole) {
        this.namaRole = namaRole;
    }

    //untuk dropdown combo box Role
    @Override
    public String toString() {
        return namaRole;
    }
}
