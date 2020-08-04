import dao.CustomerDAO;
import dao.OrderDAO;
import entity.Customer;

public class CustomerDAOTest {

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        assert customerDAO.findAllCustomers().size() == 10 : "Hello";





    }

}
