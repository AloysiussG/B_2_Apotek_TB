package control;

import model.Pengguna;
import java.util.List;
import dao.PenggunaDAO;
import model.User;
import table.PenggunaTable;

public class PenggunaControl {

    private PenggunaDAO pm = new PenggunaDAO();

    public void insertPengguna(Pengguna p) {
        pm.insertPengguna(p);
    }

    public PenggunaTable showDataPengguna(String query) {
        List<Pengguna> dataPengguna = pm.showPengguna(query);
        PenggunaTable tabel = new PenggunaTable(dataPengguna);
        return tabel;
    }

    public void updateDataPengguna(Pengguna p) {
        pm.updatePengguna(p);
    }

    public void deleteDataPengguna(int id) {
        pm.deletePengguna(id);
    }

    public String returnNamePengguna(int index) {
        return pm.returnName(index);
    }

    public int findLastIdPengguna() {
        return pm.findLastId();
    }

    public Pengguna findPengguna(int idUser, User user) {
        return pm.findPengguna(idUser, user);
    }

    public void alterIncrement() {
        pm.setIncrement();
    }

}
