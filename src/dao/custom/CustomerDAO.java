package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    String getLastCustomerId() throws Exception;





}
