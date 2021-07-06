package controller;

import utils.DBAppointments;
import utils.DBContacts;
import utils.DBCustomer;
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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type New appointment controller.
 */
public class newAppointmentController implements Initializable {

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
     * The Start time picker.
     */
    public ComboBox<LocalTime> startTimePicker;
    /**
     * The End time picker.
     */
    public ComboBox<LocalTime> endTimePicker;

    /**
     * The Type list.
     */
    public ComboBox<String> typeList;

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
     * The Customer.
     */
    public String customer;
    /**
     * The Customer id.
     */
    public int customerID;

    /**
     * The Contact name.
     */
    public String contactName;
    /**
     * The Contact id.
     */
    public int contactID;

    /**
     * The Added.
     */
    boolean added = false;

    /**
     * The Title tf.
     */
    public TextField titleTF;
    /**
     * The Location tf.
     */
    public TextField locationTF;
    /**
     * The Description ta.
     */
    public TextArea descriptionTA;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * The Date & Time.
     */
    LocalTime time = LocalTime.of(0,0);
    LocalDate date = LocalDate.now();
    LocalDateTime currentTime = LocalDateTime.of(date,time);

    ZoneId sourceZone = ZoneId.systemDefault();
    ZoneId destinationZone = ZoneId.of("America/New_York");

    ZonedDateTime localTime = currentTime.atZone(sourceZone); // current time 0:00 at local timezone
    ZonedDateTime testTime = localTime.withZoneSameInstant(destinationZone); // time 0:00 local at EST

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(localTime.format(format));
        System.out.println(testTime.format(format));

        time = LocalTime.of(0,0);

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

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
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
     * Populate.
     */
    public void populate() {

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();
        ObservableList<Contact> contacts = DBContacts.getAllContacts();
        addTimes();

        for(Customer c:customers) {
            customerName = c.getName();
            customerList.getItems().add(customerName);
        }

        for(Contact contact:contacts) {
            contactList.getItems().add(contact.getName());
        }

        startTimePicker.setItems(times);
        endTimePicker.setItems(times);

        typeList.getItems().addAll("Planning Session", "De-Briefing", "Info-Sharing", "Decision Making", "Workshop", "Team Building");
    }

    /**
     * Add new appointment.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void addNewAppointment(ActionEvent actionEvent) throws IOException {

        String title = titleTF.getText();
        String description = descriptionTA.getText();
        String location = locationTF.getText();
        contactName = contactList.getSelectionModel().getSelectedItem();
        customer = customerList.getSelectionModel().getSelectedItem();
        String type = typeList.getSelectionModel().getSelectedItem();

        LocalDate startDate = startingDate.getValue();
        LocalTime startTime = startTimePicker.getValue();
        LocalDateTime start = LocalDateTime.of(startDate,startTime);
        Timestamp startDB = Timestamp.valueOf(start);
        ZonedDateTime testStart = startDB.toInstant().atZone(ZoneId.of("UTC"));
        Timestamp startConverted = Timestamp.valueOf(testStart.format(format));

        ZonedDateTime startETTemp = startDB.toInstant().atZone(ZoneId.of("America/New_York"));
        Timestamp startET = Timestamp.valueOf(startETTemp.format(format));

        LocalDate endDate = endingDate.getValue();
        LocalTime endTime = endTimePicker.getValue();
        LocalDateTime end = LocalDateTime.of(endDate,endTime);

        Timestamp endDB = Timestamp.valueOf(end);
        ZonedDateTime testEnd = endDB.toInstant().atZone(ZoneId.of("UTC"));
        Timestamp endConverted = Timestamp.valueOf(testEnd.format(format));

        ZonedDateTime endETTemp = endDB.toInstant().atZone(ZoneId.of("America/New_York"));
        Timestamp endET = Timestamp.valueOf(endETTemp.format(format));


        Timestamp created = Timestamp.valueOf(LocalDateTime.now());

        try {

            Appointment a = new Appointment();
            a.setTitle(title);
            a.setDesc(description);
            a.setLocation(location);
            a.setContactID(getContactID());
            a.setCustomerName(customer);
            a.setType(type);
            a.setCustomerID(getCustomerID());

            for(Appointment b: appointments) {

                if(start.isAfter(end) || start.equals(end)) {

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
                } if(a.getCustomerID() == b.getCustomerID() && startTime.equals(b.getStartTime()) ||
                        a.getCustomerID() == b.getCustomerID() && startTime.isAfter(b.getStartTime()) && startTime.isBefore(b.getEndTime()) ||
                        a.getCustomerID() == b.getCustomerID() && startTime.isBefore(b.getEndTime()) && endTime.isAfter(b.getEndTime()) ||
                        a.getCustomerID() == b.getCustomerID() && startTime.equals(b.getEndTime())){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("The same customer can not have overlapping appointments.");
                    alert.showAndWait();
                    return;
                }
            }

            a.setStart(start);
            a.setEnd(end);
            String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Customer_ID, User_ID, Contact_ID) VALUES(NULL,'" + a.getTitle() + "', '"
                    + a.getDesc() + "', '" + a.getLocation() + "', '" + a.getType() + "', '" + startConverted + "', '" + endConverted + "', '" + created + "'," + a.getCustomerID() + ", 1, " +
                    a.getContactID() + ");";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

            added = true;
            a.setCreated(created.toLocalDateTime());
        } catch (NumberFormatException | SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            System.out.println(e.getMessage());
            alert.showAndWait();
        }

        if (added) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Appointment was successfully added to the database.");
            alert.showAndWait().filter(response -> response == ButtonType.OK);
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

            String sql = "SELECT Customer_ID FROM customers WHERE Customer_Name = '" + customer + "';";
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
     * Add times.
     */
    public void addTimes() {

        do {
            time = time.plusMinutes(30);
            currentTime = LocalDateTime.of(date,time);
            localTime = currentTime.atZone(sourceZone);
            testTime = localTime.withZoneSameInstant(destinationZone);
            System.out.println(testTime.format(format));
        } while(testTime.getHour()<8);

        do {
            times.add(time);
            time = time.plusMinutes(30);
            currentTime = LocalDateTime.of(date,time);
            localTime = currentTime.atZone(sourceZone);
            testTime = localTime.withZoneSameInstant(destinationZone);
        } while(testTime.getHour() < 22);
    }

    /**
     * Check weekend boolean.
     *
     * @param dateChoice the date choice
     * @return the boolean
     */
    public static boolean checkWeekend(LocalDate dateChoice) {
        DayOfWeek day = dateChoice.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}