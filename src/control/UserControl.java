package control;

import model.User;
import dao.UserDAO;

public class UserControl {

    private UserDAO ud = new UserDAO();

    public void insertDataUser(User u) {
        ud.insertUser(u);
    }

    public void updateDataUser(User u) {
        ud.updateUser(u);
    }

    public void deleteDataUser(int id) {
        ud.deleteUser(id);
    }

    public int countDataUser() {
        return ud.countUser();
    }

    public boolean uniqueUser(String username) {
        return ud.isUnique(username);
    }

    public int checkLoginUser(String username, String password) {
        return ud.checkLogin(username, password);
    }

    public void alterIncrement() {
        ud.setIncrement();
    }

    public int findIdByUsername(String username) {
        return ud.findId(username);
    }

}
