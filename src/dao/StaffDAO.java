package dao;

import model.Staff;
import connection.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import model.User;

public class StaffDAO {

    private DbConnection dbCon = new DbConnection();
    private Connection con;

    public List<Staff> showStaff(String query) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM staff as s JOIN role as r on s.idRole = r.idRole JOIN user as u on s.idUser = u.idUser  WHERE"
                + "(NIP LIKE '%" + query + "%'"
                + "OR s.nama LIKE '%" + query + "%'"
                + "OR s.TahunMasuk LIKE '%" + query + "%'"
                + "OR s.noTelp LIKE '%" + query + "%'"
                + "OR s.alamat LIKE '%" + query + "%'"
                + "OR r.namaRole LIKE '%" + query + "s'"
                + "OR u.username LIKE '%" + query + "%'"
                + "OR u.password LIKE '%" + query + "%'"
                + ")";

        System.out.println(sql);
        System.out.println("Menambil data setaff");
        List<Staff> list = new ArrayList();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    Role r = new Role(rs.getInt("r.idRole"), rs.getDouble("r.gaji"), rs.getString("r.namaRole"));
                    User u = new User(rs.getInt("u.idUser"), rs.getString("u.username"), rs.getString("u.password"));
                    Staff s = new Staff(
                            rs.getInt("s.NIP"),
                            rs.getString("s.nama"),
                            rs.getString("s.tahunMasuk"),
                            rs.getString("s.NoTelp"),
                            rs.getString("s.alamat"), r, u
                    );
                    list.add(s);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Reading Database");
            e.printStackTrace();
        }
        dbCon.closeConnection();
        return list;
    }

    public void insertStaff(Staff s) {
        con = dbCon.makeConnection();

        String sql = "INSERT INTO `staff`(`idUser`,`idRole`, `nama`, `TahunMasuk`, `noTelp`, `alamat`) VALUES ('"
                + s.getUser().getIdUser() + "','"
                + s.getRole().getIdRole() + "','"
                + s.getNama() + "','"
                + s.getTahunMasuk() + "','"
                + s.getNoTelp() + "','"
                + s.getAlamat() + "')";

        System.out.println("Adding Staff...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Staff");
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Adding Staff..");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public void updateStaff(Staff s) {
        con = dbCon.makeConnection();

        String sql = "UPDATE staff SET idRole  = '" + s.getRole().getIdRole()
                + "', nama= '" + s.getNama()
                + "', TahunMasuk = '" + s.getTahunMasuk()
                + "', noTelp = '" + s.getNoTelp()
                + "', alamat = '" + s.getAlamat()
                + "' WHERE NIP = '" + s.getNIP() + "'";

        System.out.println("Editing Setaf");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " Staff " + s.getNIP());
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Editing Staff");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public void deleteStaf(int nip) {
        con = dbCon.makeConnection();

        String sql = "DELETE FROM staff WHERE NIP = " + nip + "";
        System.out.println("Deleting staff");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Delete " + result + "Staff " + nip);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error deleting Staff...");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public int checkStaff(int userId) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM staff WHERE idUser = " + userId + "";
        System.out.println("Finding Staff");

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
            e.printStackTrace();
        }

        dbCon.closeConnection();
        return -1;
    }

    public String returnName(int userId) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM staff WHERE idUser = " + userId + "";
        System.out.println("Finding Staff");

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    return rs.getString("nama");
                }
            }

            return null;
        } catch (Exception e) {
            System.out.println(e);
        }

        dbCon.closeConnection();
        return null;
    }

    public Staff returnStaff(int userId) {
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM staff as s JOIN user as u WHERE u.idUser = " + userId + " AND s.idUser = " + userId + "";
        System.out.println("Finding Staff");

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    User uTemp = new User(Integer.parseInt(rs.getString("u.idUser")), rs.getString("u.username"), null);
                    Role rTemp = new Role(Integer.parseInt(rs.getString("s.idRole")), 0, null);
                    Staff temp = new Staff(Integer.parseInt(rs.getString("s.nip")), rs.getString("s.nama"), rs.getString("tahunMasuk"), rs.getString("s.noTelp"), rs.getString("s.alamat"), rTemp, uTemp);
                    System.out.println(uTemp.getIdUser());
                    System.out.println(rTemp.getIdRole());
                    System.out.println(temp.getRole().getIdRole());
                    System.out.println(temp.getTahunMasuk());
                    return temp;
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbCon.closeConnection();
        return null;
    }
}

//package dao;
//
//import model.Staff;
//import connection.DbConnection;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import model.Role;
//import model.User;
//
//public class StaffDAO {
//
//    private DbConnection dbCon = new DbConnection();
//    private Connection con;
//
//    public List<Staff> showStaff(String query) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM staff as s JOIN role as r on s.idRole = r.idRole JOIN user as u on s.idUser = u.idUser  WHERE"
//                + "(NIP LIKE '%" + query + "%'"
//                + "OR s.nama LIKE '%" + query + "%'"
//                + "OR s.TahunMasuk LIKE '%" + query + "%'"
//                + "OR s.noTelp LIKE '%" + query + "%'"
//                + "OR s.alamat LIKE '%" + query + "%'"
//                + "OR r.namaRole LIKE '%" + query + "s'"
//                + "OR u.username LIKE '%" + query + "%'"
//                + "OR u.password LIKE '%" + query + "%'"
//                + ")";
//
//        System.out.println(sql);
//        System.out.println("Menambil data setaff");
//        List<Staff> list = new ArrayList();
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//
//            if (rs != null) {
//                while (rs.next()) {
//                    Role r = new Role(rs.getInt("r.idRole"), rs.getDouble("r.gaji"), rs.getString("r.namaRole"));
//                    User u = new User(rs.getInt("u.idUser"), rs.getString("u.username"), rs.getString("u.password"));
//                    Staff s = new Staff(
//                            rs.getInt("s.NIP"),
//                            rs.getString("s.nama"),
//                            rs.getString("s.tahunMasuk"),
//                            rs.getString("s.NoTelp"),
//                            rs.getString("s.alamat"), r, u
//                    );
//                    list.add(s);
//                }
//            }
//            rs.close();
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error Reading Database");
//            e.printStackTrace();
//        }
//        dbCon.closeConnection();
//        return list;
//    }
//
//    public void insertStaff(Staff s) {
//        con = dbCon.makeConnection();
//
//        String sql = "INSERT INTO `staff`(`idUser`,`idRole`, `nama`, `TahunMasuk`, `noTelp`, `alamat`) VALUES ('"
//                + s.getUser().getIdUser() + "','"
//                + s.getRole().getIdRole() + "','"
//                + s.getNama() + "','"
//                + s.getTahunMasuk() + "','"
//                + s.getNoTelp() + "','"
//                + s.getAlamat() + "')";
//
//        System.out.println("Adding Staff...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Added " + result + " Staff");
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error Adding Staff..");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//    }
//
//    public void updateStaff(Staff s) {
//        con = dbCon.makeConnection();
//
//        String sql = "UPDATE staff SET idRole  = '" + s.getRole().getIdRole()
//                + "', nama= '" + s.getNama()
//                + "', TahunMasuk = '" + s.getTahunMasuk()
//                + "', noTelp = '" + s.getNoTelp()
//                + "', alamat = '" + s.getAlamat()
//                + "' WHERE NIP = '" + s.getNIP() + "'";
//
//        System.out.println("Editing Setaf");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Edited " + result + " Staff " + s.getNIP());
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error Editing Staff");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//    }
//
//    public void deleteStaf(int nip) {
//        con = dbCon.makeConnection();
//
//        String sql = "DELETE FROM staff WHERE NIP = " + nip + "";
//        System.out.println("Deleting staff");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Delete " + result + "Staff " + nip);
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error deleting Staff...");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//    }
//
//    public int checkStaff(int userId) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM staff WHERE idUser = " + userId + "";
//        System.out.println("Finding Staff");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            if (rs != null) {
//                while (rs.next()) {
//                    return Integer.parseInt(rs.getString("idUser"));
//                }
//            }
//
//            return -1;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        dbCon.closeConnection();
//        return -1;
//    }
//
//    public String returnName(int userId) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM staff WHERE idUser = " + userId + "";
//        System.out.println("Finding Staff");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//            if (rs != null) {
//                while (rs.next()) {
//                    return rs.getString("nama");
//                }
//            }
//
//            return null;
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//        return null;
//    }
//
//    public Staff returnStaff(int userId) {
//        con = dbCon.makeConnection();
//
//        String sql = "SELECT * FROM staff as s JOIN user as u WHERE u.idUser = " + userId + " AND s.idUser = " + userId + "";
//        System.out.println("Finding Staff");
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//
//            if (rs != null) {
//                while (rs.next()) {
//                    User uTemp = new User(Integer.parseInt(rs.getString("u.idUser")), rs.getString("u.username"), null);
//                    Role rTemp = new Role(Integer.parseInt(rs.getString("s.idRole")), 0, null);
//                    Staff temp = new Staff(Integer.parseInt(rs.getString("s.nip")), rs.getString("s.nama"), rs.getString("tahunMasuk"), rs.getString("s.noTelp"), rs.getString("s.alamat"), rTemp, uTemp);
//                    System.out.println(uTemp.getIdUser());
//                    System.out.println(rTemp.getIdRole());
//                    System.out.println(temp.getRole().getIdRole());
//                    System.out.println(temp.getTahunMasuk());
//                    return temp;
//                }
//            }
//
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        dbCon.closeConnection();
//        return null;
//    }
//}
