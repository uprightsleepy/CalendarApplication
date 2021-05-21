package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.Connection;


import java.io.IOException;
import java.net.URL;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {

    public Label testDB;
    public ChoiceBox<String> calendarType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you'd like to logout?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
            Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root, 520, 400);
            stage.setTitle("Login Page");
            stage.setScene(scene);

            stage.show();
        }
    }


    public void viewCustomerGUI(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerGUI.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);

        stage.show();
    }
}
