package dao.custom.impl;

import dao.DAOFactory;
import dao.DAOType;
import dao.custom.QueryDAO;
import entity.CustomEntity;

import javax.management.Query;
import java.util.List;

public class QueryDAOImplTest {

    public static void main(String[] args) throws Exception {

        QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.Query);
        List<CustomEntity> list = queryDAO.getOrderDetail3("OD002");
        System.out.println(list);







    }


}
