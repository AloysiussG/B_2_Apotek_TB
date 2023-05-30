/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import connection.DbConnection;

/**
 *
 * @author AG SETO GALIH D
 */
public class TestDAO {

    private DbConnection dbCon = new DbConnection();
    private Connection con;

    public void testSelectQuery(String tableName) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM " + tableName;
        System.out.println("[~] Testing select query...");

        try {
            Statement statement = con.createStatement();
            System.out.println("[~] Menjalankan query " + sql);
            ResultSet rs = statement.executeQuery(sql);

            int i = 1;

            if (rs != null) {
                while (rs.next()) {
                    System.out.println("[~] Accessing row " + i++);
                }
            }
            rs.close();
            statement.close();
            System.out.println("[+] Berhasil melakukan testSelectQuery!");

        } catch (Exception e) {
            System.out.println("[-] Error performing testSelectQuery!");
        }
        dbCon.closeConnection();
    }

}
