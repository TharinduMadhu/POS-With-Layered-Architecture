package dao;


import java.util.List;

public interface SuperDAO<T,ID> {

    List<T> findAll();

    T find(ID key);

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(ID key);


}
