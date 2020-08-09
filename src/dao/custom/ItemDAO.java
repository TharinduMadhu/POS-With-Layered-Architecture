package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {
    String getLastItemId() throws Exception;

}
