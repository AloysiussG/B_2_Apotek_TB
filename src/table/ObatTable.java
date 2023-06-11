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
import model.Obat;

public class ObatTable extends AbstractTableModel{
    private List<Obat> obat;

    public ObatTable(List<Obat> obat) {
        this.obat = obat;
    }
    
    public int getRowCount(){
        return obat.size();
    }
    
    public int getColumnCount(){
        return 5;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex){
        switch(columnIndex){
            case 0:
                return obat.get(rowIndex).getIdObat();
            case 1:
                return obat.get(rowIndex).getNamaObat();
            case 2:
                return obat.get(rowIndex).getKuantitas();
            case 3:
                return obat.get(rowIndex).getTanggalProduksi();
            case 4:
                return obat.get(rowIndex).getTanggalKadaluarsa();
            case 5:
                return obat.get(rowIndex).getHarga();
            case 6:
                return obat.get(rowIndex);
            default:
                return null;
        }
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "ID Obat";
            case 1:
                return "Nama Obat";
            case 2:
                return "Kuantitas";
            case 3:
                return "Tanggal Produksi";
            case 4:
                return "Tanggal Kadaluarsa";
            case 5:
                return "Harga";
            default:
                return null;
        }
    }
}
