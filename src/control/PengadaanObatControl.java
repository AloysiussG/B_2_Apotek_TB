/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Dao.PengadaanObatDAO;
import java.util.List;
import model.PengadaanObat;
import tabel.PengadaanObatTable;

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

    public void deleteDataRekamMedis(int id) {
        PODao.deletePengadaanObat(id);
    }
}
