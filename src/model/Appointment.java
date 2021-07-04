package model;

import utils.DBConnection;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * The type Appointment.
 */
public class Appointment {

    private Date date;

    private boolean isBusy = false;


    private int customerID;
    private String customerName;
    private int appointmentID;
    private String title;
    private String desc;
    private String location;

    private int contactID;
    private String contactName;

    private String type;
    private LocalDateTime start;
    private LocalTime startTime;

    private String startDateTime;
    private String endDateTime;

    private LocalDateTime end;
    private LocalTime endTime;
    private LocalDateTime created;
    /**
     * The Local zone.
     */
    ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param desc          the desc
     * @param location      the location
     * @param contactID     the contact id
     * @param type          the type
     * @param customerID    the customer id
     */
    public Appointment(int appointmentID, String title, String desc, String location, int contactID, String type, int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.customerID = customerID;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param desc          the desc
     * @param type          the type
     * @param customerID    the customer id
     */
    public Appointment(int appointmentID, String title, String desc, String type, int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.customerID = customerID;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param desc          the desc
     * @param location      the location
     * @param contactID     the contact id
     * @param type          the type
     * @param start         the start
     * @param end           the end
     * @param customerID    the customer id
     */
    public Appointment(int appointmentID, String title, String desc, String location, int contactID, String type, LocalDateTime start, LocalDateTime end, int customerID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param desc          the desc
     * @param location      the location
     * @param contactID     the contact id
     * @param type          the type
     * @param startTime     the start time
     * @param endTime       the end time
     * @param customerID    the customer id
     */
    public Appointment(int appointmentID, String title, String desc, String location, int contactID, String type, LocalTime startTime, LocalTime endTime, int customerID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param desc          the desc
     * @param location      the location
     * @param type          the type
     * @param customerID    the customer id
     */
    public Appointment(int appointmentID, String title, String desc, String location, String type, int customerID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.customerID = customerID;
    }


    /**
     * Instantiates a new Appointment.
     */
    public Appointment() {

    }

    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentID() {

        return appointmentID;
    }

    /**
     * Sets appointment id.
     *
     * @param appointmentID the appointment id
     */
    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {

        return title;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {

        try {

            String sql = "SELECT Customer_Name FROM customers WHERE Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                customerName = rs.getString("Customer_Name");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return customerName;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {

        try {

            String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = " + contactID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                contactName = rs.getString("Contact_Name");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return contactName;
    }

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * Gets desc.
     *
     * @return the desc
     */
    public String getDesc() {

        return desc;
    }

    /**
     * Sets desc.
     *
     * @param desc the desc
     */
    public void setDesc(String desc) {

        this.desc = desc;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {

        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {

        this.location = location;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactID() {

        return contactID;
    }

    /**
     * Sets contact id.
     *
     * @param contactID the contact id
     */
    public void setContactID(int contactID) {

        this.contactID = contactID;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {

        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {

        this.type = type;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerID() {

        return customerID;
    }

    /**
     * Sets customer id.
     *
     * @param customerID the customer id
     */
    public void setCustomerID(int customerID) {

        this.customerID = customerID;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {

        try {

            String sql = "SELECT CONVERT(Start, date) FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                date = rs.getDate("CONVERT(Start, date)");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return date;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {

        try {

            String sql = "SELECT Start FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Timestamp start = rs.getTimestamp("Start"); //UTC
                ZonedDateTime temp = ZonedDateTime.of(start.toLocalDateTime(),ZoneId.of("UTC"));
                ZonedDateTime startTemp = temp.toInstant().atZone(ZoneId.of(localZone.toString()));
                Timestamp startConverted = Timestamp.valueOf(startTemp.format(format));

                startTime = startConverted.toLocalDateTime().toLocalTime();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return startTime;
    }

    /**
     * Gets start date time.
     *
     * @return the start date time
     */
    public String getStartDateTime() {

        try {

            String sql = "SELECT Start FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Timestamp start = rs.getTimestamp("Start"); //UTC
                ZonedDateTime temp = ZonedDateTime.of(start.toLocalDateTime(),ZoneId.of("UTC"));
                ZonedDateTime startTemp = temp.toInstant().atZone(ZoneId.of(localZone.toString()));
                Timestamp startConverted = Timestamp.valueOf(startTemp.format(format));

                startDateTime = startConverted.toLocalDateTime().format(format);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return startDateTime;
    }

    /**
     * Gets end date time.
     *
     * @return the end date time
     */
    public String getEndDateTime() {

        try {

            String sql = "SELECT End FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Timestamp start = rs.getTimestamp("End"); //UTC
                ZonedDateTime temp = ZonedDateTime.of(start.toLocalDateTime(),ZoneId.of("UTC"));
                ZonedDateTime endTemp = temp.toInstant().atZone(ZoneId.of(localZone.toString()));
                Timestamp endConverted = Timestamp.valueOf(endTemp.format(format));

                endDateTime = endConverted.toLocalDateTime().format(format);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return endDateTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {

        try {

            String sql = "SELECT End FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Timestamp end = rs.getTimestamp("End"); //UTC
                ZonedDateTime temp = ZonedDateTime.of(end.toLocalDateTime(),ZoneId.of("UTC"));
                ZonedDateTime endTemp = temp.toInstant().atZone(ZoneId.of(localZone.toString()));
                Timestamp endConverted = Timestamp.valueOf(endTemp.format(format));

                endTime = endConverted.toLocalDateTime().toLocalTime();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return endTime;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {

        this.date = date;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {

        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {

        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {

        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {

        this.end = end;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(LocalTime startTime) {

        this.startTime = startTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalTime endTime) {

        this.endTime = endTime;
    }

    /**
     * Get month month.
     *
     * @param start the start
     * @return the month
     */
    public Month getMonth(LocalDateTime start){

        return start.getMonth();
    }
}