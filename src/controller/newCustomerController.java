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

/**
 * The type New customer controller.
 */
public class newCustomerController implements Initializable {

    /**
     * The Name tf.
     */
    public TextField nameTF;
    /**
     * The Address tf.
     */
    public TextField addressTF;
    /**
     * The Zip tf.
     */
    public TextField zipTF;
    /**
     * The Phone tf.
     */
    public TextField phoneTF;
    /**
     * The Country list.
     */
    public ComboBox<Countries> countryList;

    /**
     * The Added.
     */
    public boolean added = false;
    /**
     * The Index.
     */
    public int index = 0;
    /**
     * The Division id.
     */
    public int divisionID = 0;
    /**
     * The Divisions list.
     */
    public ComboBox<FirstLevelDivision> divisionsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populate();
    }

    /**
     * Back to customer gui.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    /**
     * Add new customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void addNewCustomer(ActionEvent actionEvent) throws IOException {
        String name = nameTF.getText();
        String address = addressTF.getText();
        String postalCode = zipTF.getText();
        String phoneNumber = phoneTF.getText();
        Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());

        try {
            Customer c = new Customer(0, name, address, postalCode, phoneNumber);
            c.setDivisionID(selectDivision());

            String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Last_Update, Division_ID) VALUES(NULL,'" +
                    c.getName() + "','" + c.getAddress() + "','" + c.getPostalCode() + "','" + c.getPhone() + "','" + lastUpdate + "'," + c.getDivisionID() + ");";

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

    /**
     * Populate.
     */
    public void populate() {
        ObservableList<Countries> countries = DBCountries.getAllCountries();
        countryList.setItems(countries);
    }

    /**
     * Select country int.
     *
     * @return the int
     */
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

    /**
     * Select division int.
     *
     * @return the int
     */
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