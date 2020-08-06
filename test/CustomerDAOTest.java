import dao.DAOFactory;
import dao.DAOType;
import dao.custom.CustomerDAO;
import dao.custom.QueryDAO;
import dao.custom.impl.CustomerDAOImpl;

import javax.management.Query;

public class CustomerDAOTest {

    public static void main(String[] args) {
        CustomerDAO customerDAOImpl = new CustomerDAOImpl();
        assert customerDAOImpl.findAll().size() == 10 : "Hello";

        //DAOFactory.getInstance().getDAO(CustomerDAO);






    }

}
