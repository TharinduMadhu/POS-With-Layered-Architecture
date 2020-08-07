package dao.custom.impl;

import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;
import entity.SuperEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public CustomEntity getOrderDetail(String orderId)  {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("SELECT c.name,o.id,o.date FROM `Order` o INNER JOIN `Customer` c on o.customerId = c.id WHERE o.id=?");
                pstm.setObject(1, orderId);
                ResultSet resultSet = pstm.executeQuery();
                if (resultSet.next()){
                    return new CustomEntity(resultSet.getString(1), resultSet.getString(2),
                            resultSet.getDate(3));
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
