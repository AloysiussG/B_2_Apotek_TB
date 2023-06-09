/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.DbConnection;
import java.sql.Connection;
import java.sql.Statement;
import model.User;

/**
 *
 * @author Gregory Wilson
 */
public class UserDAO {

    private DbConnection dbCon = new DbConnection();
    private Connection con;

    public void insertUser(User u) {
        con = dbCon.makeConnection();

        String sql = "INSERT INTO user(username, password) VALUES ('"
                + u.getUsername() + "', '" + u.getPassword() + "')";

        System.out.println("Adding User...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " User");
            statement.close();
        } catch (Exception e) {
            System.out.println("Eror adding User...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }

    public void updateUser(User u) {
        con = dbCon.makeConnection();

        String sql = "UPDATE user SET username = '" + u.getUsername()
                + "', password = '" + u.getPassword()
                + "' WHERE idUser = '" + u.getIdUser() + "'";

        System.out.println("Editing User...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " User " + u.getIdUser());
            statement.close();
        } catch (Exception e) {
            System.out.println("Error editing User...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }

    public void deleteUser(int id) {
        con = dbCon.makeConnection();

        String sql = "DELETE FROM user WHERE idUser = '" + id + "'";

        System.out.println("Deleting User...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Delete " + result + " User " + id);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error deleting User...");
            System.out.println(e);
        }
        dbCon.closeConnection();
    }
}
