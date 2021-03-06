package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Db contacts.
 */
public class DBContacts {
    /**
     * Gets all contacts.
     *
     * @return the all contacts
     */
    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        try {

            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact C = new Contact(id,name,email);

                contactList.add(C);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return contactList;
    }
}
