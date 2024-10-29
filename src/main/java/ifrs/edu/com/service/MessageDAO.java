package ifrs.edu.com.service;
import ifrs.edu.com.models.Message;

import java.util.ArrayList;
import java.util.List;


public class MessageDAO implements DAO<Message>{
    private List<Message> messages;

    MessageDAO(){
        this.messages = new ArrayList<>();
    }

    @Override
    public boolean insert(Message model) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Message model) {
        return false;
    }

    @Override
    public List<Message> list(int limit, int offset) {
        return messages.subList(offset, limit);
    }

    @Override
    public Message get(int id) {
        return messages.get(id);
    }
    
}
