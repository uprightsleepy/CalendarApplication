package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivision;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Db first level divisions.
 */
public class DBFirstLevelDivisions {
    /**
     * Gets all first level divisions.
     *
     * @return the all first level divisions
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Division");
                FirstLevelDivision f = new FirstLevelDivision(id, name);

                fldList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fldList;
    }

    /**
     * Gets all us divisions.
     *
     * @return the all us divisions
     */
    public static ObservableList<FirstLevelDivision> getAllUSDivisions() {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Division");
                FirstLevelDivision f = new FirstLevelDivision(id, name);

                fldList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fldList;
    }

    /**
     * Gets all uk divisions.
     *
     * @return the all uk divisions
     */
    public static ObservableList<FirstLevelDivision> getAllUKDivisions() {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Division");
                FirstLevelDivision f = new FirstLevelDivision(id, name);

                fldList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fldList;
    }

    /**
     * Gets all canada divisions.
     *
     * @return the all canada divisions
     */
    public static ObservableList<FirstLevelDivision> getAllCanadaDivisions() {
        ObservableList<FirstLevelDivision> fldList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Division");
                FirstLevelDivision f = new FirstLevelDivision(id, name);

                fldList.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fldList;
    }
}
