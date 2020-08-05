package dao;

import entity.Customer;

import java.util.List;

public interface CustomerDAO {
   public abstract  List<Customer> findAllCustomers();
    public abstract Customer findCustomer(String customerId);
    public abstract boolean saveCustomer(Customer customer);
    public abstract boolean updateCustomer(Customer customer);
    public abstract boolean deleteCustomer(String customerId);
    public abstract String getLastCustomerId();





}
