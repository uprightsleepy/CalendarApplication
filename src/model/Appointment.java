package model;

import utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {

    private Date date;

    private int appointmentID;
    private String title;
    private String desc;
    private String location;
    private String contact;
    private String type;

    private LocalDateTime start;
    private LocalTime startTime;

    private LocalDateTime end;
    private LocalTime endTime;
    private LocalDateTime created;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    private int customerID;
    private String customerName;

    public Appointment(int appointmentID, String title, String desc, String location, String contact, String type, LocalDateTime start, LocalDateTime end, int customerID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    public Appointment(int appointmentID, String title, String desc, String location, String contact, String type, LocalTime startTime, LocalTime endTime, int customerID) {

        this.appointmentID = appointmentID;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

            String sql = "SELECT CONVERT(Start, time) FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                startTime = rs.getTime("CONVERT(Start, time)").toLocalTime();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return startTime;
    }

    public LocalTime getEndTime() {

        try {

            String sql = "SELECT CONVERT(End, time) FROM appointments WHERE Appointment_ID = " + appointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                endTime = rs.getTime("CONVERT(End, time)").toLocalTime();
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
}