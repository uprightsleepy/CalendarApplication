package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Reports controller.
 */
public class reportsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Contact schedules.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void contactSchedules(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/contactSchedules.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Contact Schedules");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Customer appointments.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void customerAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerAppointments.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void goToAppointmentsPerCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appointmentsPerCustomer.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Appointments Per Customer");
        stage.setScene(scene);
        stage.show();
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
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
            Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root, 1000, 700);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
    }
}
