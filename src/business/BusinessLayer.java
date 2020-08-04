package business;

import dao.DataLayer;
import db.DBConnection;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessLayer {
    // Generate a new id
    public static String generateNewCustomerId() {
        String lastCustomerId = DataLayer.getLastCustomerId().replace("C", "");
        if (lastCustomerId == null) {
            return "C001";
        } else {
            int maxId = Integer.parseInt(lastCustomerId);
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }

    public static String generateNewOrderId() {
        String lastOrderId = DataLayer.getLastOrderId().replace("OD", "");
        if (lastOrderId == null) {
            return "OD001";
        } else {
            int maxId = Integer.parseInt(lastOrderId);
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public static String getNewItemId() {
        String lastItemId = DataLayer.getLastItemdId();
        if (lastItemId == null) {
            return "I001";
        } else {
            int maxCode;
            maxCode = Integer.parseInt(DataLayer.getLastItemdId().replace("I", ""));
            maxCode = maxCode + 1;
            String code = "";
            if (maxCode < 10) {
                code = "I00" + maxCode;
            } else if (maxCode < 100) {
                code = "I0" + maxCode;
            } else {
                code = "I" + maxCode;
            }
            return code;
        }
    }


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

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) {
        return DataLayer.saveItem(new ItemTM(code, description, qtyOnHand, unitPrice));
    }

    public static boolean deleteItem(String itemCode) {
        return DataLayer.deleteItem(itemCode);
    }


    public static boolean updateItem(String code, String description, int qtyOnHand, double unitPrice) {
        return DataLayer.updateItem(new ItemTM(code, description, qtyOnHand, unitPrice));
    }



    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean result = DataLayer.saveOrder(order);
            if (!result){
                connection.rollback();
                return false;
            }

            result = DataLayer.saveOrderDetail(order.getOrderId(),orderDetails);
            if (!result){
                connection.rollback();
                return false;
            }

            result = DataLayer.updateQty(orderDetails);
            if (!result){
                connection.rollback();
                return false;
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