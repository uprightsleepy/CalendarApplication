package model;

import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private boolean isBusy = false;


    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Customer(int id, String name, String address, String postalCode, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Customer(int id, String name, String address, String postalCode, String phone, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
    }

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.country = country;
    }

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

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID, int countryID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
        this.countryID = countryID;
    }

    public Customer(int id, String name, String address, String postalCode, String phone, String division, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision(int divisionID) {

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

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }
}


