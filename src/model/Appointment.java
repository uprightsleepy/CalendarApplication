package model;

import utils.DBConnection;

import java.sql.*;

public class Appointment {

    private Date date;
    private int appointmentID;
    private String title;
    private String desc;
    private String location;
    private int contact;
    private String type;
    private Time start;
    private Time end;
    private int customerID;
    private String customerName;

    public Appointment(int appointmentID, String title, String desc, String location, int contact, String type, Time start, Time end, int customerID) {

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

    public Appointment(int appointmentID, String customerName, String title, String desc, String location, int contact, String type, Time start, Time end, int customerID) {

        this.appointmentID = appointmentID;
        this.customerName = customerName;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
    }

    public Appointment(int appointmentID, String customerName, String title, String desc, String location, int contact, String type, Time start, Time end, Date date, int customerID) {

        this.appointmentID = appointmentID;
        this.customerName = customerName;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.date = date;
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

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Time getStart() {

        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
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

    public void setDate(Date date) {
        this.date = date;
    }
}
