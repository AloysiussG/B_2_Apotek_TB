/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.DbConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
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
                + u.getUsername() + "', '"
                + u.getPassword() + "')";

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
    
//    public void deleteUser(int id) {
//        con = dbCon.makeConnection();
//
//        String sql = "DELETE FROM user WHERE idUser = '" + id + "'"
//                + "SET  @num := 0"
//                + "UPDATE `user` SET idUser = @num := (@num+1)"
//                + "ALTER TABLE `user` AUTO_INCREMENT = 1;";
//
//        System.out.println("Deleting User...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Delete " + result + " User " + id);
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error deleting User...");
//            System.out.println(e);
//        }
//        dbCon.closeConnection();
//    }

    public int countUser() {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM `user`";

        System.out.println("Counting User...");

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            int size = 0;
            while (rs.next()) {
                size++;
            }

            return size;

        } catch (Exception e) {
            System.out.println(e);
        }

        dbCon.closeConnection();
        return 0;
    }

    public boolean isUnique(String username) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM `user` WHERE username = '" + username + "'";

        System.out.println("Traversing User...");

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    System.out.println(username);
                    System.out.println(rs.getString("username"));
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        dbCon.closeConnection();
        return false;
    }

    public int checkLogin(String username, String password) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM `user` WHERE username = '" + username + "' AND password = '" + password + "'";

        System.out.println("Counting User...");

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            System.out.println(password);
            if (rs != null) {
                while (rs.next()) {
                    if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                        return Integer.parseInt(rs.getString("idUser"));
                    }
                }
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        dbCon.closeConnection();
        return -1;
    }

//    public int checkLogin(String username, String password) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM `user` WHERE username = '" + username + "' AND password = '" + password + "'";
//
//        System.out.println("Counting User...");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            System.out.println(password);
//            if (rs != null) {
//                while (rs.next()) {
//                    return Integer.parseInt(rs.getString("idUser"));
//                }
//            }
//
//            statement.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//        return -1;
//    }
    public void setIncrement() {
        con = dbCon.makeConnection();

        String sql1 = "SET  @num := 0;";
        String sql2 = "UPDATE `user` SET idUser = @num := (@num+1);";
        String sql3 = "ALTER TABLE `user` AUTO_INCREMENT = 1;";

        System.out.println("Altering Increment");

        try {
            Statement statement = con.createStatement();
            statement.executeQuery(sql1);
            statement.executeQuery(sql2);
            statement.executeQuery(sql3);

        } catch (Exception e) {
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public int findId(String username) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM `user` WHERE username = '" + username + "'";

        System.out.println("Finding user");

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    return Integer.parseInt(rs.getString("idUser"));
                }
            }

            return -1;
        } catch (Exception e) {
            System.out.println(e);
        }

        dbCon.closeConnection();
        return -1;
    }

}

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package dao;
//
//import connection.DbConnection;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.sql.ResultSet;
//import model.User;
//
///**
// *
// * @author Gregory Wilson
// */
//public class UserDAO {
//
//    private DbConnection dbCon = new DbConnection();
//    private Connection con;
//
//    public void insertUser(User u) {
//        con = dbCon.makeConnection();
//
//        String sql = "INSERT INTO user(username, password) VALUES ('"
//                + u.getUsername() + "', '" + u.getPassword() + "')";
//
//        System.out.println("Adding User...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Added " + result + " User");
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Eror adding User...");
//            System.out.println(e);
//        }
//        dbCon.closeConnection();
//    }
//
//    public void updateUser(User u) {
//        con = dbCon.makeConnection();
//
//        String sql = "UPDATE user SET username = '" + u.getUsername()
//                + "', password = '" + u.getPassword()
//                + "' WHERE idUser = '" + u.getIdUser() + "'";
//
//        System.out.println("Editing User...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Edited " + result + " User " + u.getIdUser());
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error editing User...");
//            System.out.println(e);
//        }
//        dbCon.closeConnection();
//    }
//
//    public void deleteUser(int id) {
//        con = dbCon.makeConnection();
//
//        String sql = "DELETE FROM user WHERE idUser = '" + id + "'";
//
//        System.out.println("Deleting User...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Delete " + result + " User " + id);
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error deleting User...");
//            System.out.println(e);
//        }
//        dbCon.closeConnection();
//    }
////
////    public int findIdByUsername(String username) {
////        con = dbCon.makeConnection();
////
////        String sql = "SELECT * FROM user WHERE username = '" + username + "'";
////        int id = -1;
////        try {
////            Statement statement = con.createStatement();
////            ResultSet rs = statement.executeQuery(sql);
////            if (rs != null) {
////                while (rs.next()) {
////                    id = rs.getInt("idUser");
////                }
////            }
////            rs.close();
////            statement.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        dbCon.closeConnection();
////        return id;
////    }
//
//    public int countUser() {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM `user`";
//
//        System.out.println("Counting User...");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            int size = 0;
//            while (rs.next()) {
//                size++;
//            }
//
//            return size;
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return 0;
//    }
//
//    public boolean isUnique(String username) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM `user` WHERE username = '" + username + "'";
//
//        System.out.println("Traversing User...");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            if (rs != null) {
//                while (rs.next()) {
//                    return false;
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return true;
//    }
//
//    public int checkLogin(String username, String password) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM `user` WHERE username = '" + username + "' AND password = '" + password + "'";
//
//        System.out.println("Counting User...");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            System.out.println(password);
//            if (rs != null) {
//                while (rs.next()) {
//                    return Integer.parseInt(rs.getString("idUser"));
//                }
//            }
//
//            statement.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return -1;
//    }
//
//    public void setIncrement() {
//        con = dbCon.makeConnection();
//
//        String sql1 = "SET  @num := 0;";
//        String sql2 = "UPDATE `user` SET idUser = @num := (@num+1);";
//        String sql3 = "ALTER TABLE `user` AUTO_INCREMENT = 1;";
//
//        System.out.println("Altering Increment");
//
//        try {
//            Statement statement = con.createStatement();
//            statement.executeQuery(sql1);
//            statement.executeQuery(sql2);
//            statement.executeQuery(sql3);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//    }
//
//    public int findId(String username) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM `user` WHERE username = '" + username + "'";
//
//        System.out.println("Finding user");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//
//            if (rs != null) {
//                while (rs.next()) {
//                    return Integer.parseInt(rs.getString("idUser"));
//                }
//            }
//
//            return -1;
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//        return -1;
//    }
//
//}
