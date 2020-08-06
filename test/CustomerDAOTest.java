import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;

public class CustomerDAOTest {

    public static void main(String[] args) {
        CustomerDAO customerDAOImpl = new CustomerDAOImpl();
        assert customerDAOImpl.findAll().size() == 10 : "Hello";





    }

}
