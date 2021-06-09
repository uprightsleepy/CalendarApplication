package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import utils.DBConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DBAppointments {

    private static ZoneOffset local = ZoneId.systemDefault().getRules().getOffset(Instant.now());
    private static DateTimeFormatter formatter;

    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

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

                OffsetDateTime start = rs.getTimestamp("Start").toLocalDateTime().atOffset(local);

                OffsetDateTime end = rs.getTimestamp("End").toLocalDateTime().atOffset(local);

                Appointment A = new Appointment(appointmentID,title,desc,location,contact,type,customerID);
                Date date = A.getDate();
                A.setDate(date);

                A.setStart(start.toLocalDateTime());
                A.setStartTime(A.getStartTime());

                A.setEnd(end.toLocalDateTime());

                String customerName = A.getCustomerName();

                A.setCustomerName(customerName);

                appointmentsList.add(A);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;
    }

    public static ObservableList<Appointment> lookupAppointment(String appointmentTitle) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        for(Appointment a : DBAppointments.getAllAppointments()){
            if(a.getTitle().contains(appointmentTitle) || a.getTitle().toLowerCase(Locale.ROOT).contains(appointmentTitle)) {
                appointments.add(a);
            }
        }

        return appointments;
    }

    public static ObservableList<Appointment> lookupAppointment(int appointmentID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        for(Appointment a : DBAppointments.getAllAppointments()) {
            if(a.getAppointmentID() == appointmentID) {
                appointments.add(a);
            }
        }

        return appointments;
    }
}