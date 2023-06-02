package Control;

import model.User;
import java.util.List;
import dao.UserDAO;


public class UserControl {
    private UserDAO ud = new UserDAO();
    
    public void insertDataUser(User u){
        ud.insertPengguna(u);
    }
    public void updateDataUser(User u){
        ud.updateUser(u);
    }
    public void deleteDataUser(int id){
        ud.deleteUser(id);
    }
    
}
