package lk.ijse.Groceryshop.controller;

import lk.ijse.Groceryshop.dao.DataAccessCode;
import lk.ijse.Groceryshop.model.Item;
import lk.ijse.Groceryshop.view.tm.ItemTm;
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

public class ItemFormController {

    public AnchorPane itemFormContext;
    public TextField txtCode;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public Button btnSaveItem;
    public TextField txtSearch;
    public TableView tblItemView;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colOption;
    private String searchText="";
    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("dashboardForm");
    }
    private void setUi(String ui) throws IOException {
        Stage stage = (Stage) itemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+ui+".fxml"))));
        stage.centerOnScreen();
    }
    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        searchItems(searchText);

        tblItemView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(null!=newValue) {
                setData((ItemTm) newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            searchItems(searchText);
        });
    }

    private void setData(ItemTm tm) {
        btnSaveItem.setText("Update Item");
        txtCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
    }

    private void searchItems(String text) {
        String searchText = "%"+text+"%";
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        try {
            ArrayList<Item> items = new DataAccessCode().searchItem(searchText);
            for (Item item: items) {
                Button btn = new Button("Delete");
                btn.setOnAction(event -> {
                    Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
                            ButtonType.YES, ButtonType.NO).showAndWait();
                    if(buttonType.get()==ButtonType.YES) {
                        try {
                            boolean isDeleted = new DataAccessCode().deleteItem(item.getCode());

                            if (isDeleted) {
                                searchItems(searchText);
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
                ItemTm tm = new ItemTm(
                        item.getCode(),
                        item.getDescription(),
                        item.getUnitPrice(),
                        item.getQtyOnHand(),
                        btn
                );
                tmList.add(tm);

            }
            tblItemView.setItems(tmList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveItemOnAction(ActionEvent actionEvent) {
        if(txtCode.getText().equals("")){
            new Alert(Alert.AlertType.INFORMATION,"please enter Code!!").show();
            return;
        }
        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText()));
        if(btnSaveItem.getText().equalsIgnoreCase("Save item")){
            try {
                boolean isSaved = new DataAccessCode().saveItem(item);
                if(isSaved){

                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION,"Item Added SuccessFully!!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }
        }else{
            try {
                boolean isUpdated = new DataAccessCode().updateItem(item);
                if(isUpdated){

                    new Alert(Alert.AlertType.INFORMATION,"Item Updated").show();
                    // searchCustomers(searchText);
                }else{
                    new Alert(Alert.AlertType.ERROR,"Can't Update... something went wrong").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }
        }
        searchItems(searchText);

    }
    public void clearFields(){
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

    }

    public void newItemOnAction(ActionEvent actionEvent) {
        clearFields();
        btnSaveItem.setText("Save Item");
    }

}
