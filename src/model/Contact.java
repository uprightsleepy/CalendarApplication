package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * The type Contact.
 */
public class Contact {

    private static final ZoneOffset local = ZoneId.systemDefault().getRules().getOffset(Instant.now());
    /**
     * The Format.
     */
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int id;
    private String name;
    private String email;

    /**
     * Instantiates a new Contact.
     *
     * @param id    the id
     * @param name  the name
     * @param email the email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
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

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets appointments.
     *
     * @return the appointments
     */
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
