package ifrs.edu.com.service;
import ifrs.edu.com.models.User;

import java.util.ArrayList;
import java.util.List;


public class UserDAO implements DAO<User>{
    private List<User> users;

    UserDAO(){
        users = new ArrayList<>();
    }

    @Override
    public boolean insert(User model) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(User model) {
        return false;
    }

    @Override
    public List<User> list(int limit, int offset) {
        return users.subList(offset, limit);
    }

    @Override
    public User get(int id) {
        return users.get(id);
    }    
}
