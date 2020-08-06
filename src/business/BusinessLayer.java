package business;

import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
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
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(1);
        String lastCustomerId = customerDAO.getLastCustomerId().replace("C", "");
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
        OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(3);
        String lastOrderId = orderDAO.getLastOrderId().replace("OD", "");
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
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(2);
        String lastItemId = itemDAO.getLastItemId();
        if (lastItemId == null) {
            return "I001";
        } else {
            int maxCode;
            maxCode = Integer.parseInt(itemDAO.getLastItemId().replace("I", ""));
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
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(1);
        List<Customer> allCustomers = customerDAO.findAll();
        List<CustomerTM> customers = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return customers;
    }


    public static boolean saveCustomer(String id, String name, String address) {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(1);
        return customerDAO.save(new Customer(id, name, address));
    }

    public static boolean deleteCustomer(String customerId) {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(1);
        return customerDAO.delete(customerId);
    }

    public static boolean updateCustomer(String id, String name, String address) {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(1);
        return customerDAO.update(new Customer(id, name, address));
    }

    public static List<ItemTM> getAllItems() {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(2);
        List<Item> items = itemDAO.findAll();
        List<ItemTM> allItems = new ArrayList();
        for (Item item : items) {
            allItems.add(new ItemTM(item.getCode(), item.getDescription(),
                    item.getQtyOnHand(), item.getUnitPrice().doubleValue()));
        }
        return allItems;
    }

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(2);
        return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }


    public static boolean deleteItem(String itemCode) {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(2);
        return itemDAO.delete(itemCode);
    }


    public static boolean updateItem(String code, String description, int qtyOnHand, double unitPrice) {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(2);
        return itemDAO.update(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }


    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            OrderDAO orderDAO= (OrderDAO) DAOFactory.getInstance().getDAO(3);
            boolean result = orderDAO.save(new Order(order.getOrderId(),
                    Date.valueOf(order.getOrderDate()),
                    order.getCustomerId()));
            if (!result) {
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(4);
                result = orderDetailDAO.save(new OrderDetail(
                        order.getOrderId(), orderDetail.getCode(),
                        orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
                ));
                if (!result) {
                    connection.rollback();
                    return false;
                }
                ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(2);
                Item item = itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                result = itemDAO.update(item);
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
