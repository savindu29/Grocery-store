package lk.ijse.Groceryshop.controller;

import lk.ijse.Groceryshop.dao.DataAccessCode;
import lk.ijse.Groceryshop.model.Customer;
import lk.ijse.Groceryshop.view.tm.CustomerTm;
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
import java.util.ArrayList;
import java.util.Optional;

public class CustomerFormController {
    public AnchorPane customerFormContext;
    public TextField txtId;
    public TextField txtSalary;
    public TextField txtAddress;
    public TextField txtName;
    public TableView<CustomerTm> tblCustomerView;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public Button btnSaveCustomer;
    public TextField txtSearch;

    private String searchText="";
    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
    setUi("dashboardForm");
    }
    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) customerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ui+".fxml"))));
        stage.centerOnScreen();
    }
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        searchCustomers(searchText);
        tblCustomerView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if(null!=newValue) {
               setData(newValue);
           }
        });
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            searchCustomers(searchText);
        });
    }

    private void setData(CustomerTm tm) {
        btnSaveCustomer.setText("Update Customer");
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtSalary.setText(String.valueOf(tm.getSalary()));
    }

    private void searchCustomers(String text) {
        String searchText = "%"+text+"%";
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
            ArrayList<Customer> customers = new DataAccessCode().searchCustomer(searchText);
            for (Customer customer: customers) {

                Button btn = new Button("Delete");
                btn.setOnAction(event -> {
                    Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if(buttonType.get()==ButtonType.YES) {
                        try {
                            boolean isDeleted = new DataAccessCode().deleteCustomer(customer.getId());

                            if (isDeleted) {
                                searchCustomers(searchText);
                                new Alert(Alert.AlertType.INFORMATION, "Delete Successfully!!").show();
                                clearFields();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
                CustomerTm tm = new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getSalary(),btn
                );
                tmList.add(tm);

            }
            tblCustomerView.setItems(tmList);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }


    public void saveCustomerOnAction(ActionEvent actionEvent) {
        if(txtId.getText().equals("")){
            new Alert(Alert.AlertType.INFORMATION,"please enter ID!!").show();
            return;
        }
        Customer customer = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()));
        if(btnSaveCustomer.getText().equalsIgnoreCase("Save Customer")){
            try {
                boolean isSaved = new DataAccessCode().saveCustomer(customer);
                if(isSaved){
                   // searchCustomers(searchText);
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION,"Customer Added SuccessFully!!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }
        }else{
            try {
                boolean isUpdated = new DataAccessCode().updateCustomer(customer);
                if(isUpdated){

                    new Alert(Alert.AlertType.INFORMATION,"Customer Updated").show();
                   // searchCustomers(searchText);
                }else{
                    new Alert(Alert.AlertType.ERROR,"Can't Update... something went wrong").show();
                }
            } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }
        }
        searchCustomers(searchText);

    }
    public void clearFields(){
        txtId.clear();
        txtName.clear();
        txtSalary.clear();
        txtAddress.clear();

    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
    clearFields();
    btnSaveCustomer.setText("Save Customer");
    }

}
