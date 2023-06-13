package dao;

/**
 *
 * @author willi
 */
import connection.DbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Obat;
import model.PengadaanObat;
import model.Staff;
import model.User;
import model.Role;

public class PengadaanObatDAO {

    private DbConnection dbCon = new DbConnection();
    private Connection con;

    public void insertPengadaanObat(PengadaanObat po) {
        con = dbCon.makeConnection();

        String sql
                = "INSERT INTO pengadaanobat"
                + "(idObat,nip,kuantitas,supplier,tanggalPengadaan) "
                + "VALUES ("
                + "'" + po.getObat().getIdObat() + "', "
                + "'" + po.getStaff().getNIP() + "', "
                + "'" + po.getKuantitas() + "', "
                + "'" + po.getSupplier() + "', "
                + "'" + po.getTanggalPengadaan() + "')";

        System.out.println("Adding Stok Obat...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Stok Obat");
            statement.close();
        } catch (Exception e) {
            System.out.println("[!] Error Adding Stok Obat...");
            System.out.println(e);
        }
        dbCon.closeConnection();

    }

//    public List<PengadaanObat> showPengadaanObat(String query) {
//
//        con = dbCon.makeConnection();
//
//        String sql
//                = "SELECT po.*, ob.*, s.*, r.*, u.* "
//                + "FROM pengadaanobat AS po "
//                + "JOIN obat AS ob "
//                + "ON po.idObat = ob.idObat "
//                + "JOIN staff AS s "
//                + "ON po.nip = s.nip "
//                + "JOIN role AS r "
//                + "ON s.idRole = r.idRole "
//                + "JOIN user AS u "
//                + "ON s.idUser = u.idUser "
//                + "WHERE ( "
//                + "po.idPengadaan LIKE '%" + query + "%' "
//                + "OR ob.idObat LIKE '%" + query + "%' "
//                + "OR s.nama LIKE '%" + query + "%' "
//                + "OR po.kuantitas LIKE '%" + query + "%' "
//                + "OR po.supplier LIKE '%" + query + "%' "
//                + "OR po.tanggalPengadaan LIKE '%" + query + "%')";
//
//        System.out.println("Mengambil Data Pengadaan Obat...");
//        List<PengadaanObat> list = new ArrayList();
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//
//            if (rs != null) {
//                while (rs.next()) {
//                    Obat obat = new Obat(
//                            Integer.parseInt(rs.getString("ob.idObat")),
//                            Integer.parseInt(rs.getString("ob.kuantitas")),
//                            rs.getString("ob.namaObat"),
//                            rs.getString("ob.tanggalKadaluarsa"),
//                            rs.getString("ob.tanggalProduksi"),
//                            Double.parseDouble(rs.getString("ob.harga")));
//
//                    Role role = new Role(
//                            Integer.parseInt(rs.getString("r.idRole")),
//                            Double.parseDouble(rs.getString("r.gaji")),
//                            rs.getString("r.namaRole"));
//
//                    User user = new User(
//                            Integer.parseInt(rs.getString("u.idUser")),
//                            rs.getString("u.username"),
//                            rs.getString("u.password"));
//
//                    Staff staff = new Staff(
//                            Integer.parseInt(rs.getString("s.nip")),
//                            rs.getString("s.nama"),
//                            rs.getString("s.tahunMasuk"),
//                            rs.getString("s.noTelp"),
//                            rs.getString("s.alamat"),
//                            role, user);
//
//                    PengadaanObat pObat = new PengadaanObat(
//                            Integer.parseInt(rs.getString("po.idPengadaan")),
//                            Integer.parseInt(rs.getString("po.kuantitas")),
//                            Integer.parseInt(rs.getString("po.idObat")),
//                            rs.getString("po.supplier"),
//                            rs.getString("po.tanggalPengadaan"),
//                            obat, staff);
//                    list.add(pObat);
//                }
//            }
//            rs.close();
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error Reading Database...");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//        return list;
//    }
    public List<PengadaanObat> showPengadaanObat(String query) {
        con = dbCon.makeConnection();

        String sql
                = "SELECT po.*, ob.*, s.*, r.*, u.* "
                + "FROM pengadaanobat AS po "
                + "JOIN obat AS ob "
                + "ON po.idObat = ob.idObat "
                + "JOIN staff AS s "
                + "ON po.nip = s.nip "
                + "JOIN role AS r "
                + "ON s.idRole = r.idRole "
                + "JOIN user AS u "
                + "ON s.idUser = u.idUser "
                + "WHERE ( "
                + "po.idPengadaan LIKE '%" + query + "%' "
                + "OR ob.namaObat LIKE '%" + query + "%' "
                + "OR s.nama LIKE '%" + query + "%' "
                + "OR po.kuantitas LIKE '%" + query + "%' "
                + "OR po.supplier LIKE '%" + query + "%' "
                + "OR po.tanggalPengadaan LIKE '%" + query + "%')";

        System.out.println("Mengambil Data Pengadaan Obat...");
        List<PengadaanObat> list = new ArrayList();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null) {
                while (rs.next()) {
                    Obat obat = new Obat(
                            Integer.parseInt(rs.getString("ob.idObat")),
                            Integer.parseInt(rs.getString("ob.kuantitas")),
                            rs.getString("ob.namaObat"),
                            rs.getString("ob.tanggalKadaluarsa"),
                            rs.getString("ob.tanggalProduksi"),
                            Double.parseDouble(rs.getString("ob.harga")));

                    Role role = new Role(
                            Integer.parseInt(rs.getString("r.idRole")),
                            Double.parseDouble(rs.getString("r.gaji")),
                            rs.getString("r.namaRole"));

                    User user = new User(
                            Integer.parseInt(rs.getString("u.idUser")),
                            rs.getString("u.username"),
                            rs.getString("u.password"));

                    Staff staff = new Staff(
                            Integer.parseInt(rs.getString("s.nip")),
                            rs.getString("s.nama"),
                            rs.getString("s.tahunMasuk"),
                            rs.getString("s.noTelp"),
                            rs.getString("s.alamat"),
                            role, user);

                    PengadaanObat pObat = new PengadaanObat(
                            Integer.parseInt(rs.getString("po.idPengadaan")),
                            Integer.parseInt(rs.getString("po.kuantitas")),
                            Integer.parseInt(rs.getString("po.idObat")),
                            rs.getString("po.supplier"),
                            rs.getString("po.tanggalPengadaan"),
                            obat, staff);
                    list.add(pObat);
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Reading Database...");
            System.out.println(e);
        }

        dbCon.closeConnection();
        return list;
    }

    public void updatePengadaanObat(PengadaanObat po) {
        con = dbCon.makeConnection();

        String sql
                = "UPDATE pengadaanobat SET "
                + "idObat = '" + po.getObat().getIdObat() + "', "
                + "kuantitas = '" + po.getKuantitas() + "', "
                + "supplier = '" + po.getSupplier() + "', "
                + "tanggalPengadaan = '" + po.getTanggalPengadaan() + "' "
                + "WHERE idPengadaan = '" + po.getIdPengadaan() + "'";

        System.out.println("Editing Pengadaan Obat...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " Pengadaan Obat " + po.getIdPengadaan());
            statement.close();
        } catch (Exception e) {
            System.out.println("[!] Error Editing Pengadaan Obat...");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }

    public void deletePengadaanObat(int idPengadaanObat) {
        con = dbCon.makeConnection();

        String sql = "DELETE FROM pengadaanobat WHERE idPengadaan = " + idPengadaanObat;

        System.out.println("Deleting Stok Obat...");

        try {
            Statement statement = con.createStatement();
            int result = statement.executeUpdate(sql);
            System.out.println("Delete " + result + " Stok Obat " + idPengadaanObat);
            statement.close();
        } catch (Exception e) {
            System.out.println("[!] Error Deleting Stok Obat...");
            System.out.println(e);
        }

        dbCon.closeConnection();
    }
}

////code lama
//
//package dao;
//
///**
// *
// * @author willi
// */
//import connection.DbConnection;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import model.Obat;
//import model.PengadaanObat;
//
//public class PengadaanObatDAO {
//
//    private DbConnection dbCon = new DbConnection();
//    private Connection con;
//
//    public void insertPengadaanObat(PengadaanObat po) {
//        con = dbCon.makeConnection();
//
//        String sql
//                = "INSERT INTO pengadaanobat"
//                + "(idObat,kuantitas,supplier,tanggalPengadaan) "
//                + "VALUES ("
//                + "'" + po.getObat().getIdObat() + "', "
//                + "'" + po.getKuantitas() + "', "
//                + "'" + po.getSupplier() + "', "
//                + "'" + po.getTanggalPengadaan() + "')";
//
//        System.out.println("Adding Stok Obat...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Added " + result + " Stok Obat");
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("[!] Error Adding Stok Obat...");
//            System.out.println(e);
//        }
//        dbCon.closeConnection();
//
//    }
//
//    public List<PengadaanObat> showPengadaanObat(String query) {
//
//        con = dbCon.makeConnection();
//
//        String sql
//                = "SELECT po.*, ob.*"
//                + "FROM pengadaanobat AS po "
//                + "JOIN obat AS ob "
//                + "ON po.idObat = ob.idObat "
//                + "WHERE ( "
//                + "po.idPengadaan LIKE '%" + query + "%' "
//                + "OR ob.idObat LIKE '%" + query + "%' "
//                + "OR po.kuantitas LIKE '%" + query + "%' "
//                + "OR po.supplier LIKE '%" + query + "%' "
//                + "OR po.tanggalPengadaan LIKE '%" + query + "%')";
//
//        System.out.println("Mengambil Data Pengadaan Obat...");
//        List<PengadaanObat> list = new ArrayList();
//
//        try {
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//
//            if (rs != null) {
//                while (rs.next()) {
//                    Obat obat = new Obat(
//                            Integer.parseInt(rs.getString("ob.idObat")),
//                            Integer.parseInt(rs.getString("ob.kuantitas")),
//                            rs.getString("ob.namaObat"),
//                            rs.getString("ob.tanggalKadaluarsa"),
//                            rs.getString("ob.tanggalProduksi"),
//                            Double.parseDouble(rs.getString("ob.harga")));
//
//                    PengadaanObat pObat = new PengadaanObat(
//                            Integer.parseInt(rs.getString("po.idPengadaan")),
//                            Integer.parseInt(rs.getString("po.kuantitas")),
//                            Integer.parseInt(rs.getString("po.idObat")),
//                            rs.getString("po.supplier"),
//                            rs.getString("po.tanggalPengadaan"),
//                            obat
//                    );
//                    list.add(pObat);
//                }
//            }
//            rs.close();
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("Error Reading Database...");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//        return list;
//    }
//
//    public void updatePengadaanObat(PengadaanObat po) {
//        con = dbCon.makeConnection();
//
//        String sql
//                = "UPDATE pengadaanobat SET "
//                + "idObat = '" + po.getObat().getIdObat() + "', "
//                + "kuantitas = '" + po.getKuantitas() + "', "
//                + "supplier = '" + po.getSupplier() + "', "
//                + "tanggalPengadaan = '" + po.getTanggalPengadaan() + "' "
//                + "WHERE idPengadaan = '" + po.getIdPengadaan() + "'";
//
//        System.out.println("Editing Pengadaan Obat...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Edited " + result + " Pengadaan Obat " + po.getIdPengadaan());
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("[!] Error Editing Pengadaan Obat...");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//    }
//
//    public void deletePengadaanObat(int idPengadaanObat) {
//        con = dbCon.makeConnection();
//
//        String sql = "DELETE FROM pengadaanobat WHERE idPengadaan = " + idPengadaanObat;
//
//        System.out.println("Deleting Stok Obat...");
//
//        try {
//            Statement statement = con.createStatement();
//            int result = statement.executeUpdate(sql);
//            System.out.println("Delete " + result + " Stok Obat " + idPengadaanObat);
//            statement.close();
//        } catch (Exception e) {
//            System.out.println("[!] Error Deleting Stok Obat...");
//            System.out.println(e);
//        }
//
//        dbCon.closeConnection();
//    }
//}
