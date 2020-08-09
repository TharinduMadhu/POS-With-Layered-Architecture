package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import db.DBConnection;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {


    public String getLastItemId() throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Item ORDER BY code DESC LIMIT 1");
            if (!rst.next()) {
                return null;
            } else {
                return rst.getString(1);
            }
    }


    @Override
    public List<Item> findAll() throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM item");
            List<Item> items = new ArrayList<>();
            while (rst.next()) {
                items.add(new Item(rst.getString(1),
                        rst.getString(2),
                        rst.getBigDecimal(3),
                        rst.getInt(4)));
            }
            return items;
    }

    @Override
    public Item find(String key) throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE code=?", key);
            if (rst.next()) {
                return new Item(rst.getString(1),
                        rst.getString(2),
                        rst.getBigDecimal(3),
                        rst.getInt(4));
            }
            return null;
        }

    @Override
    public boolean save(Item item) throws Exception {
            return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?)", item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
        }

    @Override
    public boolean update(Item item) throws Exception {
            return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean delete(String itemCode) throws Exception {
            return CrudUtil.execute("DELETE FROM Item WHERE code=?", itemCode);
    }
}
