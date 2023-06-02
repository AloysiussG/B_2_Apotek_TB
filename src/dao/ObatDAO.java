/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ASUS
 */

import model.Obat;
import connection.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ObatDAO {
    private DbConnection dbCon = new DbConnection();
    private Connection con; 
    
    public List<Obat> showObat(String query){
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM obat WHERE"
                + "idObat LIKE '%" +query + "%'"
                + "OR namaObat LIKE '%" +query + "%'"
                + "OR tanggalKadaluarsa LIKE '%" +query + "%'"
                + "OR harga LIKE '%" +query + "%'"
                + "OR tanggalProduksi LIKE '%" +query + "%'"
                + "OR kuantitas LIKE '%" +query + "%'";
        
        System.out.println(sql);
        System.out.println("Mengambil Data Obat");
        
        List<Obat> list = new ArrayList();
        
        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs != null){
                while(rs.next()){
                    Obat obat = new Obat
                            (Integer.parseInt(rs.getString("idObat")),
                            Integer.parseInt(rs.getString("kuantitas")),
                            rs.getString("namaObat"),
                            rs.getString("tanggalKadaluarsa"),
                            rs.getString("tanggalProduksi"),
                            Double.parseDouble(rs.getString("harga"))
                            );
                }
            }
        }catch(Exception e){
            System.out.println("Error Reading Databse");
            e.printStackTrace();
        }
        
        dbCon.closeConnection();
        return list;
    }
    
    public void insertObat(Obat obat){
        con = dbCon.makeConnection();
        String sql = null;
        
        sql = "INSERT INTO `obat` (`idObat`, `namaObat`, `tanggalKadaluarsa`, `harga`, `tanggalProduksi`, `kuantitas`) VALUES ("
                + obat.getIdObat() + ", '" + obat.getNamaObat() + "','" + obat.getTanggalKaldaluarsa()+ "'," + obat.getHarga() + ",'" + obat.getTanggalProduksi() + "'," + obat.getKuantitas()+ ")";
        System.out.println("Adding Obat");
        
        try{
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " obat");
            statement.close();
        }catch(Exception e){
            System.out.println("Error Adding Obat");
            System.out.println(e);
        }
        
        dbCon.closeConnection();
    }
    
    public void updateObat(int id, Obat obat){
        con = dbCon.makeConnection();
        String sql = null;
        
        sql = "UPDATE `obat` SET = `namaObat` = " + obat.getNamaObat() + ", `tanggalKadaluarsa`=" + obat.getTanggalKaldaluarsa()+ ", `harga`=" + obat.getHarga() + ", `tanggalProduksi`=" + obat.getTanggalProduksi() + ", `kuantitas`=" + obat.getKuantitas()+ " WHERE `idObat` = " + id;
        System.out.println("Editing Obat");
        
        try{
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " obat");
            statement.close();
        }catch(Exception e){
            System.out.println("Error Editing Obat");
            System.out.println(e);
        }
        
        dbCon.closeConnection();
    }   
    
    public void deleteObat(int id){
        con = dbCon.makeConnection();
        String sql = null;
        
        sql = "DELETE FROM `obat` WHERE `idObat` = " + id + "";
        System.out.println("Deleting Obat");
        
        try{
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Deleted "  + result + " obat");
            statement.close();
        }catch(Exception e){
            System.out.println("Error Deleting Obat");
            System.out.println(e);
        }
        
        dbCon.closeConnection();
    }
    
}
