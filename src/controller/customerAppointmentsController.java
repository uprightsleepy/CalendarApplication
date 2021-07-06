package controller;

import utils.DBAppointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Customer appointments controller.
 */
public class customerAppointmentsController implements Initializable {

    /**
     * The Month selector.
     */
    public ComboBox<Month> monthSelector;

    /**
     * The Ps label.
     */
    public Label psLabel;
    /**
     * The Db label.
     */
    public Label dbLabel;
    /**
     * The Is label.
     */
    public Label isLabel;
    /**
     * The Dm label.
     */
    public Label dmLabel;
    /**
     * The W label.
     */
    public Label wLabel;
    /**
     * The Tb label.
     */
    public Label tbLabel;

    /**
     * The Planning sessions.
     */
    int planningSessions = 0;
    /**
     * The De briefing sessions.
     */
    int deBriefingSessions = 0;
    /**
     * The Info sharing sessions.
     */
    int infoSharingSessions = 0;
    /**
     * The Decision making sessions.
     */
    int decisionMakingSessions = 0;
    /**
     * The Workshop sessions.
     */
    int workshopSessions = 0;
    /**
     * The Team building sessions.
     */
    int teamBuildingSessions = 0;


    /**
     * The Appointments.
     */
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populate();
    }

    /**
     * Populates the user view.
     */
    public void populate() {

        monthSelector.getItems().addAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST,
                Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
    }

    /**
     * Sets month selector.
     */
    public void setMonthSelector() {

        planningSessions = 0;
        deBriefingSessions = 0;
        infoSharingSessions = 0;
        decisionMakingSessions = 0;
        workshopSessions = 0;
        teamBuildingSessions = 0;

        psLabel.setText("0");
        dbLabel.setText("0");
        isLabel.setText("0");
        dmLabel.setText("0");
        wLabel.setText("0");
        tbLabel.setText("0");

        for(Appointment a : appointments) {

            if(a.getMonth(a.getStart()).equals(monthSelector.getSelectionModel().getSelectedItem())) {

                if(a.getType().equals("Planning Session")) {

                    planningSessions++;
                    psLabel.setText(String.valueOf(planningSessions));
                } if(a.getType().equals("De-Briefing")) {

                    deBriefingSessions++;
                    dbLabel.setText(String.valueOf(deBriefingSessions));
                } if(a.getType().equals("Info-Sharing")) {

                    infoSharingSessions++;
                    isLabel.setText(String.valueOf(infoSharingSessions));
                } if(a.getType().equals("Decision Making")) {

                    decisionMakingSessions++;
                    dmLabel.setText(String.valueOf(decisionMakingSessions));
                } if(a.getType().equals("Workshop")) {

                    workshopSessions++;
                    wLabel.setText(String.valueOf(workshopSessions));
                } if(a.getType().equals("Team Building")){

                    teamBuildingSessions++;
                    tbLabel.setText(String.valueOf(teamBuildingSessions));
                }
            }
        }
    }

    /**
     * Back to reports.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void backToReports(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will return to the Reports Menu. Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/reports.fxml")));
            Stage stage = (Stage)((Button)(actionEvent.getSource())).getScene().getWindow();

            Scene scene = new Scene(root,1000,700);
            stage.setTitle("Report Selector");
            stage.setScene(scene);

            stage.show();
        }
    }
}