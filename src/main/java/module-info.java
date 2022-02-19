module com.example.muzeumfrontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.muzeumfrontendjavafx to javafx.fxml;
    exports com.example.muzeumfrontendjavafx;
    exports com.example.muzeumfrontendjavafx.api;
    opens com.example.muzeumfrontendjavafx.api to javafx.fxml;
}