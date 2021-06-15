package controller;

import DBAccess.DBCountries;
import DBAccess.DBFirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.FirstLevelDivision;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class newCustomerController implements Initializable {

    public TextField nameTF;
    public TextField addressTF;
    public TextField zipTF;
    public TextField phoneTF;
    public ComboBox<Countries> countryList;

    public boolean added = false;
    public int index = 0;
    public int divisionID = 0;
    public ComboBox<FirstLevelDivision> divisionsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populate();
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

    public void addNewCustomer(ActionEvent actionEvent) throws IOException {
        String name = nameTF.getText();
        String address = addressTF.getText();
        String postalCode = zipTF.getText();
        String phoneNumber = phoneTF.getText();
        Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());

        try {
            Customer c = new Customer(0, name, address, postalCode, phoneNumber);

            String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Last_Update, Division_ID) VALUES(NULL,'" +
                    c.getName() + "','" + c.getAddress() + "','" + c.getPostalCode() + "','" + c.getPhone() + "','" + lastUpdate + "'," + selectDivision() + ");";

            c.setDivisionID(selectDivision());
            System.out.println("Division ID: " + c.getDivisionID());

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            added = true;
        } catch (NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            System.out.println(e.getMessage());
            alert.showAndWait();
        }

        if (added) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Customer was successfully added to the database.");
            alert.showAndWait();
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerGUI.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);
        stage.show();
    }
    public void populate() {
        ObservableList<Countries> countries = DBCountries.getAllCountries();
        countryList.setItems(countries);
    }

    public int selectCountry() {

        ObservableList<FirstLevelDivision> divisions;
        index = countryList.getSelectionModel().getSelectedIndex();

        if(index == 0){

            divisions = DBFirstLevelDivisions.getAllUSDivisions();
            divisionsList.setItems(divisions);
        } else if(index == 1) {

            divisions = DBFirstLevelDivisions.getAllUKDivisions();
            divisionsList.setItems(divisions);
        } else {

            divisions = DBFirstLevelDivisions.getAllCanadaDivisions();
            divisionsList.setItems(divisions);
        }
        return index;
    }

    public int selectDivision() {
        String divisionName = divisionsList.getSelectionModel().getSelectedItem().getName();

        try {
            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division ='" + divisionName + "';";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(divisionID);
        return divisionID;
    }
}
