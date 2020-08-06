package dao;

import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.*;
import entity.SuperEntity;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOType daoType) {
        switch (daoType) {
            case Customer:
                return (T) new CustomerDAOImpl();
            case Item:
                return (T) new ItemDAOImpl();
            case Order:
                return (T) new OrderDAOImpl();
            case OrderDetail:
                return (T) new OrderDetailDAOImpl();
            case QueryDAO:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}



