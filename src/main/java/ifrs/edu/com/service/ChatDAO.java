package ifrs.edu.com.service;
import ifrs.edu.com.models.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatDAO implements DAO<Chat>{
    private List<Chat> list;

    ChatDAO(){
        list = new ArrayList<>();
    }

    @Override
    public boolean insert(Chat model) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Chat model) {
        return false;
    }

    @Override
    public List<Chat> list(int limit, int offset) {
        return list.subList(offset, limit);
    }

    @Override
    public Chat get(int id) {
        return list.get(id);
    }
}
