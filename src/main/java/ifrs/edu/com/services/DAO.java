package ifrs.edu.com.services;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public boolean insert(T model);

    public boolean delete(int id);

    public boolean update(T model);

    public List<T> list(int limit, int offset);

    public T get(int id);
}
