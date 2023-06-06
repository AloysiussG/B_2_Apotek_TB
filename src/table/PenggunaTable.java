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
import model.Pengguna;

public class PenggunaTable extends AbstractTableModel {
    private List<Pengguna> pengguna;

    public PenggunaTable(List<Pengguna> pengguna) {
        this.pengguna = pengguna;
    }
    
    
    public int getRowCount(){
        return pengguna.size();
    }
    
    
    public int getColumnCount(){
        return 5;
    }
    
    
    public Object getValueAt(int rowIndex, int columnIndex){
        switch(columnIndex){
            case 0:
                return pengguna.get(rowIndex).getIdUser();
            case 1:
                return pengguna.get(rowIndex).getNama();
            case 2:
                return pengguna.get(rowIndex).getAlamat();
            case 3: 
                return pengguna.get(rowIndex).getNoTelp();
            case 4:
                return pengguna.get(rowIndex).getUser().getUsername();
            case 5:
                return pengguna.get(rowIndex).getUser().getPassword();
            default:
                return null;
        }
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "ID User";
            case 1:
                return "Nama";
            case 2: 
                return "Alamat";
            case 3:
                return "Nomor Telepon";          
            case 4:
                return "Username";
            case 5:
                return "Password";
            default:
                return null;
        }
    }
}
