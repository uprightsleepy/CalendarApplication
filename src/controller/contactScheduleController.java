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

/**
 * The type Contact schedule controller.
 */
public class contactScheduleController implements Initializable {

    /**
     * The Ac list.
     */
    public TableView<Appointment> ACList;
    /**
     * The Id col.
     */
    public TableColumn<Appointment,Integer> idCol;
    /**
     * The Title col.
     */
    public TableColumn<Appointment,String> titleCol;
    /**
     * The Desc col.
     */
    public TableColumn<Appointment,String> descCol;
    /**
     * The Type col.
     */
    public TableColumn<Appointment,String> typeCol;
    /**
     * The Start col.
     */
    public TableColumn<Appointment,String> startCol;
    /**
     * The End col.
     */
    public TableColumn<Appointment,String> endCol;
    /**
     * The Cust id col.
     */
    public TableColumn<Appointment,Integer> custIdCol;

    /**
     * The Dg list.
     */
    public TableView<Appointment> DGList;
    /**
     * The D gid col.
     */
    public TableColumn<Appointment,Integer> DGidCol;
    /**
     * The D gtitle col.
     */
    public TableColumn<Appointment,String> DGtitleCol;
    /**
     * The D gdesc col.
     */
    public TableColumn<Appointment,String> DGdescCol;
    /**
     * The D gtype col.
     */
    public TableColumn<Appointment,String> DGtypeCol;
    /**
     * The D gstart col.
     */
    public TableColumn<Appointment,String> DGstartCol;
    /**
     * The D gend col.
     */
    public TableColumn<Appointment,String> DGendCol;
    /**
     * The D gcust id col.
     */
    public TableColumn<Appointment,Integer> DGcustIdCol;

    /**
     * The Ll list.
     */
    public TableView<Appointment> LLList;
    /**
     * The L lid col.
     */
    public TableColumn<Appointment,Integer> LLidCol;
    /**
     * The L ltitle col.
     */
    public TableColumn<Appointment,String> LLtitleCol;
    /**
     * The L ldesc col.
     */
    public TableColumn<Appointment,String> LLdescCol;
    /**
     * The L ltype col.
     */
    public TableColumn<Appointment,String> LLtypeCol;
    /**
     * The L lstart col.
     */
    public TableColumn<Appointment,String> LLstartCol;
    /**
     * The L lend col.
     */
    public TableColumn<Appointment,String> LLendCol;
    /**
     * The L lcust id col.
     */
    public TableColumn<Appointment,Integer> LLcustIdCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();
    }

    /**
     * Populate.
     */
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
