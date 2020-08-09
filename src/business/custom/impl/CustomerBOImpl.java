package business.custom.impl;

import business.custom.CustomerBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.CustomerDAO;
import entity.Customer;
import util.CustomerTM;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    public  String generateNewCustomerId() throws Exception {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
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

    public  List<CustomerTM> getAllCustomers() throws Exception {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
        List<Customer> allCustomers = customerDAO.findAll();
        List<CustomerTM> customers = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return customers;
    }


    public  boolean saveCustomer(String id, String name, String address) throws Exception {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
        return customerDAO.save(new Customer(id, name, address));
    }

    public  boolean deleteCustomer(String customerId) throws Exception {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
        return customerDAO.delete(customerId);
    }

    public  boolean updateCustomer(String id, String name, String address) throws Exception {
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.Customer);
        return customerDAO.update(new Customer(id, name, address));
    }






}
