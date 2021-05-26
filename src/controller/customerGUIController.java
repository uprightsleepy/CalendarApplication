package controller;

import DBAccess.DBCountries;
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
import model.Countries;
import model.Customer;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerGUIController implements Initializable {

    public TableView<Customer> customerList;
    public TableColumn<Customer, Integer> idCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, String> postalCol;
    public TableColumn<Customer, String> phoneCol;
    public TableColumn<Customer, String> locationCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populate();
    }

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

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/newCustomer.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Add Customer");
        stage.setScene(scene);

        stage.show();
    }

    public void modCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/modCustomer.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Modify Customer");
        stage.setScene(scene);

        stage.show();
    }

    public void deleteCustomer() {

        Customer customerToDelete = customerList.getSelectionModel().getSelectedItem();
        if(customerToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no customers selected.");
            alert.showAndWait();
        } else {
            try {
                String sql = "DELETE FROM customers WHERE Customer_ID = " + customerToDelete.getId();

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.executeUpdate();
            } catch (NullPointerException | SQLException e) {
                e.printStackTrace();
            }

            populate();
        }
    }

    public void populate() throws NullPointerException{

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();
        System.out.println(customers);

        customerList.setItems(customers);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }
}
