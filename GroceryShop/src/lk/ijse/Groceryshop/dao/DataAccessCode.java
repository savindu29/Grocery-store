package lk.ijse.Groceryshop.dao;

import lk.ijse.Groceryshop.db.DBConnection;
import lk.ijse.Groceryshop.model.Customer;
import lk.ijse.Groceryshop.model.Item;
import lk.ijse.Groceryshop.model.Order;
import lk.ijse.Groceryshop.model.OrderDetail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataAccessCode {
    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");
        stm.setString(1,customer.getId());
        stm.setString(2,customer.getName());
        stm.setString(3,customer.getAddress());
        stm.setDouble(4,customer.getSalary());
        return stm.executeUpdate()>0;
    }
    public ArrayList<Customer> searchCustomer(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = new ArrayList<>();
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  customer WHERE id LIKE ? || name LIKE ? || address LIKE ?");
        stm.setString(1,searchText);
        stm.setString(2,searchText);
        stm.setString(3,searchText);
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)));
        }
        return customers;

    }
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM customer WHERE id=?");
        stm.setString(1,id);
        return stm.executeUpdate() > 0;

    }
    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id =?");

        stm.setString(1,customer.getName());
        stm.setString(2,customer.getAddress());
        stm.setDouble(3,customer.getSalary());
        stm.setString(4,customer.getId());
        return stm.executeUpdate()>0;
    }
    public boolean saveItem(Item item) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
        stm.setString(1,item.getCode());
        stm.setString(2,item.getDescription());
        stm.setDouble(3,item.getUnitPrice());
        stm.setInt(4,item.getQtyOnHand());
        return stm.executeUpdate()>0;
    }
    public ArrayList<Item> searchItem(String text) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = new ArrayList<>();
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  item WHERE code LIKE ? || description LIKE ?");
        stm.setString(1,text);
        stm.setString(2,text);
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
            items.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)));
        }
        return items;

    }
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM item WHERE code=?");
        stm.setString(1,code);
        return stm.executeUpdate() > 0;

    }
    public boolean updateItem(Item item) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code =?");

        stm.setString(1,item.getDescription());
        stm.setDouble(2,item.getUnitPrice());
        stm.setInt(3,item.getQtyOnHand());
        stm.setString(4,item.getCode());
        return stm.executeUpdate()>0;
    }
    public boolean updateItem(String itemCode,int qtyOnHand) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET  qtyOnHand=? WHERE code =?");

        stm.setInt(1,qtyOnHand);
        stm.setString(2,itemCode);
        return stm.executeUpdate()>0;
    }


    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO orders VALUES(?,?,?,?)");
        stm.setString(1,order.getId());
        stm.setDate(2, Date.valueOf(order.getDate()));
        stm.setString(3,order.getCustomerId());
        stm.setDouble(4,order.getTotal());
        return stm.executeUpdate()>0;
    }
    public ArrayList<Order> searchOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  orders");
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
            orders.add(new Order( rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)));
        }
        return orders;

    }
    public boolean addOrderDetails(OrderDetail detail) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO orderdetail VALUES(?,?,?,?)");
        stm.setString(1,detail.getOrderId());
        stm.setString(2,detail.getItemCode());
        stm.setInt(3,detail.getQty());
        stm.setDouble(4,detail.getUnitPrice());
        return stm.executeUpdate()>0;
    }
    public ArrayList<OrderDetail> searchOrderDetail() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM  orderdetail");
        ResultSet rst = stm.executeQuery();
        while(rst.next()){
            orderDetailArrayList.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)));
        }
        return orderDetailArrayList;

    }




}
