/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table;

/**
 *
 * @author ASUS
 */

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Staff;

public class StaffTable extends AbstractTableModel{
    private List<Staff> staff;

    public StaffTable(List<Staff> staff) {
        this.staff = staff;
    }
    
    public int getRowCount(){
        return staff.size();
    }
    
    public int getColumnCount(){
        return 7;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex){
        switch(columnIndex){
            case 0:
                return staff.get(rowIndex).getNIP();
            case 1:
                return staff.get(rowIndex).getNama();
            case 2:
                return staff.get(rowIndex).getTahunMasuk();
            case 3:
                return staff.get(rowIndex).getNoTelp();
            case 4:
                return staff.get(rowIndex).getAlamat();
            case 5:
                return staff.get(rowIndex).getRole().getNamaRole();
            case 6:
                return staff.get(rowIndex).getUser().getUsername();
            case 7:
                return staff.get(rowIndex).getUser().getPassword();
            case 8:
                return staff.get(rowIndex);
            default:
                return null;
        }
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "NIP";
            case 1:
                return "Nama";
            case 2:
                return "Tahun Masuk";
            case 3:
                return "Nomor Telepon";
            case 4:
                return "Alamat";
            case 5:
                return "Role";
            case 6:
                return "Username";
            case 7:
                return "Password";
            default:
                return null;
        }
    }
}
