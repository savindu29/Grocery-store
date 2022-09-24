package lk.ijse.Groceryshop.model;

import java.util.ArrayList;

public class Order {
    private String id;
    private String date;
    private String customerId;
    private double total;
    private ArrayList<OrderDetail> orderDetailArrayList;

    public Order(String id, String date, String customerId,double total, ArrayList<OrderDetail> orderDetailArrayList) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.total = total;
        this.orderDetailArrayList = orderDetailArrayList;
    }

    public Order() {
    }

    public Order(String id, String date, String customerId,double total) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetail> getOrderDetailArrayList() {
        return orderDetailArrayList;
    }

    public void setOrderDetailArrayList(ArrayList<OrderDetail> orderDetailArrayList) {
        this.orderDetailArrayList = orderDetailArrayList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
