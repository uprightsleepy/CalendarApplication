package controller;

import DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class newCustomerController implements Initializable {

    public TextField nameTF;
    public TextField addressTF;
    public TextField zipTF;
    public TextField phoneTF;
    public boolean added = false;
    public ComboBox countryList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void backToCustomerGUI(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will return to the Customer Menu. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerGUI.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Customer Menu");
            stage.setScene(scene);

            stage.show();
        }
    }

    public void addNewCustomer(ActionEvent actionEvent) {
        String name = nameTF.getText();
        String address = addressTF.getText();
        String postalCode = zipTF.getText();
        String phoneNumber = phoneTF.getText();
        Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());

        try {
            Customer c = new Customer(0, name, address, postalCode, phoneNumber);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Last_Update, Division_ID) VALUES(NULL,'" +
                    c.getName() + "','" + c.getAddress() + "','" + c.getPostalCode() + "','" + c.getPhone() + "','" + lastUpdate + "','60');";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            added = true;
        } catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            System.out.println(e.getMessage());
            alert.showAndWait();
        }

        if(added) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Customer was successfully added to the database.");
            alert.showAndWait();
        }
    }

    public void removeCustomer(ActionEvent actionEvent) {

    }
}
