package lk.ijse.Groceryshop.controller;

import lk.ijse.Groceryshop.dao.DataAccessCode;
import lk.ijse.Groceryshop.model.Order;
import lk.ijse.Groceryshop.view.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class OrderDetailsformController {
    public AnchorPane orderDetailsContext;
    public TableColumn colOrderId;
    public TableView<OrderTm> tblOverview;
    public TableColumn colCustomerId;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colOption;

    public void initialize(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadOrderDetails();
    }

    private void loadOrderDetails() {
        ObservableList<OrderTm> tmList= FXCollections.observableArrayList();
        try {
            for (Order order:new DataAccessCode().searchOrders())
            {
                Button btn = new Button("View More");
                OrderTm tm = new OrderTm(order.getId(),order.getCustomerId(), Date.valueOf(order.getDate()),order.getTotal(),btn);
                tmList.add(tm);

                btn.setOnAction(event -> {
                    try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ItemDetailsForm.fxml"));

                        Parent parent=loader.load();
                        ItemDetailsFormController controller = loader.getController();
                        controller.loadOrderDetails(tm.getOrderId());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(parent));
                        stage.show();

                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        tblOverview.setItems(tmList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }
    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) orderDetailsContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ui+".fxml"))));

    }
}
