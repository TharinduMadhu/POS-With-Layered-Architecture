package dao;

import entity.Item;

import java.util.List;

public interface ItemDAO {
    List<Item> findAllItems();

    Item findItem(String itemCode);

    boolean saveItem(Item item);

    boolean updateItem(Item item);

    boolean deleteItem(String itemCode);

    String getLastItemId();

}
