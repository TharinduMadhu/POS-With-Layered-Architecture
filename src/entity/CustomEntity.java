package entity;

import java.math.BigDecimal;
import java.util.Date;

public class CustomEntity implements SuperEntity{
    private String customerId;
    private String orderId;
    private String customerName;
    private Date orderDate;
    private BigDecimal total;

    public CustomEntity() {
    }

//    @Override
//    public String toString() {
//        return "CustomEntity{" +
//                "orderId='" + orderId + '\'' +
//                ", customerName='" + customerName + '\'' +
//                ", orderDate=" + orderDate +
//                '}';
//    }

    //Constructor depends on the join query
    //No of constructors equals to the no of join queries
    public CustomEntity(String customerName,String orderId, Date orderDate) {
        this.customerName = customerName;
        this.orderId = orderId;
        this.orderDate = orderDate;
    }

    public CustomEntity(String customerId, String orderId, String customerName, Date orderDate) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "customerId='" + customerId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", total=" + total +
                '}';
    }







    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public CustomEntity(String orderId, Date orderDate, String customerId, String customerName, BigDecimal total) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.total = total;
    }



}
