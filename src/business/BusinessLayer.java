package business;

import dao.DataLayer;
import db.DBConnection;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessLayer {

    public static List<CustomerTM> getAllCustomers() {
        return DataLayer.getAllCustomers();
    }

    public static boolean saveCustomer(String id, String name, String address) {
        return DataLayer.saveCustomer(new CustomerTM(id, name, address));
    }

    public static boolean deleteCustomer(String customerId) {
        return DataLayer.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(String id, String name, String address) {
        return DataLayer.updateCustomer(new CustomerTM(id, name, address));
    }

    public static List<ItemTM> getAllItems() {
        return DataLayer.getAllItems();
    }

    public static boolean saveItem(String code, String description, int unitPrice, int qtyOnHand) {
        return DataLayer.saveItem(new ItemTM(code, description, unitPrice, qtyOnHand));
    }

    public static boolean deleteItem(String itemCode) {
        return DataLayer.deleteItem(itemCode);
    }


    public static boolean updateItem(String code, String description, int unitPrice,
                                     int qtyOnHand) {
        return DataLayer.updateItem(new ItemTM(code, description, unitPrice, qtyOnHand));
    }

    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getOrderDate());
            pstm.setObject(3, order.getCustomerId());
            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                connection.rollback();
                return false;
            }

            for (OrderDetailTM orderDetail : orderDetails) {
                pstm = connection.prepareStatement("INSERT INTO OrderDetail VALUES (?,?,?,?)");
                pstm.setObject(1, order.getOrderId());
                pstm.setObject(2, orderDetail.getCode());
                pstm.setObject(3, orderDetail.getQty());
                pstm.setObject(4, orderDetail.getUnitPrice());
                affectedRows = pstm.executeUpdate();

                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }

                pstm = connection.prepareStatement("UPDATE Item SET qtyOnHand=qtyOnHand-? WHERE code=?");
                pstm.setObject(1, orderDetail.getQty());
                pstm.setObject(2, orderDetail.getCode());
                affectedRows = pstm.executeUpdate();

                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}