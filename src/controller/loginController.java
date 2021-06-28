package controller;

import DBAccess.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.*;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

import java.net.URL;

public class loginController implements Initializable {

    public Label locationID;

    public TextField usernameTF;
    public PasswordField passwordTF;
    public Button loginButton;
    public boolean loginSuccessful = false;
    public ImageView leftPanel;

    Image frenchPanel = new Image("side_panel_fr.png");

    Locale defaultLocale = Locale.getDefault();
    ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", Locale.US);

    int attempt = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        attempt = 0;

        if(defaultLocale.getDisplayLanguage().equals("French")) {

            bundle = ResourceBundle.getBundle("MessageBundle", Locale.CANADA_FRENCH);
            usernameTF.setPromptText(bundle.getString("username"));
            passwordTF.setPromptText(bundle.getString("password"));
            loginButton.setText(bundle.getString("login"));
            leftPanel.setImage(frenchPanel);
        }

        TimeZone timezone = TimeZone.getDefault();
        String zoneID = timezone.getID();
        locationID.setText(zoneID);
    }

    public void login(ActionEvent actionEvent) throws IOException {

        ObservableList<User> userList = DBUsers.getAllUsers();

        for(User user : userList) {

            if(!user.getUsername().equals(usernameTF.getText())) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("errorTitle"));
                alert.setContentText(bundle.getString("usernameError"));
                alert.showAndWait();

                recordAttempt();
            } else if(!user.getPassword().equals(passwordTF.getText())) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("errorTitle"));
                alert.setContentText(bundle.getString("passwordError"));
                alert.showAndWait();

                recordAttempt();
            } else if (user.getPassword().equals(passwordTF.getText()) && user.getUsername().equals(usernameTF.getText()) && attempt > 2){

                susLogin();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
                Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

                Scene scene = new Scene(root,1000,700);
                stage.setTitle("Customer View");
                stage.setScene(scene);
                stage.show();
            } else {

                loginSuccessful = true;
                recordAttempt();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/mainScreen.fxml")));
                Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

                Scene scene = new Scene(root,1000,700);
                stage.setTitle("Customer View");
                stage.setScene(scene);
                stage.show();
            }
            break;
        }
    }

    public void recordAttempt() throws IOException{

        Date date = new Date();

        String str = "Login attempt recorded at " + date +" | Login Successful: " + loginSuccessful;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/login_activity.txt", true));
        writer.append('\n');
        writer.append(str);
        writer.close();
        attempt++;
    }

    public void susLogin() throws IOException{

        Date date = new Date();

        String str = "Suspicious Successful Login attempt recorded at " + date;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/login_activity.txt", true));
        writer.append('\n');
        writer.append(str);
        writer.close();
    }
}
