package dao.custom;

import dao.SuperDAO;
import entity.Customer;

import java.util.List;

public interface CustomerDAO extends SuperDAO<Customer,String> {
    String getLastCustomerId();





}
