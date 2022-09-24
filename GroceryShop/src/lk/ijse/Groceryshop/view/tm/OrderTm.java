package lk.ijse.Groceryshop.view.tm;

import javafx.scene.control.Button;

import java.util.Date;

public class OrderTm {
    private String orderId;
    private String customerId;
    private Date date;
    private double total;
    private Button btn;

    public OrderTm() {
    }

    public OrderTm(String orderId, String customerId, Date date, double total, Button btn) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.date = date;
        this.total = total;
        this.btn = btn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
