/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author ASUS
 */
import model.Obat;
import java.util.List;
import dao.ObatDAO;
import table.ObatTable;

public class ObatControl {

    private ObatDAO oDao = new ObatDAO();

    public void insertDataObat(Obat obat) {
        oDao.insertObat(obat);
    }

    public ObatTable showDataObat(String query) {
        List<Obat> dataObat = oDao.showObat(query);
        ObatTable to = new ObatTable(dataObat);

        return to;
    }

    public List<Obat> showDataObatAsList(String query) {
        List<Obat> data = oDao.showObat(query);
        return data;
    }

    public void updateDataObat(int id, Obat obat) {
        oDao.updateObat(id, obat);
    }

    public void deleteDataObat(int id) {
        oDao.deleteObat(id);
    }

    //tambahan
    public int findDataKuantitasObat(String namaObat) {
        return oDao.findKuantitasObat(namaObat);
    }

    public List<Obat> showListObat() {
        List<Obat> dataObat = oDao.showObatDropdown();
        return dataObat;
    }
}
