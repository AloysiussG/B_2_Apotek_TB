/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Gregory Wilson
 */
public class PengadaanObat {
    private int idPengadaan, kuantitas, idObat;
    private String supplier, tanggalPengadaan;
    private Obat obat;

    public PengadaanObat(int idPengadaan, int kuantitas, int idObat, String supplier, String tanggalPengadaan, Obat obat) {
        this.idPengadaan = idPengadaan;
        this.kuantitas = kuantitas;
        this.idObat = idObat;
        this.supplier = supplier;
        this.tanggalPengadaan = tanggalPengadaan;
        this.obat = obat;
    }

    public Obat getObat() {
        return obat;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public void setObat(Obat obat) {
        this.obat = obat;
    }

    public int getIdPengadaan() {
        return idPengadaan;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public int getIdObat() {
        return idObat;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getTanggalPengadaan() {
        return tanggalPengadaan;
    }

    public void setIdPengadaan(int idPengadaan) {
        this.idPengadaan = idPengadaan;
    }

    public void setQuantity(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setTanggalPengadaan(String tanggalPengadaan) {
        this.tanggalPengadaan = tanggalPengadaan;
    }
    
    
}
