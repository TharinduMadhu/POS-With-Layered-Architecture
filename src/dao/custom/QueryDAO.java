package dao.custom;

import dao.SuperDAO;
import db.DBConnection;
import entity.CustomEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    CustomEntity getOrderDetail(String orderId) throws Exception;
    CustomEntity getOrderDetail2(String orderId) throws Exception;
    List<CustomEntity> getOrderDetail3(String orderId) throws Exception;

//    public static CustomEntity getOrderDetail2(){
//
//    }





}








