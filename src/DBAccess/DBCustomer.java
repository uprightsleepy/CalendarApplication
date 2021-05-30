package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomer {

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


                String division = C.getDivision(divisionID);
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
}
