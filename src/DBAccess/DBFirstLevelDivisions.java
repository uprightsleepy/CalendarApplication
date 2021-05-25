package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivision;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFirstLevelDivisions {
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
