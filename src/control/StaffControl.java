package Control;

import model.Staff;
import java.util.List;
import model.User;
import dao.StaffDAO;
import table.StaffTable;

public class StaffControl {
    private StaffDAO sd = new StaffDAO();
    
    public void insertDataStaff(Staff s,User u){
        sd.insertStaff(s, u);
    }
    
    public StaffTable showDataStaff(String query){
        List<Staff> dataStaff = sd.showStaff(query);
        StaffTable tabel = new StaffTable(dataStaff);
        
        return tabel;
    }
    
    public void updateDataStaff(Staff s){
        sd.updateStaff(s);
    }
    
    public void deleteDataStaff(int id){
        sd.deleteStaf(id);
    }
    
    
}
