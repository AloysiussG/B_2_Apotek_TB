package Control;

import model.Pengguna;
import java.util.List;
import dao.PenggunaDAO;
import table.PenggunaTable;

public class PenggunaControl {
    private PenggunaDAO pm = new PenggunaDAO();
    
    public void insertPengguna(Pengguna p){
        pm.insertPengguna(p);
    }
    
    public PenggunaTable showDataPengguna(String query){
        List<Pengguna> dataPengguna= pm.showPengguna(query);
        PenggunaTable tabel = new PenggunaTable(dataPengguna);
        
        return tabel;
    }
    
    public void updateDataStaff(Pengguna p){
        pm.updatePengguna(p);
    }
    
    public void deleteDataStaff(int id){
        pm.deletePengguna(id);
    }
    
}
