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

public class updateAppointmentController implements Initializable {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Appointment modifiedAppointment = mainScreenController.getAppointmentToModify();
    LocalTime time = LocalTime.of(8,0);

    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    public ComboBox<String> customerList;
    public ComboBox<String> contactList;
    public ComboBox<String> typeList;

    public ComboBox<LocalTime> startTimePicker;
    public ComboBox<LocalTime> endTimePicker;

    public DatePicker startingDate;
    public DatePicker endingDate;

    public String customerName;
    public int customerID = modifiedAppointment.getCustomerID();

    public String contactName;
    private int contactID = modifiedAppointment.getContactID();

    boolean added = false;

    public TextField contactTF;
    public TextField titleTF;
    public TextField locationTF;
    public TextField typeTF;
    public TextArea descriptionTA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();

    }


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

    public void addTimes() {
        do {

            times.add(time);
            time = time.plusMinutes(30);
        } while(!time.equals(LocalTime.of(19,30)));
    }

    public void sendBack(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
        Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

        Scene scene = new Scene(root,1000,700);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

        stage.show();
    }
}