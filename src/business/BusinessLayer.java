//package business;
//
//import dao.DAOFactory;
//import dao.DAOType;
//import dao.custom.CustomerDAO;
//import dao.custom.ItemDAO;
//import dao.custom.OrderDAO;
//import dao.custom.OrderDetailDAO;
//import dao.custom.impl.CustomerDAOImpl;
//import dao.custom.impl.ItemDAOImpl;
//import dao.custom.impl.OrderDAOImpl;
//import dao.custom.impl.OrderDetailDAOImpl;
//import db.DBConnection;
//import entity.Customer;
//import entity.Item;
//import entity.Order;
//import entity.OrderDetail;
//import util.CustomerTM;
//import util.ItemTM;
//import util.OrderDetailTM;
//import util.OrderTM;
//
//import java.math.BigDecimal;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BusinessLayer {
//    // Generate a new id
//    public  String generateNewCustomerId() throws Exception {
//        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
//        String lastCustomerId = customerDAO.getLastCustomerId().replace("C", "");
//        if (lastCustomerId == null) {
//            return "C001";
//        } else {
//            int maxId = Integer.parseInt(lastCustomerId);
//            maxId = maxId + 1;
//            String id = "";
//            if (maxId < 10) {
//                id = "C00" + maxId;
//            } else if (maxId < 100) {
//                id = "C0" + maxId;
//            } else {
//                id = "C" + maxId;
//            }
//            return id;
//        }
//    }
//
//    public  String generateNewOrderId() throws Exception {
//        OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOType.Order);
//        String lastOrderId = orderDAO.getLastOrderId().replace("OD", "");
//        if (lastOrderId == null) {
//            return "OD001";
//        } else {
//            int maxId = Integer.parseInt(lastOrderId);
//            maxId = maxId + 1;
//            String id = "";
//            if (maxId < 10) {
//                id = "OD00" + maxId;
//            } else if (maxId < 100) {
//                id = "OD0" + maxId;
//            } else {
//                id = "OD" + maxId;
//            }
//            return id;
//        }
//    }
//
//    public  String getNewItemId() throws Exception {
//        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
//        String lastItemId = itemDAO.getLastItemId();
//        if (lastItemId == null) {
//            return "I001";
//        } else {
//            int maxCode;
//            maxCode = Integer.parseInt(itemDAO.getLastItemId().replace("I", ""));
//            maxCode = maxCode + 1;
//            String code = "";
//            if (maxCode < 10) {
//                code = "I00" + maxCode;
//            } else if (maxCode < 100) {
//                code = "I0" + maxCode;
//            } else {
//                code = "I" + maxCode;
//            }
//            return code;
//        }
//    }
//
//    public static List<CustomerTM> getAllCustomers() throws Exception {
//        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
//        List<Customer> allCustomers = customerDAO.findAll();
//        List<CustomerTM> customers = new ArrayList<>();
//        for (Customer customer : allCustomers) {
//            customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
//        }
//        return customers;
//    }
//
//
//    public  boolean saveCustomer(String id, String name, String address) throws Exception {
//        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
//        return customerDAO.save(new Customer(id, name, address));
//    }
//
//    public  boolean deleteCustomer(String customerId) throws Exception {
//        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
//        return customerDAO.delete(customerId);
//    }
//
//    public  boolean updateCustomer(String id, String name, String address) throws Exception {
//        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
//        return customerDAO.update(new Customer(id, name, address));
//    }
//
//    public  List<ItemTM> getAllItems() throws Exception {
//        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
//        List<Item> items = itemDAO.findAll();
//        List<ItemTM> allItems = new ArrayList();
//        for (Item item : items) {
//            allItems.add(new ItemTM(item.getCode(), item.getDescription(),
//                    item.getQtyOnHand(), item.getUnitPrice().doubleValue()));
//        }
//        return allItems;
//    }
//
//    public  boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) throws Exception {
//        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
//        return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
//    }
//
//
//    public  boolean deleteItem(String itemCode) throws Exception {
//        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
//        return itemDAO.delete(itemCode);
//    }
//
//
//    public  boolean updateItem(String code, String description, int qtyOnHand, double unitPrice) throws Exception {
//        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
//        return itemDAO.update(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
//    }
//
//
//    public  boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//            OrderDAO orderDAO= (OrderDAO) DAOFactory.getInstance().getDAO(DAOType.Order);
//            boolean result = orderDAO.save(new Order(order.getOrderId(),
//                    Date.valueOf(order.getOrderDate()),
//                    order.getCustomerId()));
//            if (!result) {
//                connection.rollback();
//                return false;
//            }
//            for (OrderDetailTM orderDetail : orderDetails) {
//                OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOType.OrderDetail);
//                result = orderDetailDAO.save(new OrderDetail(
//                        order.getOrderId(), orderDetail.getCode(),
//                        orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
//                ));
//                if (!result) {
//                    connection.rollback();
//                    return false;
//                }
//                ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
//                Item item = itemDAO.find(orderDetail.getCode());
//                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
//                result = itemDAO.update(item);
//                if (!result) {
//                    connection.rollback();
//                    return false;
//                }
//            }
//            connection.commit();
//            return true;
//        } catch (Throwable throwables) {
//            throwables.printStackTrace();
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return false;
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }
//}
