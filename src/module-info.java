module WGU.C195PA {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    opens controller;
    opens DBAccess;

    exports model;
    exports DBAccess;
    exports controller;
}