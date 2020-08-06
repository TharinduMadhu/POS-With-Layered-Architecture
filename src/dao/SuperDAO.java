package dao;


import entity.SuperEntity;

import java.util.List;
//public static  <T extends Comparable>void sort(T[] se)
public interface   SuperDAO <T extends SuperEntity,ID> {

    List<T> findAll();

    T find(ID key);

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(ID key);


}
