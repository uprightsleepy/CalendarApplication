package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Contact {

    private static final ZoneOffset local = ZoneId.systemDefault().getRules().getOffset(Instant.now());
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        try {

            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                name = rs.getString("Contact_Name");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObservableList<Appointment> getAppointments() {

        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String type = rs.getString("Type");
                int customerID = rs.getInt("Customer_ID");

                OffsetDateTime startConv = rs.getTimestamp("Start").toLocalDateTime().atOffset(local);
                OffsetDateTime endConv = rs.getTimestamp("End").toLocalDateTime().atOffset(local);

                Timestamp start = Timestamp.valueOf(startConv.format(format));
                Timestamp end = Timestamp.valueOf(endConv.format(format));

                Appointment A = new Appointment(appointmentID,title,desc,type,customerID);

                A.setStart(start.toLocalDateTime());
                A.setEnd(end.toLocalDateTime());

                appointmentsList.add(A);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return appointmentsList;
    }
}
