/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import model.PengadaanObat;
/**
 *
 * @author willi
 */
public class PengadaanObatTable extends AbstractTableModel {
    private List<PengadaanObat> list;

    public PengadaanObatTable(List<PengadaanObat> list) {
        this.list = list;
    }
    
    public int getRowCount(){
        return list.size();
    }
    
    public int getColumnCount(){
        return 6;
    }
    
    public Object getValueAt (int rowIndex, int columnIndex){
        switch (columnIndex){
            case 0:
                return list.get(rowIndex).getIdPengadaan();
            case 1:
                return list.get(rowIndex).getObat().getNamaObat();
            case 2:
                return list.get(rowIndex).getStaff().getNama();
            case 3:
                return list.get(rowIndex).getKuantitas();
            case 4:
                return list.get(rowIndex).getSupplier();
            case 5:
                return list.get(rowIndex).getTanggalPengadaan();
            case 6:
                return list.get(rowIndex);
            default :
                return null;
        }
    }
    
    public String getColumnName (int column) {
        switch(column) {
            case 0:
                return "ID Pengadaan";
            case 1:
                return "Nama Obat";
            case 2:
                return "Nama Staff";
            case 3:
                return "Kuantitas";
            case 4:
                return "Supplier";
            case 5:
                return "Tanggal Pengadaan";
            default:
                return null;
        }
    }
    
}
