package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DBAppointments {

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_ID");
                String type = rs.getString("Type");
                int customerID = rs.getInt("Customer_ID");
                
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                Appointment A = new Appointment();
                A.setAppointmentID(appointmentID);
                A.setTitle(title);
                A.setDesc(desc);
                A.setLocation(location);
                A.setContact(contact);
                A.setType(type);
                A.setCustomerID(customerID);

                A.setDate(A.getDate());

                A.setStart(start);
                A.setStartTime(A.getStartTime());

                A.setEnd(end);
                A.setEndTime(A.getEndTime());

                String customerName = A.getCustomerName();
                A.setCustomerName(customerName);

                appointmentsList.add(A);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;
    }
}
