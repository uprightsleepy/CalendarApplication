package controller;

import DBAccess.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Customer gui controller.
 */
public class customerGUIController implements Initializable {

    /**
     * The Customer list.
     */
    public TableView<Customer> customerList;
    /**
     * The Id col.
     */
    public TableColumn<Customer, Integer> idCol;
    /**
     * The Name col.
     */
    public TableColumn<Customer, String> nameCol;
    /**
     * The Address col.
     */
    public TableColumn<Customer, String> addressCol;
    /**
     * The Postal col.
     */
    public TableColumn<Customer, String> postalCol;
    /**
     * The Phone col.
     */
    public TableColumn<Customer, String> phoneCol;
    /**
     * The Country col.
     */
    public TableColumn<Customer, String> countryCol;
    /**
     * The Division col.
     */
    public TableColumn<Customer, String> divisionCol;

    /**
     * The Search field.
     */
    public TextField searchField;

    private static Customer customerToModify;
    private static int customerToModifyIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populate();
    }

    /**
     * Back to main.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void backToMain(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will return to the Main Menu. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
    }

    /**
     * Add customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void addCustomer(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/newCustomer.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Add Customer");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Mod customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void modCustomer(ActionEvent actionEvent) throws IOException {

        customerToModify = customerList.getSelectionModel().getSelectedItem();

        if(customerToModify == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no customers selected.");
            alert.showAndWait();
        } else{

            customerToModify = customerList.getSelectionModel().getSelectedItem();
            System.out.println("First_Level_Division ID: " + customerToModify.getDivisionID());
            customerToModifyIndex = DBCustomer.getAllCustomers().indexOf(customerToModify);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modCustomer.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Update Customer");

            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Delete customer.
     */
    public void deleteCustomer() {

        Customer customerToDelete = customerList.getSelectionModel().getSelectedItem();

        if(customerToDelete == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no customers selected.");
            alert.showAndWait();
        } else if(customerToDelete.checkAppointments(customerToDelete.getId())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Can not delete a customer that has appointments.");
            alert.showAndWait();
        } else {

            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to permanently delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK) {

                    String sql = "DELETE FROM customers WHERE Customer_ID = " + customerToDelete.getId();

                    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                    ps.executeUpdate();
                }
            } catch (NullPointerException | SQLException e) {

                e.printStackTrace();
            }

            populate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Deleted");
            alert.setContentText("Customer ID "+ String.valueOf(customerToDelete.getId()) + ", " + customerToDelete.getName() + " | successfully deleted.");
            alert.showAndWait();
        }
    }

    /**
     * Populate.
     *
     * @throws NullPointerException the null pointer exception
     */
    public void populate() throws NullPointerException{

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();

        customerList.setItems(customers);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
    }

    /**
     * Gets customer to modify.
     *
     * @return the customer to modify
     */
    public static Customer getCustomerToModify() {

        return customerToModify;
    }

    /**
     * Gets customer to modify index.
     *
     * @return the customer to modify index
     */
    public static int getCustomerToModifyIndex() {

        return customerToModifyIndex;
    }

    /**
     * Search.
     */
    public void search() {

        try {

            int q = Integer.parseInt(searchField.getText());
            ObservableList<Customer> customers = DBCustomer.lookupCustomer(q);
            customerList.setItems(customers);

            if(searchField.getText().isEmpty()) {

                customerList.setItems(DBCustomer.getAllCustomers());
            }
        } catch (NumberFormatException e) {

            String q = searchField.getText();
            ObservableList<Customer> customers = DBCustomer.lookupCustomer(q);
            customerList.setItems(customers);

            if(searchField.getText().isEmpty()) {

                customerList.setItems(DBCustomer.getAllCustomers());
            }
        }
    }
}
