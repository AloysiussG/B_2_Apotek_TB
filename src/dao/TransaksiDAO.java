/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pengguna;
import model.Transaksi;
import model.User;
import model.Staff;
import model.Role;
import model.Obat;

/**
 *
 * @author Gregory Wilson
 */
public class TransaksiDAO {
    private DbConnection dbCon = new DbConnection();
    private Connection con;
    
    public void insertTransaksi (Transaksi t){
        con = dbCon.makeConnection();
        
        String sql = "INSERT INTO transaksi(idTransaksi, NIP, idObat, idPengguna, tanggalPembelian, jumlah) VALUES ('"
               + t.getIdTransaksi() +"', '"
               + t.getStaff().getNIP() +"', '"
               + t.getObat().getIdObat()+"', '"
               + t.getPengguna().getIdPengguna() +"', '"
               + t.getTanggalPembelian()+"', '"
               + t.getJumlah()+ "')";
        System.out.println("Adding Transaksi...");
        
        try{
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Transaksi");
            statement.close();
        } catch (Exception e) {
            System.out.println("Error adding Pasien...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }
    
    public List<Transaksi> showTransaksi(String query) {
        con = dbCon.makeConnection();
        
        String sql = "SELECT t.*, s.*, o.*, p.* "
                + "FROM transaksi as t JOIN staff as s ON t.NIP = s.NIP JOIN obat as o ON t.idObat = o.idObat JOIN pengguna as p ON t.idPengguna = p.idPengguna WHERE (t.idTransaksi LIKE "
                + "'%" + query + "%'"
                + "OR t.tanggalPembelian LIKE '%" + query + "%'"
                + "OR p.nama LIKE '%" + query + "%'"
                + "OR s.nama LIKE '%" + query + "%'"
                + "OR o.namaObat LIKE '%" + query + "%'"
                + "OR t.jumlah LIKE '%" + query + "%')";
        
        System.out.println("Mengambil data Transaksi...");
        List<Transaksi> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    Role r = new Role(rs.getInt("r.idRole"),rs.getDouble("r.gaji"),rs.getString("r.namaRole"));
                    
                    User u = new User(
                            Integer.parseInt(rs.getString("u.idUser")),
                            rs.getString("u.username"),
                            rs.getString("u.password")
                    );
                    
                    Staff s = new Staff(
                        rs.getInt("s.NIP"),
                        rs.getString("s.nama"),
                        rs.getString("s.tahunMasuk"),
                        rs.getString("s.NoTelp"),
                        rs.getString("s.alamat"),r,u
                    );
                    
                    Obat o = new Obat(
                            Integer.parseInt(rs.getString("idObat")),
                            Integer.parseInt(rs.getString("kuantitas")),
                            rs.getString("namaObat"),
                            rs.getString("tanggalKadaluarsa"),
                            rs.getString("tanggalProduksi"),
                            Double.parseDouble(rs.getString("harga"))
                    );

                    Pengguna p = new Pengguna(
                                    Integer.parseInt(rs.getString("p.idUser")),
                                    Integer.parseInt(rs.getString("p.idPengguna")),
                                    rs.getString("p.nama"),
                                    rs.getString("p.noTelp"),
                                    rs.getString("p.alamat"), u);
                    
                    Transaksi t = new Transaksi(
                            Integer.parseInt(rs.getString("t.idTransaksi")),
                            Integer.parseInt(rs.getString("t.jumlah")),
                            rs.getString("t.tanggalPembelian"),
                            s, o, p);
                    
                    list.add(t);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error reading database...");
            System.out.println(e);
        }
        dbCon.closeConnection();
        
        return list;
    }
    
    public void updateTransaksi(Transaksi t) {
        con = dbCon.makeConnection();
        
        String sql = "UPDATE transaksi SET tanggalPembelian = '" + t.getTanggalPembelian()
                + "', nama = '" + t.getPengguna().getNama()
                + "', nama = '" + t.getStaff().getNama()
                + "', jumlah = '" + t.getJumlah()
                + "' WHERE idTransaksi = '" + t.getIdTransaksi()+ "'";
        
        System.out.println("Editing Transaksi...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " Pengguna " + t.getIdTransaksi());
            statement.close();
        } catch (Exception e) {
            System.out.println("Error editing Transaksi...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }
}
