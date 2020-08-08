package dao.custom.impl;

import dao.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudUtilTest {
    public static void main(String[] args) throws SQLException {
//        ArrayList<String> params1 = new ArrayList<>();
//        params1.add("C016");
//        params1.add("gh");
//        params1.add("kj");

        boolean result = CrudUtil.executeUpdate("INSERT INTO  item VALUES (code=?,description=?,qtyOnHand=?,unitPrice=?)","I011","Pelwatte","124",450.0);
        System.out.println(result);

//        ArrayList<String> params2 = new ArrayList<>();
//        ResultSet rs = CrudUtil.executeQuery("SELECT * FROM customer","");
//        System.out.println(rs.next());
    }
}
