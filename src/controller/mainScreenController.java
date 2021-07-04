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
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * The type Main screen controller.
 */
public class mainScreenController implements Initializable {

    /**
     * The Appointments list.
     */
    public TableView<Appointment> appointmentsList;
    /**
     * The Id col.
     */
    public TableColumn<Appointment,Integer> idCol;
    /**
     * The Name col.
     */
    public TableColumn<Appointment,String> nameCol;
    /**
     * The Title col.
     */
    public TableColumn<Appointment,String> titleCol;
    /**
     * The Desc col.
     */
    public TableColumn<Appointment,String> descCol;
    /**
     * The Type col.
     */
    public TableColumn<Appointment,String> typeCol;
    /**
     * The Date col.
     */
    public TableColumn<Appointment,String> dateCol;
    /**
     * The Start col.
     */
    public TableColumn<Appointment,String> startCol;
    /**
     * The End col.
     */
    public TableColumn<Appointment,String> endCol;
    /**
     * The Cust id col.
     */
    public TableColumn<Appointment,String> custIdCol;

    /**
     * The Sort week selection boolean.
     */
    boolean sortWeekSelecton = false;

    /**
     * The Group.
     */
    final ToggleGroup group = new ToggleGroup();
    /**
     * The Week radio.
     */
    public RadioButton weekRadio;
    /**
     * The Month radio.
     */
    public RadioButton monthRadio;
    /**
     * The All radio.
     */
    public RadioButton allRadio;
    /**
     * The Sort list.
     */
    public ComboBox<String> sortList;
    /**
     * The Sort button.
     */
    public Button sortButton;

    /**
     * The Test.
     */
    LocalDate test = null;

    /**
     * The Current time.
     */
    LocalDateTime currentTime = LocalDateTime.now();

    /**
     * The Search field.
     */
    public TextField searchField;
    /**
     * The Appointment message.
     */
    public Label appointmentMessage;

    private static Appointment appointmentToModify;
    private static int appointmentToModifyIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();
        checkTimes();
    }

    /**
     * Logout.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     * logs the user out the intended way and logs it in the "login_activity.txt" file
     */
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


    /**
     * View customer gui.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void viewCustomerGUI(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerGUI.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Customer Menu");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * New appointment gui.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void newAppointmentGUI(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/newAppointment.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Load report options.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void loadReportOptions(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/reports.fxml")));
        Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("Reports");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Update appointment gui.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    /**
     * Populate.
     *
     * @throws NullPointerException the null pointer exception
     */
    public void populate() throws NullPointerException {

        ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
        System.out.println(currentTime);

        weekRadio.setToggleGroup(group);
        monthRadio.setToggleGroup(group);
        allRadio.setToggleGroup(group);
        allRadio.setSelected(true);

        appointmentsList.setItems(appointments);
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**
     * Delete appointment.
     */
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


                    Alert complete = new Alert(Alert.AlertType.INFORMATION);
                    complete.setTitle("Appointment Deleted");
                    complete.setContentText("Appointment ID "+ String.valueOf(appointmentToDelete.getAppointmentID()) + ", " + appointmentToDelete.getType() + " | successfully deleted.");
                    complete.showAndWait();
                }
            } catch (NullPointerException | SQLException e) {

                e.printStackTrace();
            }

            populate();
        }
    }

    /**
     * Record signout.
     *
     * @throws IOException the io exception
     */
    public void recordSignout() throws IOException{

        Date date = new Date();

        String str = "Logout recorded at " + date +" | Session Terminated via Logout Button";
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/login_activity.txt", true));
        writer.append('\n');
        writer.append(str);
        writer.close();
    }

    /**
     * Gets appointment to modify.
     *
     * @return the appointment to modify
     */
    public static Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    /**
     * Gets appointment to modify index.
     *
     * @return the appointment to modify index
     */
    public static int getAppointmentToModifyIndex() {
        return appointmentToModifyIndex;
    }

    /**
     * Search.
     */
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

    /**
     * Check times.
     */
    public void checkTimes() {
        for(Appointment a : appointmentsList.getItems()) {

            LocalDateTime appointmentTime = LocalDateTime.of(a.getDate().toLocalDate(),a.getStartTime());
            Duration diff = Duration.between(currentTime, appointmentTime);
            long minutes = diff.toMinutes();

            if(minutes <= 15 && minutes >= 0) {

                appointmentMessage.setText("Appointment ID " + a.getAppointmentID() + " | " + a.getDate() + " " + a.getStartTime());
                return;
            } else {

                appointmentMessage.setText("No Upcoming Appointments");
            }
        }
    }

    /**
     * Sort by month.
     */
    public void sortByMonth() {

        sortList.setDisable(false);
        sortButton.setDisable(false);
        sortList.getItems().clear();
        sortList.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
    }

    /**
     * View all.
     */
    public void viewAll() {

        sortList.getItems().clear();
        sortList.setDisable(true);
        sortButton.setDisable(true);
        weekRadio.setSelected(false);
        monthRadio.setSelected(false);
        populate();
    }

    /**
     * Sort by week.
     */
    public void sortByWeek() {

        sortList.getItems().clear();
        for(int i = 0; i <53; i++) {
            sortList.getItems().add(String.valueOf(i));
        }
        sortList.setDisable(false);
        sortButton.setDisable(false);
    }

    /**
     * Sort. Applied lambda expressions to replace for loops and improve performance.
     */
    public void sort() {

        ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
        appointmentsList.getItems().clear();

        String selection = sortList.getSelectionModel().getSelectedItem();

        // Implemented lambdas to replaces repetitive looping in these if-statements
        if(weekRadio.isSelected()) {

            appointments.forEach(item -> { test = item.getDate().toLocalDate();

                TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
                int weekNumber = test.get(weekOfYear);
                if(weekNumber == Integer.parseInt(selection)) {
                    appointmentsList.getItems().add(item);
                } });
        } if(monthRadio.isSelected()) {

            appointments.forEach(item -> {
                test = item.getDate().toLocalDate();
                Month month = test.getMonth();
                System.out.println(month);
                if(selection.equals(month.toString())) {
                    appointmentsList.getItems().add(item);
                }
            });
        }
    }
}