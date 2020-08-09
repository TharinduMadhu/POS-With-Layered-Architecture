package business.custom;

import business.SuperBO;
import dao.SuperDAO;
import util.CustomerTM;

import java.util.List;

public interface CustomerBO extends SuperBO {
    public String generateNewCustomerId() throws Exception;
    public  List<CustomerTM> getAllCustomers() throws Exception;
    public  boolean saveCustomer(String id, String name, String address) throws Exception;
    public boolean deleteCustomer(String customerId) throws Exception;
    public boolean updateCustomer(String id, String name, String address) throws Exception;



}
