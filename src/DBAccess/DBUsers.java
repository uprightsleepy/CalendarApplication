package DBAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                User login = new User(userID,username,password);
                System.out.println(login);

                userList.add(login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
