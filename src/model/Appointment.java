package model;

import utils.DBConnection;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

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

    ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Appointment(int appointmentID, String title, String desc, String location, int contactID, String type, int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.customerID = customerID;
    }

    public Appointment(int appointmentID, String title, String desc, String type, int customerID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.customerID = customerID;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


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

    public Appointment(int appointmentID, String title, String desc, String location, String type, int customerID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.customerID = customerID;
    }


    public Appointment() {

    }

    public int getAppointmentID() {

        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {

        this.appointmentID = appointmentID;
    }

    public String getTitle() {

        return title;
    }

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

    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public int getContactID() {

        return contactID;
    }

    public void setContactID(int contactID) {

        this.contactID = contactID;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public int getCustomerID() {

        return customerID;
    }

    public void setCustomerID(int customerID) {

        this.customerID = customerID;
    }

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

    public void setDate(Date date) {

        this.date = date;
    }

    public LocalDateTime getStart() {

        return start;
    }

    public void setStart(LocalDateTime start) {

        this.start = start;
    }

    public LocalDateTime getEnd() {

        return end;
    }

    public void setEnd(LocalDateTime end) {

        this.end = end;
    }

    public void setStartTime(LocalTime startTime) {

        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {

        this.endTime = endTime;
    }

    public Month getMonth(LocalDateTime start){

        return start.getMonth();
    }
}