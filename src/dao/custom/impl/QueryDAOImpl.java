package dao.custom.impl;

import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;
import entity.Customer;
import entity.SuperEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public CustomEntity getOrderDetail(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT c.name,o.id,o.date FROM `Order` o INNER JOIN `Customer` c on o.customerId = c.id WHERE o.id=?");
            pstm.setObject(1, orderId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new CustomEntity(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getDate(3));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CustomEntity getOrderDetail2(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT C.id,O.id,C.name,O.date FROM `Customer` C   INNER JOIN  `Order` O  ON C.id = O.customerId WHERE O.id=?");
            pstm.setObject(1, orderId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new CustomEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4)
                );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CustomEntity> getOrderDetail3(String orderId) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT o.id,o.date,c.id,c.name,SUM(od.qty*od.unitPrice) AS  total\n" +
                "FROM customer c INNER JOIN `order` o on o.customerId = c.id\n" +
                "              INNER JOIN orderdetail od on o.id = od.orderId\n" +
                "WHERE o.id=? GROUP BY o.id");
        pstm.setObject(1, orderId);
        ResultSet rst = pstm.executeQuery();
        ArrayList<CustomEntity> list = new ArrayList<>();
        if (rst.next()) {
            list.add(new CustomEntity(rst.getString(1), rst.getDate(2), rst.getString(3), rst.getString(4), rst.getBigDecimal(5)));
            return list;
        } else return null;
    }






}
