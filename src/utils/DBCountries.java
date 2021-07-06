package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.*;


/**
 * The type Db countries.
 */
public class DBCountries {

    /**
     * Gets all countries.
     *
     * @return the all countries
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries C = new Countries(countryID, countryName);

                countriesList.add(C);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countriesList;
    }

    /**
     * Check date conversion.
     */
    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date FROM countries";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
