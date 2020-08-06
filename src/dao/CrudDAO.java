package dao;

import entity.SuperEntity;

import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID>  extends SuperDAO{
    List<T> findAll();

    T find(ID key);

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(ID key);

}
