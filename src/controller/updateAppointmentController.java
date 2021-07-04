package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Update appointment controller.
 */
public class updateAppointmentController implements Initializable {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Appointment modifiedAppointment = mainScreenController.getAppointmentToModify();
    /**
     * The Time.
     */
    LocalTime time = LocalTime.of(8,0);

    /**
     * The Times.
     */
    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    /**
     * The Appointments.
     */
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    /**
     * The Customer list.
     */
    public ComboBox<String> customerList;
    /**
     * The Contact list.
     */
    public ComboBox<String> contactList;
    /**
     * The Type list.
     */
    public ComboBox<String> typeList;

    /**
     * The Start time picker.
     */
    public ComboBox<LocalTime> startTimePicker;
    /**
     * The End time picker.
     */
    public ComboBox<LocalTime> endTimePicker;

    /**
     * The Starting date.
     */
    public DatePicker startingDate;
    /**
     * The Ending date.
     */
    public DatePicker endingDate;

    /**
     * The Customer name.
     */
    public String customerName;
    /**
     * The Customer id.
     */
    public int customerID = modifiedAppointment.getCustomerID();

    /**
     * The Contact name.
     */
    public String contactName;
    private int contactID = modifiedAppointment.getContactID();

    /**
     * The Added.
     */
    boolean added = false;

    /**
     * The Contact tf.
     */
    public TextField contactTF;
    /**
     * The Title tf.
     */
    public TextField titleTF;
    /**
     * The Location tf.
     */
    public TextField locationTF;
    /**
     * The Type tf.
     */
    public TextField typeTF;
    /**
     * The Description ta.
     */
    public TextArea descriptionTA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();

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

    /**
     * Populate.
     */
    public void populate() {

        titleTF.setText(modifiedAppointment.getTitle());
        descriptionTA.setText(modifiedAppointment.getDesc());
        locationTF.setText(modifiedAppointment.getLocation());
        typeList.getItems().addAll("Planning Session", "De-Briefing", "Info-Sharing", "Decision Making", "Workshop", "Team Building");
        typeList.getSelectionModel().select(modifiedAppointment.getType());

        startingDate.setValue(modifiedAppointment.getStart().toLocalDate());
        endingDate.setValue(modifiedAppointment.getEnd().toLocalDate());

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();
        ObservableList<Contact> contacts = DBContacts.getAllContacts();

        for(Customer c:customers) {
            customerName = c.getName();
            customerList.getItems().add(customerName);
        }
        customerList.getSelectionModel().select(modifiedAppointment.getCustomerName());


        for(Contact contact : contacts) {
            contactName = contact.getName();
            contactList.getItems().add(contactName);
        }
        contactList.getSelectionModel().select(getContactName());

        addTimes();
        startTimePicker.setItems(times);
        startTimePicker.getSelectionModel().select(modifiedAppointment.getStartTime());
        endTimePicker.setItems(times);
        endTimePicker.getSelectionModel().select(modifiedAppointment.getEndTime());
    }

    /**
     * Add appointment.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void addAppointment(ActionEvent actionEvent) throws IOException {

        String title = titleTF.getText();
        String description = descriptionTA.getText();
        String location = locationTF.getText();

        customerName = customerList.getSelectionModel().getSelectedItem();

        contactName = contactList.getSelectionModel().getSelectedItem();

        String type = typeList.getSelectionModel().getSelectedItem();

        LocalDate startDate = startingDate.getValue();
        LocalTime startTime = startTimePicker.getValue();
        LocalDateTime start = LocalDateTime.of(startDate,startTime);

        Timestamp startDB = Timestamp.valueOf(start);
        ZonedDateTime testStart = startDB.toInstant().atZone(ZoneId.of("UTC"));
        Timestamp startConverted = Timestamp.valueOf(testStart.format(format));

        LocalDate endDate = endingDate.getValue();
        LocalTime endTime = endTimePicker.getValue();
        LocalDateTime end = LocalDateTime.of(endDate,endTime);

        Timestamp endDB = Timestamp.valueOf(end);
        ZonedDateTime testEnd = endDB.toInstant().atZone(ZoneId.of("UTC"));
        Timestamp endConverted = Timestamp.valueOf(testEnd.format(format));

        Timestamp created = Timestamp.valueOf(LocalDateTime.now());

        try {

            Appointment a = new Appointment();
            a.setTitle(title);
            a.setDesc(description);
            a.setLocation(location);
            a.setContactID(getContactID());
            a.setCustomerName(customerName);
            a.setType(type);
            a.setCustomerID(getCustomerID());

            for(Appointment b: appointments) {

                if (testStart.toLocalDateTime().equals(b.getStart())) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Cannot add an appointment at the same time as another appointment.");
                    alert.showAndWait();
                    return;
                } if(start.isAfter(end) || start.equals(end)) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Start time must be set before the ending time of the appointment.");
                    alert.showAndWait();
                    return;
                } if(checkWeekend(testStart.toLocalDate()) || checkWeekend(testEnd.toLocalDate())) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Unable to schedule appointments on the weekend.");
                    alert.showAndWait();
                    return;
                }
            }

            String sql = "UPDATE appointments SET Title = '" + a.getTitle() + "', Description = '" + a.getDesc() + "', Location = '" +
                    a.getLocation() +"', Type = '" + a.getType() + "', Start = '" + startConverted  + "', End = '" + endConverted + "', Create_Date = NULL, Created_By = 'application', Last_Update = '"
                    + created + "', Customer_ID =" + a.getCustomerID() + ", Contact_ID = " + a.getContactID()+ " WHERE Appointment_ID = " + modifiedAppointment.getAppointmentID() +";";


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
            alert.setContentText("Appointment was successfully updated.");
            alert.showAndWait();
            sendBack(actionEvent);
        }
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerID() {

        try {
            String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = '" + customerName + "';";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerID = rs.getInt("Customer_ID");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return customerID;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactID() {

        try {

            String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = '" + contactName + "';";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                contactID = rs.getInt("Contact_ID");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return contactID;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {

        try {

            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = '" + contactID + "';";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                contactName = rs.getString("Contact_Name");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return contactName;
    }

    /**
     * Add times.
     */
    public void addTimes() {
        do {

            times.add(time);
            time = time.plusMinutes(30);
        } while(!time.equals(LocalTime.of(19,30)));
    }

    /**
     * Send back.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void sendBack(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1000,700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        stage.show();
    }

    /**
     * Check weekend boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean checkWeekend(LocalDate date) {
        DayOfWeek d = date.getDayOfWeek();
        return d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY;
    }
}