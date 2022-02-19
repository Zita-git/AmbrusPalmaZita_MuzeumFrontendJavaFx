package com.example.muzeumfrontendjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {


    @FXML
    private ListView listazasFestmeny;
    @FXML
    private Button torlesBTNFestmeny,hozaadasBTNFestmeny,modositBTNFestmeny;
    @FXML
    private TextArea titleFestmeny,titleFestmenymodosit;
    @FXML
    private CheckBox onDisplayFestmeny,onDisplayFestmenymodosit;
    @FXML
    private DatePicker datePickerFestmeny,datePickerFestmenymodosit;


    @FXML
    private ListView listazasSzobrok;
    @FXML
    private Button torlesBTNSzobrok,hozaadasBTNSzobrok,modositBTNSzobrok;
    @FXML
    private TextField nevSzobrok,nevModositSzobrok;
    @FXML
    private Spinner magassagSzobrok,arSzobrok,magassagModositSzobrok,arModositSzobrok;


    public void onTorlesFestmenyBTNClick(ActionEvent actionEvent) {
    }

    public void onHozzaadasFestmenyBTNClick(ActionEvent actionEvent) {
    }

    public void onModositasFestmenyBTNClick(ActionEvent actionEvent) {
    }

    public void onTorlesSzobrokBTNClick(ActionEvent actionEvent) {
    }

    public void onHozzaadasSzobrokBTNClick(ActionEvent actionEvent) {
    }

    public void onModositasSzobrokBTNClick(ActionEvent actionEvent) {
    }
}
