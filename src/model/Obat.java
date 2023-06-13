/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Gregory Wilson
 */
public class Obat {

    private int idObat, kuantitas;
    private String namaObat, tanggalKadaluarsa, tanggalProduksi;
    private double harga;

    public Obat(int idObat, int kuantitas, String namaObat, String tanggalKadaluarsa, String tanggalProduksi, double harga) {
        this.idObat = idObat;
        this.kuantitas = kuantitas;
        this.namaObat = namaObat;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.tanggalProduksi = tanggalProduksi;
        this.harga = harga;
    }

    public int getIdObat() {
        return idObat;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public String getTanggalKadaluarsa() {
        return tanggalKadaluarsa;
    }

    public String getTanggalProduksi() {
        return tanggalProduksi;
    }

    public double getHarga() {
        return harga;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public void setTanggalKadaluarsa(String tanggalKaldaluarsa) {
        this.tanggalKadaluarsa = tanggalKaldaluarsa;
    }

    public void setTanggalProduksi(String tanggalProduksi) {
        this.tanggalProduksi = tanggalProduksi;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String showData() {
        return "Nama Obat : " + this.namaObat + "\n"
                + "Expire Date : " + this.tanggalKadaluarsa + "\n"
                + "Harga : " + this.harga + "\n"
                + "Tanggal Produksi : " + this.tanggalProduksi + "\n"
                + "Kuantitas : " + this.kuantitas + "\n";
    }

    @Override
    public String toString() {
        return namaObat;
    }
}
