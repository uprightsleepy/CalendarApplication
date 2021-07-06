package model;

import utils.DBConnection;

import java.sql.*;

/**
 * The type Customer.
 */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private int countryID;
    private int divisionID;
    private String division;

    private int appointmentID = 0;
    private int[] appointmentList;

    int numberOfAppointments;
    /**
     * The .
     */
    int i = 0;

    private boolean isBusy = false;


    /**
     * Gets division name.
     *
     * @return the division name
     */
    public String getDivisionName() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     */
    public Customer(int id, String name, String address, String postalCode, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     * @param country    the country
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     * @param divisionID the division id
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     * @param divisionID the division id
     * @param country    the country
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.country = country;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     * @param divisionID the division id
     * @param countryID  the country id
     * @param country    the country
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID, int countryID, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     * @param divisionID the division id
     * @param countryID  the country id
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID, int countryID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }

    /**
     * Instantiates a new Customer.
     *
     * @param id         the id
     * @param name       the name
     * @param address    the address
     * @param postalCode the postal code
     * @param phone      the phone
     * @param division   the division
     * @param country    the country
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
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
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {

        try {

            String sql = "SELECT Country FROM countries WHERE Country_ID = " + countryID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {

                country = rs.getString("Country");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return country;
    }

    /**
     * Gets country id.
     *
     * @param divisionID the division id
     * @return the country id
     */
    public int getCountryID(int divisionID) {

        try {

            String sql = "SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = " + divisionID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {

                countryID = rs.getInt("COUNTRY_ID");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return countryID;
    }

    /**
     * Sets country id.
     *
     * @param countryID the country id
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionID() {

        try {

            String sql = "SELECT Division_ID FROM customers WHERE Customer_ID = " + id + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {

                divisionID = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return divisionID;
    }

    /**
     * Gets division name.
     *
     * @param divisionID the division id
     * @return the division name
     */
    public String getDivisionName(int divisionID) {

        try {

            String sql = "SELECT Division FROM first_level_divisions WHERE Division_ID = " + divisionID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {

                division = rs.getString("Division");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return division;
    }

    /**
     * Sets division id.
     *
     * @param divisionID the division id
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Check appointments boolean.
     *
     * @param customerID the customer id
     * @return the boolean
     */
    public boolean checkAppointments(int customerID) {

        try {

            String sql = "SELECT Appointment_ID FROM appointments WHERE Customer_ID = " + customerID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                appointmentID = rs.getInt("Appointment_ID");

                isBusy = !rs.isBeforeFirst();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return isBusy;
    }

    public int getNumberOfAppointments() {

        numberOfAppointments = 0;

        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                 numberOfAppointments++;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return numberOfAppointments;
    }
}