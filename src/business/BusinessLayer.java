package business;

import com.sun.org.apache.xerces.internal.xs.ShortList;
import dao.CustomerDAO;
import dao.ItemDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessLayer {
    // Generate a new id
    public static String generateNewCustomerId() {
        String lastCustomerId = CustomerDAO.getLastCustomerId().replace("C", "");
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
        String lastOrderId = OrderDAO.getLastOrderId().replace("OD", "");
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
        String lastItemId = ItemDAO.getLastItemdId();
        if (lastItemId == null) {
            return "I001";
        } else {
            int maxCode;
            maxCode = Integer.parseInt(ItemDAO.getLastItemdId().replace("I", ""));
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
        List<Customer> allCustomers = CustomerDAO.findAllCustomers();
        List<CustomerTM> customers = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return customers;
    }

    public static boolean saveCustomer(String id, String name, String address) {
        return CustomerDAO.saveCustomer(new Customer(id, name, address));
    }

    public static boolean deleteCustomer(String customerId) {
        return CustomerDAO.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(String id, String name, String address) {
        return CustomerDAO.updateCustomer(new Customer(id, name, address));
    }

    public static List<ItemTM> getAllItems() {
        List<Item> items = ItemDAO.findAllItems();
        List<ItemTM> allItems = new ArrayList();
        for (Item item : items) {
            allItems.add(new ItemTM(item.getCode(), item.getDescription(),
                    item.getQtyOnHand(), item.getUnitPrice().doubleValue()));
        }
        return allItems;
    }

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) {
        return ItemDAO.saveItem(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }


    public static boolean deleteItem(String itemCode) {
        return ItemDAO.deleteItem(itemCode);
    }


    public static boolean updateItem(String code, String description, int qtyOnHand, double unitPrice) {
        return ItemDAO.updateItem(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }


    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean result = OrderDAO.saveOrder(new Order(order.getOrderId(),
                    Date.valueOf(order.getOrderDate()),
                    order.getCustomerId()));
            if (!result) {
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                result = OrderDetailDAO.saveOrderDetail(new OrderDetail(
                        order.getOrderId(), orderDetail.getCode(),
                        orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
                ));
                if (!result) {
                    connection.rollback();
                    return false;
                }
                Item item = ItemDAO.findItem(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                result = ItemDAO.updateItem(item);
                if (!result) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (Throwable throwables) {
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
