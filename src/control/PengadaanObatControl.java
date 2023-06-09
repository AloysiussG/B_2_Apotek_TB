/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dao.PengadaanObatDAO;
import java.util.List;
import model.PengadaanObat;
import table.PengadaanObatTable;

/**
 *
 * @author willi
 */
public class PengadaanObatControl {

    private PengadaanObatDAO PODao = new PengadaanObatDAO();

    public void insertPengadaanObat(PengadaanObat po) {
        PODao.insertPengadaanObat(po);
    }

    public PengadaanObatTable showPengadaanObat(String query) {
        List<PengadaanObat> dataPengadaanObat = PODao.showPengadaanObat(query);
        PengadaanObatTable tablePengadaanObat = new PengadaanObatTable(dataPengadaanObat);
        return tablePengadaanObat;
    }

    public void updatePengadaanObat(PengadaanObat po) {
        PODao.updatePengadaanObat(po);
    }

    public void deletePengadaanObat(int id) {
        PODao.deletePengadaanObat(id);
    }
}
