package dao;

import entity.OrderDetail;
import entity.OrderDetailPK;

import java.util.List;

public interface OrderDetailDAO {
    List<OrderDetail> findAllOrderDetails();

    OrderDetail findOrderDetail(OrderDetailPK orderDetailPK);

    boolean saveOrderDetail(OrderDetail orderDetail);

    boolean updateOrderDetail(OrderDetail orderDetail);

    boolean deleteOrderDetail(OrderDetailPK orderDetailPK);





}
