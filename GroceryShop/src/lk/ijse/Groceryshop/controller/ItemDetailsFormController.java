package lk.ijse.Groceryshop.controller;

import lk.ijse.Groceryshop.dao.DataAccessCode;
import lk.ijse.Groceryshop.model.OrderDetail;
import lk.ijse.Groceryshop.view.tm.OrderDetailTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ItemDetailsFormController {
    public AnchorPane orderDetailsContext;
    public TableView<OrderDetailTm> tblDetails;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public Label lblHeading;

    public void initialize(){
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));



    }

    public void loadOrderDetails(String id) throws SQLException, ClassNotFoundException {
        ObservableList<OrderDetailTm> tmList = FXCollections.observableArrayList();
        for (OrderDetail orderDetail :new DataAccessCode().searchOrderDetail())

        {
        if(orderDetail.getOrderId().equalsIgnoreCase(id)) {
            int tempQty = orderDetail.getQty();
            double tempUnitPrice = orderDetail.getUnitPrice();
            double total = tempQty*tempUnitPrice;
            tmList.add(new OrderDetailTm(
                    orderDetail.getOrderId(),
                    orderDetail.getItemCode(),
                    tempQty,tempUnitPrice,total));
        }
        }
        tblDetails.setItems(tmList);
        lblHeading.setText(id+" - "+lblHeading.getText());

        return;
    }
    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) orderDetailsContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ui+".fxml"))));

    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
    setUi("OrderDetailsform");
    }
}
