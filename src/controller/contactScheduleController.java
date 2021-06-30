package controller;

import DBAccess.DBContacts;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class contactScheduleController implements Initializable {

    public TableView<Appointment> ACList;
    public TableColumn<Appointment,Integer> idCol;
    public TableColumn<Appointment,String> titleCol;
    public TableColumn<Appointment,String> descCol;
    public TableColumn<Appointment,String> typeCol;
    public TableColumn<Appointment,String> startCol;
    public TableColumn<Appointment,String> endCol;
    public TableColumn<Appointment,Integer> custIdCol;

    public TableView<Appointment> DGList;
    public TableColumn<Appointment,Integer> DGidCol;
    public TableColumn<Appointment,String> DGtitleCol;
    public TableColumn<Appointment,String> DGdescCol;
    public TableColumn<Appointment,String> DGtypeCol;
    public TableColumn<Appointment,String> DGstartCol;
    public TableColumn<Appointment,String> DGendCol;
    public TableColumn<Appointment,Integer> DGcustIdCol;

    public TableView<Appointment> LLList;
    public TableColumn<Appointment,Integer> LLidCol;
    public TableColumn<Appointment,String> LLtitleCol;
    public TableColumn<Appointment,String> LLdescCol;
    public TableColumn<Appointment,String> LLtypeCol;
    public TableColumn<Appointment,String> LLstartCol;
    public TableColumn<Appointment,String> LLendCol;
    public TableColumn<Appointment,Integer> LLcustIdCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();
    }

    public void populate() {

        ObservableList<Contact> contacts = DBContacts.getAllContacts();
        ObservableList<Appointment> ACAppointments = contacts.get(0).getAppointments();
        ObservableList<Appointment> DGAppointments = contacts.get(1).getAppointments();
        ObservableList<Appointment> LLAppointments = contacts.get(2).getAppointments();

        if(ACAppointments != null){

            ACList.setItems(ACAppointments);
            idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }

        if(DGAppointments != null){

            DGList.setItems(DGAppointments);
            DGidCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            DGtitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            DGdescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
            DGtypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            DGstartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            DGendCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            DGcustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }

        if(LLAppointments != null){

            LLList.setItems(LLAppointments);
            LLidCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            LLtitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            LLdescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
            LLtypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            LLstartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            LLendCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            LLcustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
    }

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
