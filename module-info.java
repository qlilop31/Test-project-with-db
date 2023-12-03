module com.qlilop.kurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires org.junit.jupiter.api;


    opens com.qlilop.kurs to javafx.fxml;
    exports com.qlilop.kurs;
    exports com.qlilop.kurs.mainScreen;
    opens com.qlilop.kurs.mainScreen to javafx.fxml;
    exports com.qlilop.kurs.db;
    opens com.qlilop.kurs.db to javafx.fxml;
    exports com.qlilop.kurs.loginScreen;
    opens com.qlilop.kurs.loginScreen to javafx.fxml;
    exports com.qlilop.kurs.accountScreen;
    opens com.qlilop.kurs.accountScreen to javafx.fxml;
    exports com.qlilop.kurs.models;
    opens com.qlilop.kurs.models to javafx.fxml;
}