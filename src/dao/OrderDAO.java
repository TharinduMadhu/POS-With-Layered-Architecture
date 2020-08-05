package dao;

import entity.Order;

import java.util.List;

public interface OrderDAO {
    String getLastOrderId();

    boolean deleteOrder(String orderId);

    boolean updateOrder(Order order);

    boolean saveOrder(Order order);
    Order findOrder(String orderId);

    List<Order> findAllOrders();

}
