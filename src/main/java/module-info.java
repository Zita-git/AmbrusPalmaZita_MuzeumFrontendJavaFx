module com.example.muzeumfrontendjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.muzeumfrontendjavafx to javafx.fxml;
    exports com.example.muzeumfrontendjavafx;
}