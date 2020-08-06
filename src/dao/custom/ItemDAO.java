package dao.custom;

import dao.SuperDAO;
import entity.Item;

import java.util.List;

public interface ItemDAO extends SuperDAO<Item,String> {
    String getLastItemId();

}
