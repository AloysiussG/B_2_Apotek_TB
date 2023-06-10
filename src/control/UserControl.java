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

    public int findIdByUsername(String username) {
        return ud.findIdByUsername(username);
    }

}
