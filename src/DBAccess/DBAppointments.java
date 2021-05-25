package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Countries;
import utils.DBConnection;

import java.sql.*;

public class DBAppointments {

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
//                Date date = rs.getDate("Date");
                Time start = rs.getTime("Start");
                Time end = rs.getTime("End");
                int customerID = rs.getInt("Customer_ID");

                Appointment A = new Appointment(appointmentID,title,desc,location,contact,type,start,end,customerID);

                appointmentsList.add(A);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;
    }
}
