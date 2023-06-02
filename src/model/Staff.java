/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gregory Wilson
 */
public class Staff {
    private int NIP, idRole, idUser;
    private String nama, tahunMasuk, noTelp, alamat;
    private Role role;
    private User user;

    public Staff(int NIP, int idRole, int idUser, String nama, String tahunMasuk, String noTelp, String alamat, Role role, User user) {
        this.NIP = NIP;
        this.idRole = idRole;
        this.idUser = idUser;
        this.nama = nama;
        this.tahunMasuk = tahunMasuk;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.role = role;
        this.user = user;
    }

    public Staff(int NIP, String nama, String tahunMasuk, String noTelp, String alamat, Role r, User u) {
        this.NIP = NIP;
        this.nama = nama;
        this.tahunMasuk = tahunMasuk;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.role = role;
        this.user = user;
    }

    public int getIdUser() {
        return idUser;
    }

    public Role getRole() {
        return role;
    }

    public User getUser() {
        return user;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNIP() {
        return NIP;
    }

    public int getIdRole() {
        return idRole;
    }

    public String getNama() {
        return nama;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }    
}
