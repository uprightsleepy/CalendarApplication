package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;

public class Main extends Application {
    public ImageView leftPanel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
        primaryStage.setTitle("Login Page");
        //Locale.setDefault(new Locale("fr"));
        primaryStage.setScene(new Scene(root,520,400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
