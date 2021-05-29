package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
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
                Time start = rs.getTime("Start");
                Time end = rs.getTime("End");
                int customerID = rs.getInt("Customer_ID");

                Appointment A = new Appointment(appointmentID," ",title,desc,location,contact,type,start,end,null,customerID);

                String customerName = A.getCustomerName();
                A.setCustomerName(customerName);

                Date date = A.getDate();
                A.setDate(date);

                appointmentsList.add(A);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;
    }
}
