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
import model.User;

/**
 *
 * @author Gregory Wilson
 */
public class PenggunaDAO {

    private DbConnection dbCon = new DbConnection();
    private Connection con;

    public void insertPengguna(Pengguna p) {
        con = dbCon.makeConnection();

        String sql = "INSERT INTO pengguna(idUser, nama, noTelp, alamat) VALUES ('"
                + p.getUser().getIdUser() + "', '"
                + p.getNama() + "', '" + p.getNoTelp() + "','"
                + p.getAlamat() + "')";

        System.out.println("Adding Pengguna...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Pengguna");
            statement.close();
        } catch (Exception e) {
            System.out.println("Eror adding Pengguna...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }

    public List<Pengguna> showPengguna(String query) {
        con = dbCon.makeConnection();

        String sql = "SELECT p.*, u.* "
                + "FROM pengguna as p JOIN user as u ON p.idUser = u.idUser WHERE (p.idPengguna LIKE "
                + "'%" + query + "%'"
                + "OR p.idUser LIKE '%" + query + "%'"
                + "OR p.nama LIKE '%" + query + "%'"
                + "OR p.noTelp LIKE '%" + query + "%'"
                + "OR p.alamat LIKE '%" + query + "%'"
                + "OR u.username LIKE '%" + query + "%'"
                + "OR u.password LIKE '%" + query + "%')";

        System.out.println("Mengambil data Pengguna...");
        List<Pengguna> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    User u = new User(
                            Integer.parseInt(rs.getString("u.idUser")),
                            rs.getString("u.username"),
                            rs.getString("u.password")
                    );

                    Pengguna p = new Pengguna(
                            Integer.parseInt(rs.getString("p.idPengguna")),
                            rs.getString("p.nama"),
                            rs.getString("p.noTelp"),
                            rs.getString("p.alamat"), u);
                    list.add(p);
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

    public void updatePengguna(Pengguna p) {
        con = dbCon.makeConnection();

        String sql = "UPDATE pengguna SET nama = '" + p.getNama()
                + "', noTelp = '" + p.getNoTelp()
                + "', alamat = '" + p.getAlamat()
                + "' WHERE idPengguna = " + p.getIdPengguna();

        System.out.println("Editing Pengguna...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " Pengguna " + p.getIdPengguna());
            statement.close();
        } catch (Exception e) {
            System.out.println("Error editing Pengguna...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }

    public void deletePengguna(int id) {
        con = dbCon.makeConnection();

        String sql = "DELETE FROM pengguna WHERE idPengguna = '" + id + "'";

        System.out.println("Deleting Pengguna...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Delete " + result + " Pengguna " + id);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error deleting Pengguna...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }
}
