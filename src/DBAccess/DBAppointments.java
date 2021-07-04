package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;
import java.sql.*;
import java.time.*;
import java.util.Locale;


/**
 * The type Db appointments.
 */
public class DBAppointments {

    private static final ZoneOffset local = ZoneId.systemDefault().getRules().getOffset(Instant.now());

    /**
     * Gets all appointments.
     *
     * @return the all appointments
     */
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
                int contact = rs.getInt("Contact_ID");
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

    /**
     * Lookup appointment observable list.
     *
     * @param appointmentType the appointment type
     * @return the observable list
     */
    public static ObservableList<Appointment> lookupAppointment(String appointmentType) {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        for(Appointment a : DBAppointments.getAllAppointments()){

            if(a.getType().contains(appointmentType) || a.getType().toLowerCase(Locale.ROOT).contains(appointmentType)) {

                appointments.add(a);
            }
        }

        return appointments;
    }

    /**
     * Lookup appointment observable list.
     *
     * @param appointmentID the appointment id
     * @return the observable list
     */
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