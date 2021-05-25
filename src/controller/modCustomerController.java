package controller;

import DBAccess.DBCountries;
import DBAccess.DBFirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivision;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class modCustomerController implements Initializable {
    public int index = 0;
    public ComboBox<FirstLevelDivision> divisionsList;
    public ComboBox<Countries> countryList;

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

    public void selectCountry(ActionEvent actionEvent) {
        ObservableList<FirstLevelDivision> divisions;
        index = countryList.getSelectionModel().getSelectedIndex();
        System.out.println(index);

        if(index == 0){

            divisions = DBFirstLevelDivisions.getAllUSDivisions();
            divisionsList.setItems(divisions);
            divisionsList.getSelectionModel().selectFirst();
        } else if(index == 1) {

            divisions = DBFirstLevelDivisions.getAllUKDivisions();
            divisionsList.setItems(divisions);
            divisionsList.getSelectionModel().selectFirst();

        } else {

            divisions = DBFirstLevelDivisions.getAllCanadaDivisions();
            divisionsList.setItems(divisions);
            divisionsList.getSelectionModel().selectFirst();
        }
    }

    public void populate() {
        ObservableList<Countries> countries = DBCountries.getAllCountries();
        countryList.setItems(countries);
    }
}
