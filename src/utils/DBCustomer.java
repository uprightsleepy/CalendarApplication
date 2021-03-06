package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * The type Db customer.
 */
public class DBCustomer {

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                Customer C = new Customer(customerID, customerName,address,postalCode,phone, 0,0," ");


                String division = C.getDivisionName(divisionID);
                C.setDivision(division);

                int countryID = C.getCountryID(divisionID);
                C.setCountryID(countryID);

                String country = C.getCountry();
                C.setCountry(country);

                customerList.add(C);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return customerList;
    }

    /**
     * Lookup customer observable list.
     *
     * @param customerName the customer name
     * @return the observable list
     */
    public static ObservableList<Customer> lookupCustomer(String customerName) {

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        for(Customer c : DBCustomer.getAllCustomers()) {

            if(c.getName().contains(customerName) || c.getName().toLowerCase(Locale.ROOT).contains(customerName)) {

                customers.add(c);
            }
        }

        return customers;
    }

    /**
     * Lookup customer observable list.
     *
     * @param customerID the customer id
     * @return the observable list
     */
    public static ObservableList<Customer> lookupCustomer(int customerID) {

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        for(Customer c : DBCustomer.getAllCustomers()) {

            if(c.getId() == customerID) {

                customers.add(c);
            }
        }

        return customers;
    }
}