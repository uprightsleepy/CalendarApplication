package controller;

import utils.DBCustomer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Appointments per customer.
 */
public class appointmentsPerCustomer implements Initializable {

    /**
     * The Customers.
     */
    ObservableList<Customer> customers = DBCustomer.getAllCustomers();

    /**
     * The Customer table.
     */
    public TableView<Customer> customerTable;
    /**
     * The Id col.
     */
    public TableColumn<Customer, Integer> idCol;
    /**
     * The Name col.
     */
    public TableColumn<Customer, String> nameCol;
    /**
     * The Number col.
     */
    public TableColumn<Customer, Integer> numberCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();
    }

    /**
     * Populates the user view.
     */
    public void populate() {

        customerTable.setItems(customers);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointments"));
    }

    /**
     * Back to reports menu.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void backtoReports(ActionEvent actionEvent) throws IOException {

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