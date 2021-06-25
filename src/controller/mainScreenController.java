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
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class mainScreenController implements Initializable {

    public TableView<Appointment> appointmentsList;
    public TableColumn<Appointment,Integer> idCol;
    public TableColumn<Appointment,String> nameCol;
    public TableColumn<Appointment,String> titleCol;
    public TableColumn<Appointment,String> descCol;
    public TableColumn<Appointment,String> typeCol;
    public TableColumn<Appointment,String> dateCol;
    public TableColumn<Appointment,String> startCol;
    public TableColumn<Appointment,String> endCol;
    public TableColumn<Appointment,String> custIdCol;

    LocalDateTime currentTime = LocalDateTime.now();

    public TextField searchField;
    public Label appointmentMessage;

    private static Appointment appointmentToModify;
    private static int appointmentToModifyIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();
        checkTimes();
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

    public void loadReportOptions(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/reports.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Reports");
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
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    public void deleteAppointment() {

        Appointment appointmentToDelete = appointmentsList.getSelectionModel().getSelectedItem();
        if(appointmentToDelete == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("There are no appointments selected.");
            alert.showAndWait();
        } else {

            try {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to permanently delete this appointment?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentToDelete.getAppointmentID();

                    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                    ps.executeUpdate();
                }
            } catch (NullPointerException | SQLException e) {

                e.printStackTrace();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Deleted");
            alert.setContentText("Appointment ID "+ String.valueOf(appointmentToDelete.getAppointmentID()) + ", " + appointmentToDelete.getType() + " | successfully deleted.");
            populate();
            alert.showAndWait();
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

    public void search() {

        try {

            int q = Integer.parseInt(searchField.getText());
            ObservableList<Appointment> appointments = DBAppointments.lookupAppointment(q);
            appointmentsList.setItems(appointments);

            if(searchField.getText().isEmpty()) {

                appointmentsList.setItems(DBAppointments.getAllAppointments());
            }
        } catch (NumberFormatException e) {

            String q = searchField.getText();
            ObservableList<Appointment> appointments = DBAppointments.lookupAppointment(q);
            appointmentsList.setItems(appointments);

            if(searchField.getText().isEmpty()) {

                appointmentsList.setItems(DBAppointments.getAllAppointments());
            }
        }
    }

    public void checkTimes() {
        for(Appointment a : appointmentsList.getItems()) {

            if((a.getStartTime().until(currentTime, ChronoUnit.MINUTES)) <= 15) {

                appointmentMessage.setText("Appointment ID " + a.getAppointmentID() + " | " + a.getDate() + " " + a.getStartTime());
            } else {

                appointmentMessage.setText("No Upcoming Appointments");
            }
        }
    }
}