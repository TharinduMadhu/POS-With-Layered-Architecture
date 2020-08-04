package dao;

import com.sun.org.apache.xpath.internal.objects.XBoolean;
import db.DBConnection;
import util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataLayer {
    public static List<CustomerTM> getAllCustomers() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
            ArrayList<CustomerTM> customers = new ArrayList<>();
            while (rst.next()) {
                CustomerTM customer =
                        new CustomerTM(rst.getString(1),
                                rst.getString(2), rst.getString(3));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


    public static boolean saveCustomer(CustomerTM customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO CUSTOMER VALUES (?,?,?)");
            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getName());
            pstm.setObject(3, customer.getAddress());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public static boolean deleteCustomer(String customerId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM CUSTOMER WHERE id=?");
            pstm.setObject(1, customerId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(CustomerTM customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE CUSTOMER SET " +
                    "NAME=?,ADDRESS=? WHERE customerId=?");
            pstm.setObject(1, customer.getName());
            pstm.setObject(2, customer.getAddress());
            pstm.setObject(3, customer.getId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static List<ItemTM> getAllItems() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM  item");
            ArrayList<ItemTM> items = new ArrayList<>();
            while (rst.next()) {
                items.add(new ItemTM(rst.getString(1), rst.getString(2),
                        rst.getInt(3), rst.getDouble(4)));
            }
            return items;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static boolean saveItem(ItemTM item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO ITEM VALUES (?,?,?,?)");
            pstm.setObject(1, item.getCode());
            pstm.setObject(2, item.getDescription());
            pstm.setObject(3, item.getQtyOnHand());
            pstm.setObject(4, item.getUnitPrice());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public static boolean deleteItem(String code) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM item WHERE code=?");
            pstm.setObject(1, code);
            return (pstm.executeUpdate() > 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public static boolean updateItem(ItemTM item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET description=?,unitPrice=?,qtyOnHand=? where code=?");
            pstm.setObject(1, item.getDescription());
            pstm.setObject(2, item.getUnitPrice());
            pstm.setObject(3, item.getQtyOnHand());
            pstm.setObject(4, item.getCode());
            return (pstm.executeUpdate() > 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static String getLastCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM customer ORDER BY id DESC LIMIT 1 ");
            if (rst.next()) {
                return rst.getString(1);
            } else {
                return null;
            }
        } catch (SQLException thtowables) {
            thtowables.printStackTrace();
            return null;
        }
    }


    public static String getLastOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rst = statement.executeQuery("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1");
            if (rst.next()) {
                return rst.getString(1);
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static String getLastItemdId() {
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1");
            if (rst.next()) {
                return (rst.getString(1));
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static boolean saveOrder(OrderTM order) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getOrderDate());
            pstm.setObject(3, order.getCustomerId());
            int affectedRows = pstm.executeUpdate();
            return (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean saveOrderDetail(String orderId, List<OrderDetailTM> orderDetails){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO `OrderDetail` VALUES (?,?,?,?)");
            boolean result = false;
            for (OrderDetailTM orderDetail: orderDetails) {
                pstm.setObject(1, orderId);
                pstm.setObject(2, orderDetail.getCode());
                pstm.setObject(3, orderDetail.getQty());
                pstm.setObject(4, orderDetail.getUnitPrice());
                result =  pstm.executeUpdate()> 0;
                if (!result){
                    return false;
                }
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean updateQty(List<OrderDetailTM> orderDetails){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET qtyOnHand=qtyOnHand-? WHERE code=?");
            boolean result = false;
            for (OrderDetailTM orderDetail: orderDetails) {
                pstm.setObject(1, orderDetail.getQty());
                pstm.setObject(2, orderDetail.getCode());
                result =  pstm.executeUpdate()> 0;
                if (!result){
                    return false;
                }
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }








    }
