import dao.CustomerDAO;
import dao.impl.CustomerDAOImpl;

public class CustomerDAOTest {

    public static void main(String[] args) {
        CustomerDAO customerDAOImpl = new CustomerDAOImpl();
        assert customerDAOImpl.findAllCustomers().size() == 10 : "Hello";





    }

}
