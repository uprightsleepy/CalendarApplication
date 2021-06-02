package controller;

import DBAccess.DBAppointments;
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
import utils.DBConnection;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainScreenController implements Initializable {

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

    private static Appointment appointmentToModify;
    private static int appointmentToModifyIndex;

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

        recordSignout();
    }


    public void viewCustomerGUI(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerGUI.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);

        stage.show();
    }

    public void newAppointmentGUI(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/newAppointment.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);

        stage.show();
    }

    public void updateAppointmentGUI(ActionEvent actionEvent) throws IOException {
        appointmentToModify = appointmentsList.getSelectionModel().getSelectedItem();

        if(appointmentToModify == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no appointments selected.");
            alert.showAndWait();
        } else{

            appointmentToModify = appointmentsList.getSelectionModel().getSelectedItem();
            appointmentToModifyIndex = DBAppointments.getAllAppointments().indexOf(appointmentToModify);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/updateAppointment.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Update Appointment");

            stage.setScene(scene);
            stage.show();
        }
    }

    public void populate() throws NullPointerException {

        ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

        appointmentsList.setItems(appointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    }

    public void deleteAppointment() {

        Appointment appointmentToDelete = appointmentsList.getSelectionModel().getSelectedItem();
        if(appointmentToDelete == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no customers selected.");
            alert.showAndWait();
        } else {
            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to permanently delete this customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentToDelete.getAppointmentID();

                    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                    ps.executeUpdate();
                }
            } catch (NullPointerException | SQLException e) {

                e.printStackTrace();
            }

            populate();
        }
    }

    public void recordSignout() throws IOException{
        Date date = new Date();

        String str = "Logout recorded at " + date +" | Session Terminated via Logout Button";
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/login_activity.txt", true));
        writer.append('\n');
        writer.append(str);
        writer.close();
    }

    public static Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    public static int getAppointmentToModifyIndex() {
        return appointmentToModifyIndex;
    }
}
