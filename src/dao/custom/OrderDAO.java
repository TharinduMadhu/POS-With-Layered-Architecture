package dao.custom;

import dao.SuperDAO;
import entity.Order;

import java.util.List;

public interface OrderDAO extends SuperDAO<Order,String> {
    String getLastOrderId();



}
