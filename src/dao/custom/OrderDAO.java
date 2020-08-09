package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order,String> {
    String getLastOrderId() throws Exception;



}
