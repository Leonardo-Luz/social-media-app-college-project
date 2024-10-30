package ifrs.edu.com.service;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T>{
    public boolean insert(T model) throws SQLException;
    public boolean delete(int id) throws SQLException;
    public boolean update(T model) throws SQLException;

    public List<T> list(int limit, int offset) throws SQLException;
    public T get(int id) throws SQLException;
}
