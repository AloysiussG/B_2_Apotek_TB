/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gregory Wilson
 */
public class Pengguna {
    private int idUser, idPengguna;
    private String nama, noTelp, alamat;
    private User user;

    public Pengguna(int idUser, int idPengguna, String nama, String noTelp, String alamat, User user) {
        this.idUser = idUser;
        this.idPengguna = idPengguna;
        this.nama = nama;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public int getIdPengguna() {
        return idPengguna;
    }
    
    public String getNama() {
        return nama;
    }
    
    public String getNoTelp() {
        return noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public String showData(){        
            return
                    "Id Pengguna :"+ this.idPengguna + "\n" +
                    "Id User :"+ this.idUser + "\n" +
                    "Nama : "+ this.nama + "\n" +
                    "Alamat : "+ this.alamat + "\n" +
                    "No Telepon : "+ this.noTelp + "\n";        
    }
}
