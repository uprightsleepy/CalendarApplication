package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.*;
import java.util.*;

import javafx.stage.Stage;

import java.net.URL;

public class loginController implements Initializable {
    public Label locationID;
    public TextField usernameTF;
    public PasswordField passwordTF;
    public Button loginButton;

    public boolean loginSuccessful = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale currentLocale = Locale.getDefault();
//        String language = System.getProperty("user.language");
        String country = currentLocale.getDisplayCountry();
        locationID.setText(country);
    }

    public void login(ActionEvent actionEvent) throws IOException {
        if(!usernameTF.getText().equals("test")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Incorrect Username. Please enter the correct login credentials.");
            alert.showAndWait();
            recordAttempt();
        } else if(!passwordTF.getText().equals("test")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Incorrect Password. Please enter the correct login credentials.");
            alert.showAndWait();
            recordAttempt();
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
    }

    public void recordAttempt() throws IOException{
        Date date = new Date();

        String str = "Login attempt recorded at " + date +" | Login Successful: " + loginSuccessful;
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/login_activity.txt", true));
        writer.append('\n');
        writer.append(str);
        writer.close();
    }
}
