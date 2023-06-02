/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gregory Wilson
 */
public class Transaksi {
    private int idTransaksi, idObat, idPengguna, jumlah, NIP;
    private String tanggalPembelian;
    private Staff staff;
    private Obat obat;
    private Pengguna pengguna;

    public Transaksi(int idTransaksi, int idObat, int idPengguna, int jumlah, int NIP, String tanggalPembelian, Staff staff, Obat obat, Pengguna pengguna) {
        this.idTransaksi = idTransaksi;
        this.idObat = idObat;
        this.idPengguna = idPengguna;
        this.jumlah = jumlah;
        this.NIP = NIP;
        this.tanggalPembelian = tanggalPembelian;
        this.staff = staff;
        this.obat = obat;
        this.pengguna = pengguna;
    }

    public Transaksi(int idTransaksi, int jumlah, String tanggalPembelian, Staff staff, Obat obat, Pengguna pengguna) {
        this.idTransaksi = idTransaksi;
        this.jumlah = jumlah;
        this.tanggalPembelian = tanggalPembelian;
        this.staff = staff;
        this.obat = obat;
        this.pengguna = pengguna;
    }

    public Staff getStaff() {
        return staff;
    }

    public Obat getObat() {
        return obat;
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setObat(Obat obat) {
        this.obat = obat;
    }

    public void setPengguna(Pengguna pengguna) {
        this.pengguna = pengguna;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public int getIdObat() {
        return idObat;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getTanggalPembelian() {
        return tanggalPembelian;
    }

    public int getNIP() {
        return NIP;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setTanggalPembelian(String tanggalPembelian) {
        this.tanggalPembelian = tanggalPembelian;
    }

    public void setNIP(int NIP) {
        this.NIP = NIP;
    }
    
    public double totalPembelian(){
        return obat.getHarga()*jumlah;
    }
}
