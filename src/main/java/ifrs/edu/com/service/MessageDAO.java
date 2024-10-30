package ifrs.edu.com.service;
import ifrs.edu.com.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO implements DAO<Message>{
    private Connection db;

    MessageDAO(Connection db){
        this.db = db;
    }

    @Override
    public boolean insert(Message model) throws SQLException{
        String query = "INSERT INTO message (text, userid, chatid) VALUES (?, ?, ?)";
        PreparedStatement ps = this.db.prepareStatement(query);
        
        ps.setString(1, model.getText());
        ps.setInt(2, model.getUser().getUserId());
        ps.setInt(3, model.getChat().getChatId());

        return ps.execute();
    }

    @Override
    public boolean delete(int id) throws SQLException{
        String query = "DELETE FROM chat WHERE chatid = ?";
        PreparedStatement ps = this.db.prepareStatement(query);
        
        ps.setInt(1, id);

        return ps.execute();
    }

    @Override
    public boolean update(Message model) throws SQLException{
        String query = 
            """
                UPDATE chat SET text=?, userid=?, chatid=?
                WHERE chatid = ?
            """;
        PreparedStatement ps = this.db.prepareStatement(query);
        
        ps.setString(1, model.getText());
        ps.setInt(2, model.getUser().getUserId());
        ps.setInt(3, model.getChat().getChatId());

        ps.setInt(4, model.getMessageId());

        return ps.executeUpdate() > 0;
    }

    @Override
    public List<Message> list(int limit, int offset) throws SQLException{
        List<Message> list = new ArrayList<>();

        String query = 
            """
                SELECT messageid, text, userid, chatid, createdat, updatedat FROM chat 
                LIMIT ? OFFSET ?
            """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, limit);
        ps.setInt(2, offset);

        ResultSet response = ps.executeQuery();

        while(response.next()) {
            list.add(new Message(
                response.getInt("messageid"),
                response.getString("text"),
                response.getInt("userid"),
                response.getInt("chatid"),
                response.getDate("createdat"),
                response.getDate("updatedat")
            ));
        }

        return list;
    }

    @Override
    public Message get(int id) throws SQLException{
        String query = 
            """
                SELECT messageid, text, userid, chatid, createdat, updatedat FROM chat 
                WHERE id=?
                LIMIT 1
            """;
        PreparedStatement ps = this.db.prepareStatement(query);

        ps.setInt(1, id);

        ResultSet response = ps.executeQuery();

        if(response.next()) {
            return new Message(
                response.getInt("messageid"),
                response.getString("text"),
                response.getInt("userid"),
                response.getInt("chatid"),
                response.getDate("createdat"),
                response.getDate("updatedat")
            );
        }

        return null;
    }
    
}
