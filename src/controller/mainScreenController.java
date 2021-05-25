package controller;

import DBAccess.DBAppointments;
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
import model.Appointment;
import model.Countries;



import java.io.IOException;
import java.net.URL;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {

    public Label testDB;
    public ChoiceBox<String> calendarType;

    public TableView<Appointment> appointmentsList;
    public TableColumn<Appointment,Integer> idCol;
    public TableColumn<Appointment,String> nameCol;
    public TableColumn<Appointment,String> titleCol;
    public TableColumn<Appointment,String> descCol;
    public TableColumn<Appointment,String> typeCol;
    public TableColumn<Appointment,String> dateCol;
    public TableColumn<Appointment,String> startCol;
    public TableColumn<Appointment,String> endCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();

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

    public void populate() throws NullPointerException{

        ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
        for(Appointment a : appointments) {
            System.out.println(appointments);

            appointmentsList.setItems(appointments);
            idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
//            nameCol.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        }
    }
}
