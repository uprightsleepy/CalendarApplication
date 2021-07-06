module WGU.C195PA {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;

    opens controller;

    exports model;
    exports controller;
    exports utils;
    opens utils;
}