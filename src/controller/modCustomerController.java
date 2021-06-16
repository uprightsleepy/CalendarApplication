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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class modCustomerController implements Initializable {

    public int index;
    public int originalCountry;
    public ComboBox<FirstLevelDivision> divisionsList;
    public ComboBox<Countries> countryList;
    public TextField nameTF;
    public TextField addressTF;
    public TextField zipTF;
    public TextField phoneTF;

    boolean added = false;

    private final Customer modifiedCustomer = customerGUIController.getCustomerToModify();

    public int originalDivision = modifiedCustomer.getDivisionID();

    int divisionID;
    String divisionName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Original Division: " + originalDivision);

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

    public void setOriginalCountrySelection() {

        originalCountry = modifiedCustomer.getCountryID(divisionID);

        if(originalCountry == 1) {

            countryList.getSelectionModel().select(0);
        } else if(originalCountry == 2) {

            countryList.getSelectionModel().select(1);
        } else {

            countryList.getSelectionModel().select(2);
        }
    }

    public void selectCountry() {

        ObservableList<FirstLevelDivision> divisions;
        index = countryList.getSelectionModel().getSelectedIndex();


        if(index == 0){

            divisions = DBFirstLevelDivisions.getAllUSDivisions();
            divisionsList.setItems(divisions);
            divisionsList.getSelectionModel().select(0);
        } else if(index == 1) {

            divisions = DBFirstLevelDivisions.getAllUKDivisions();
            divisionsList.setItems(divisions);
            divisionsList.getSelectionModel().select(0);

        } else {

            divisions = DBFirstLevelDivisions.getAllCanadaDivisions();
            divisionsList.setItems(divisions);
            divisionsList.getSelectionModel().select(0);
        }
    }

    public void populate() {

        nameTF.setText(modifiedCustomer.getName());
        addressTF.setText(modifiedCustomer.getAddress());
        zipTF.setText(modifiedCustomer.getPostalCode());
        phoneTF.setText(modifiedCustomer.getPhone());

        ObservableList<Countries> countries = DBCountries.getAllCountries();
        countryList.setItems(countries);

        setOriginalCountrySelection();
        getDivision();
    }

    public void getDivision() {

        if(originalCountry == 1) {

            divisionsList.setItems(DBFirstLevelDivisions.getAllUSDivisions());
        } else if(originalCountry == 2) {

            divisionsList.setItems(DBFirstLevelDivisions.getAllUKDivisions());
        } else {

            divisionsList.setItems(DBFirstLevelDivisions.getAllCanadaDivisions());
        }
    }

    public void update(ActionEvent actionEvent) throws IOException {

        String name = nameTF.getText();
        String address = addressTF.getText();
        String postalCode = zipTF.getText();
        String phoneNumber = phoneTF.getText();
        Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());

        try {

            Customer c = new Customer(modifiedCustomer.getId(), name, address, postalCode, phoneNumber,modifiedCustomer.getDivisionID());

            String sql = "UPDATE customers SET Customer_Name = '" + c.getName() + "', Address = '" + c.getAddress() + "', Postal_Code = '" +
                    c.getPostalCode() +"', Phone = '" + c.getPhone() + "', Last_Update = '" + lastUpdate + "', Division_ID = " + selectDivision() + " WHERE Customer_ID =" + c.getId() + ";";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            added = true;
        } catch (NumberFormatException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            e.printStackTrace();
            alert.showAndWait();
        }

        if(added) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Customer was successfully added to the database.");
            alert.showAndWait();
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerGUI.fxml")));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1000,700);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);

        stage.show();
    }

    public int selectDivision() {

        String divisionName = divisionsList.getSelectionModel().getSelectedItem().getName();

        try {

            String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division ='" + divisionName + "';";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                originalDivision = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        System.out.println(divisionID);
        return originalDivision;
    }

    public String getDivisionName() {

        try {

            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID =" + divisionID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                divisionName = rs.getString("Division");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return divisionName;
    }
}
