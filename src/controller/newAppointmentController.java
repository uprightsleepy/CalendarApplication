package controller;

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
import model.Customer;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class newAppointmentController implements Initializable {
    ObservableList<LocalTime> times = FXCollections.observableArrayList();

    public ComboBox<String> customerList;

    public ComboBox<LocalTime> startTimePicker;
    public ComboBox<LocalTime> endTimePicker;

    public DatePicker startingDate;
    public DatePicker endingDate;

    public String customerName;
    public int customerID;

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
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

            stage.show();
        }
    }

    public void populate() {

        ObservableList<Customer> customers = DBCustomer.getAllCustomers();

        for(Customer c:customers) {
            customerName = c.getName();
            customerList.getItems().add(customerName);
        }

        addTimes();
        startTimePicker.setItems(times);
        endTimePicker.setItems(times);
    }

    public void addNewAppointment(ActionEvent actionEvent) throws IOException {

        String title = titleTF.getText();
        String description = descriptionTA.getText();
        String location = locationTF.getText();
        String contact = contactTF.getText();
        String customer = customerList.getSelectionModel().getSelectedItem();
        String type = typeTF.getText();

        LocalDate startDate = startingDate.getValue();
        LocalTime startTime = startTimePicker.getValue();
        LocalDateTime start = LocalDateTime.of(startDate,startTime);

        Timestamp startDB = Timestamp.valueOf(start);

        LocalDate endDate = endingDate.getValue();
        LocalTime endTime = endTimePicker.getValue();
        LocalDateTime end = LocalDateTime.of(endDate,endTime);
        Timestamp endDB = Timestamp.valueOf(end);

        Timestamp created = Timestamp.valueOf(LocalDateTime.now());

        System.out.println(start);
        System.out.println(startDB);

        System.out.println();

        System.out.println(end);
        System.out.println(endDB);
        try {

            Appointment a = new Appointment();
            a.setTitle(title);
            a.setDesc(description);
            a.setLocation(location);
            a.setContact(contact);
            a.setCustomerName(customer);
            a.setType(type);
            a.setCustomerID(getCustomerID());
            a.setCreated(created.toLocalDateTime());

            //needs to be LocalDateTime to add to "Start" and "End" columns in DB
            String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Customer_ID) VALUES(NULL,'" + a.getTitle() + "', '"
                    + a.getDesc() + "', '" + a.getLocation() + "', '" + a.getType() + "', '" + startDB +"', '" + endDB + "', '" + created + "'," + a.getCustomerID() + ");";

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
            alert.setContentText("Appointment was successfully added to the database.");
            alert.showAndWait();
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

    public void addTimes() {
        times.addAll((LocalTime.of(9,0)),
                LocalTime.of(9,30),
                LocalTime.of(10,0),
                LocalTime.of(10,30),
                LocalTime.of(11,0),
                LocalTime.of(11,30),
                LocalTime.of(12,0),
                LocalTime.of(12,30),
                LocalTime.of(13,0),
                LocalTime.of(13,30),
                LocalTime.of(14,0),
                LocalTime.of(14,30),
                LocalTime.of(15,0),
                LocalTime.of(15,30),
                LocalTime.of(16,0),
                LocalTime.of(16,30),
                LocalTime.of(17,0)
        );
    }
}
