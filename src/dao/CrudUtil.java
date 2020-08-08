package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrudUtil {

    public static boolean executeUpdate(String sql,Object... param) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        int i = 0;
        for (Object  pars : param) {
            i++;
            pstm.setObject(i, param);
        }
        return pstm.executeUpdate() > 0;
    }


//    public static boolean executeUpdate(String sql, ArrayList<String> param) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        int i = 0;
//        for (String pars : param) {
//            i++;
//            pstm.setObject(i, param);
//        }
//        return pstm.executeUpdate() > 0;
//    }

//    public static ResultSet executeQuery(String sql, ArrayList<String> params) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        int i = 0;
//        for (String p : params) {
//            i++;
//            pstm.setObject(i, p);
//        }
//        ResultSet rs = pstm.executeQuery();
//        return rs;
//    }


}
