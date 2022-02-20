module com.example.muzeumfrontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.muzeumfrontendjavafx to javafx.fxml, com.google.gson;
    exports com.example.muzeumfrontendjavafx;
    exports com.example.muzeumfrontendjavafx.api;
    opens com.example.muzeumfrontendjavafx.api to javafx.fxml;
    opens com.example.muzeumfrontendjavafx.muzeum to javafx.base, com.google.gson;
}