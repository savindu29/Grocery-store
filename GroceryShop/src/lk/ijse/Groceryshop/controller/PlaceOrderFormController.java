package lk.ijse.Groceryshop.controller;

import lk.ijse.Groceryshop.db.DBConnection;
import lk.ijse.Groceryshop.model.Customer;
import lk.ijse.Groceryshop.model.Item;
import lk.ijse.Groceryshop.view.tm.CartTm;
import lk.ijse.Groceryshop.dao.DataAccessCode;
import lk.ijse.Groceryshop.model.Order;
import lk.ijse.Groceryshop.model.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class PlaceOrderFormController {
    public AnchorPane placeOrderContext;
    public TextField lblDate;
    public TextField txtQty;
    public ComboBox cmbItemCode;
    public ComboBox cmbCustomerId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public TableView<CartTm> tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;
    public Label lblTotal;
    public TextField txtOrderId;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));


        loadDate();
        loadCustomerIds("");
        loadItemCodes("");
        setOrderId();
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null!=newValue){
                setCustomerDetails();
                
            }
        });
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null!=newValue){
                setItemDetails();

            }
        });
    }

    private void  setOrderId() {
        ArrayList<Order>  orders = new ArrayList<>();
        try {
            orders=new DataAccessCode().searchOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String num = String.format("%03d" , orders.size()+1);
        txtOrderId.setText("D"+num);

    }


    private void setItemDetails() {
        try {

            Item item = new DataAccessCode().searchItem((String) cmbItemCode.getValue()).get(0);
            txtDescription.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCustomerDetails() {
        try {

            Customer customer = new DataAccessCode().searchCustomer((String) cmbCustomerId.getValue()).get(0);

            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadItemCodes(String text) {
        String searchText = "%"+text+"%";
        try {
            for (Item item:new DataAccessCode().searchItem(searchText)
                 ) {
                cmbItemCode.getItems().add(item.getCode());

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerIds(String text) {
        String searchText = "%"+text+"%";
        try {
            for (Customer customer :  new DataAccessCode().searchCustomer(searchText)) {
                cmbCustomerId.getItems().add(customer.getId());

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadDate() {
        lblDate.setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));


    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
    setUi("DashboardForm");
    }
    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) placeOrderContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ui+".fxml"))));

    }
    ObservableList<CartTm> obList = FXCollections.observableArrayList();
    public void addToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = unitPrice*qty;

        Button btn = new Button("delete");
        if(!checkQty(String.valueOf(cmbItemCode.getValue()),qty)){
            new Alert(Alert.AlertType.WARNING,"No enough items on Stock please double check it").show();
            return;
        }
        int row = isAlreadyExists((String) cmbItemCode.getValue());
        if(row==-1){
            CartTm tm = new CartTm(
                    (String) cmbItemCode.getValue(),
                    txtDescription.getText(),
                    unitPrice,
                    qty,
                    total,btn);

            obList.add(tm);
            tblCart.setItems(obList);
        }else{
            int tempQty = obList.get(row).getQty()+qty;
            if(!checkQty(String.valueOf(cmbItemCode.getValue()),tempQty)){
                new Alert(Alert.AlertType.WARNING,"No enough items on Stock please double check it").show();
                return;
            }
            double tempTotal = unitPrice*tempQty;


            obList.get(row).setQty(tempQty);
            obList.get(row).setTotal(tempTotal);
           tblCart.refresh();
        }
        clearItemFields();
        calculateTotal();

        for (CartTm tm :obList){
            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure",
                        ButtonType.YES,ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if(buttonType.get()==ButtonType.YES){
                    obList.remove(tm);
                    calculateTotal();
                    cmbItemCode.requestFocus();

                }
            });
        }


    }
    private boolean checkQty(String  code,int qty) throws SQLException, ClassNotFoundException {
        String searchText = "%"+code+"%";
        Item item = new DataAccessCode().searchItem(searchText).get(0);
        if(item.getQtyOnHand()>=qty){
            return true;
        }
        return false;
    }

    private void clearItemFields() {
        txtQty.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        cmbItemCode.setValue(null);

        cmbItemCode.requestFocus();
    }
    private void clearAllFields(){
        clearItemFields();
        cmbCustomerId.setValue(null);
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        obList = FXCollections.observableArrayList();
        System.out.println(obList.size());
        tblCart.setItems(obList);
        lblTotal.setText("");
    }


    private int isAlreadyExists(String code){
        for (int i = 0; i < obList.size(); i++) {
          if(obList.get(i).getCode().equals(code)){
              return i;
          }
        }
        return -1;
    }
    private void calculateTotal(){
        double total = 0.00;
        for (CartTm tm:obList)
        {
            total+=tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }



    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Auto commit false
            DBConnection.getInstance().getConnection().setAutoCommit(false);

            String orderId = txtOrderId.getText();
            String orderDate = lblDate.getText();
            String customerId = (String) cmbCustomerId.getValue();
            double orderTotal = Double.parseDouble(lblTotal.getText());
            ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
            for (CartTm tm : obList) {
                orderDetailArrayList.add(new OrderDetail(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice()));
            }
            Order order = new Order(orderId, orderDate, customerId,orderTotal, orderDetailArrayList);


            boolean isSaved = new DataAccessCode().saveOrder(order);
            if (isSaved) {

                boolean isAdded = addOrderDetails(orderDetailArrayList);
                if(isAdded){
                    if(updateStock(orderDetailArrayList)) {
                        DBConnection.getInstance().getConnection().commit();
                        new Alert(Alert.AlertType.INFORMATION,
                                "Placed Order").show();
                    }else{
                        new Alert(Alert.AlertType.ERROR,
                                "No enough items on Stock please double check it").show();
                    }
                }
            }
        }
        finally {
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }

        setOrderId();
        clearAllFields();




    }
    private boolean addOrderDetails(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < details.size(); i++) {
            boolean isAdded= new DataAccessCode().addOrderDetails(details.get(i));
            if(!isAdded){
                return false;
            }
        }
        return true;
    }
    private boolean updateStock(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        String searchText = "%"+""+"%";
        for (OrderDetail detail:details)
        {
            int qty = detail.getQty();
            int qtyOnHand=0;
            ArrayList<Item> items = new DataAccessCode().searchItem(searchText);
            for (int i = 0; i < items.size(); i++) {
                if(items.get(i).getCode().equalsIgnoreCase(detail.getItemCode())){
                    qtyOnHand = items.get(i).getQtyOnHand();
                    break;
                }
            }
            if(qty<=qtyOnHand){
                new DataAccessCode().updateItem(detail.getItemCode(),(qtyOnHand-qty));
            }else{
                return false;
            }
        }
        return true;
    }

}
