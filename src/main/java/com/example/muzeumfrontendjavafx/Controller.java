package com.example.muzeumfrontendjavafx;

import com.example.muzeumfrontendjavafx.HelloController;
import com.example.muzeumfrontendjavafx.api.PaintingsApi;
import com.example.muzeumfrontendjavafx.api.StatuesApi;
import com.example.muzeumfrontendjavafx.muzeum.Paintings;
import com.example.muzeumfrontendjavafx.muzeum.Statues;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class Controller extends HelloController {

    @FXML
    public Tab modositasFestmenyTab;
    @FXML
    private TableView<Paintings> listazasFestmeny;
    @FXML
    private TableColumn<Paintings, Integer> idFestmeny;
    @FXML
    private TableColumn<Paintings, Boolean> kiallitva;
    @FXML
    private TableColumn<Paintings, String> cimFestmeny,keszultFestmeny;
    @FXML
    private Button torlesBTNFestmeny,hozaadasBTNFestmeny,modositBTNFestmeny;
    @FXML
    private TextArea titleFestmeny,titleFestmenymodosit;
    @FXML
    private CheckBox onDisplayFestmeny,onDisplayFestmenymodosit;
    @FXML
    private Spinner<Integer> datePickerFestmeny,datePickerFestmenymodosit;


    @FXML
    public Tab modositasSzobrokTab;
    @FXML
    private TableView<Statues> listazasSzobrok;
    @FXML
    private TableColumn<Statues,Integer> idSzobor,magassagSzobor,arSzobor;
    @FXML
    private TableColumn<Statues,String> nevSzobor;
    @FXML
    private Button torlesBTNSzobrok,hozaadasBTNSzobrok,modositBTNSzobrok;
    @FXML
    private TextField nevSzobrok,nevModositSzobrok;
    @FXML
    private Spinner<Integer> magassagSzobrok,arSzobrok,magassagModositSzobrok,arModositSzobrok;


    public void onTorlesFestmenyBTNClick(ActionEvent actionEvent) {
        Paintings torlendo = listazasFestmeny.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos törölni szeretné a(z) " + torlendo.getTitle() + " című festményt?")) {
            return;
        }
        try {
            boolean siker = PaintingsApi.delete(torlendo.getId());
            alert(siker ? "Sikertelen törlés!" : "Sikeres törlés!" );
            festmenyListaFeltolt();
        } catch (IOException e) {
            hibaKiir(e);
        }

    }

    public void kijelolFestmeny(MouseEvent mouseEvent) {
        int selectedIndex = listazasFestmeny.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            torlesBTNFestmeny.setDisable(false);
            modositasFestmenyTab.setDisable(false);
            modositBTNFestmeny.setDisable(false);

            Paintings modositando = listazasFestmeny.getSelectionModel().getSelectedItem();
            titleFestmenymodosit.setText(modositando.getTitle());
            onDisplayFestmenymodosit.setSelected(modositando.getOn_display());
            datePickerFestmenymodosit.getValueFactory().setValue(modositando.getYear());
        }else{
            torlesBTNFestmeny.setDisable(true);
            modositasFestmenyTab.setDisable(true);
        }
    }

    public void onHozzaadasFestmenyBTNClick(ActionEvent actionEvent) {
        try {
            String cim = titleFestmeny.getText();
            if (cim.isEmpty()) {
                alert("A cím megadása kötelező");
                return;
            }

            boolean kiallitva = onDisplayFestmeny.isSelected();

            int ev = 0;
            try {
                ev = datePickerFestmeny.getValue();
            } catch (NullPointerException e) {
                alert("Az év megadása kötelező");
                return;
            }catch (Exception e) {
                alert("Az új évnek 0 felett és 2023 alatt kell lennie!");
                return;
            }
            if (ev >= 2022) {
                alert("Az új évnek 2023 alatt kell lennie!");
                return;
            }
            if (ev <= 0) {
                alert("Az új évnek 0 felett kell lennie!");
                return;
            }

            try {
                Paintings uj = new Paintings(0, cim, kiallitva, ev);
                Paintings letrehozott = PaintingsApi.post(uj);
                if (letrehozott != null) {
                    alert("Sikeres hozzáadás!");
                    titleFestmeny.setText("");
                    onDisplayFestmeny.setSelected(false);
                    datePickerFestmeny.getValueFactory().setValue(2000);
                } else {
                    alert("Sikertelen hozzáadás!");
                }
            } catch (IOException e) {
                hibaKiir(e);
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
        festmenyListaFeltolt();
    }

    public void onModositasFestmenyBTNClick(ActionEvent actionEvent) {
        Paintings modositando = listazasFestmeny.getSelectionModel().getSelectedItem();

        String cim = titleFestmenymodosit.getText().trim();
        if (titleFestmenymodosit.getText().isEmpty()){
            alert("Az új cím megadása kötelező");
            return;
        }

        int ev=0;
        try {
            ev = datePickerFestmenymodosit.getValue();
        } catch (NullPointerException ex){
            alert("Az új év megadása kötelező");
            return;
        }catch (Exception e) {
            alert("Az új évnek 0 felett és 2023 alatt kell lennie!");
            return;
        }
        if (ev >= 2022) {
            alert("Az új évnek 2023 alatt kell lennie!");
            return;
        }
        if (ev <= 0) {
            alert("Az új évnek 0 felett kell lennie!");
            return;
        }

        modositando.setTitle(cim);
        modositando.setYear(ev);
        modositando.setOn_display(onDisplayFestmenymodosit.isSelected());


        try {
            Paintings modositott = PaintingsApi.put(modositando, modositando.getId());
            if (modositott != null) {
                alertWait("Sikeres módosítás");
            } else {
                alert("Sikertelen módosítás");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleFestmenymodosit.setText("");
        onDisplayFestmenymodosit.setSelected(false);
        datePickerFestmenymodosit.getValueFactory().setValue(2000);
        festmenyListaFeltolt();
    }




    public void onTorlesSzobrokBTNClick(ActionEvent actionEvent) {
        Statues torlendo = listazasSzobrok.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos törölni szeretné a(z) " + torlendo.getPerson() + " álltal készített szobrot?")) {
            return;
        }
        try {
            boolean siker = StatuesApi.delete(torlendo.getId());
            alert(siker ? "Sikertelen törlés!" : "Sikeres törlés!" );
            szoborListaFeltolt();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    public void kijelolSzobor(MouseEvent mouseEvent) {
        int selectedIndex = listazasSzobrok.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            torlesBTNSzobrok.setDisable(false);
            modositasSzobrokTab.setDisable(false);
            modositBTNSzobrok.setDisable(false);

            Statues modositando = listazasSzobrok.getSelectionModel().getSelectedItem();
            nevModositSzobrok.setText(modositando.getPerson());
            magassagModositSzobrok.getValueFactory().setValue(modositando.getHeight());
            arModositSzobrok.getValueFactory().setValue(modositando.getPrice());
        }else{
            torlesBTNSzobrok.setDisable(true);
            modositasSzobrokTab.setDisable(true);
        }
    }

    public void onHozzaadasSzobrokBTNClick(ActionEvent actionEvent) {
        try {
            String nev = nevSzobrok.getText();
            if (nev.isEmpty()) {
                alert("A név megadása kötelező");
                return;
            }

            int magassag = 0;
            try {
                magassag = (int) magassagSzobrok.getValue();
            } catch (NullPointerException e) {
                alert("A magasság megadása kötelező");
                return;
            } catch (Exception e) {
                alert("A magasságnak 0 felett és 250 alatt kell lennie!");
                return;
            }
            if (magassag > 250) {
                alert("A magasságnak 250 alatt kell lennie!");
                return;
            }
            if (magassag < 0) {
                alert("A magasságnak 0 felett kell lennie!");
                return;
            }

            int ar = 0;
            try {
                ar = (int) arSzobrok.getValue();
            } catch (NullPointerException e) {
                alert("Az ár megadása kötelező");
                return;
            } catch (Exception e) {
                alert("Az árnak 1000 felett és 1000000000 alatt kell lennie!");
                return;
            }
            if (ar > 1000000000) {
                alert("Az árnak 1000000000 alatt kell lennie!");
                return;
            }
            if (ar < 1000) {
                alert("Az árnak 1000 felett kell lennie!");
                return;
            }

            try {
                Statues uj = new Statues(0, nev, magassag, ar);
                Statues letrehozott = StatuesApi.post(uj);
                if (letrehozott != null) {
                    alert("Sikeres hozzáadás!");
                    nevSzobrok.setText("");
                    magassagSzobrok.getValueFactory().setValue(100);
                    arSzobrok.getValueFactory().setValue(2000);
                } else {
                    alert("Sikertelen hozzáadás!");
                }
            } catch (IOException e) {
                hibaKiir(e);
            }
        } catch (Exception e) {
            hibaKiir(e);
        }
        szoborListaFeltolt();
    }

    public void onModositasSzobrokBTNClick(ActionEvent actionEvent) {
        Statues modositando = listazasSzobrok.getSelectionModel().getSelectedItem();

        String nev = nevModositSzobrok.getText().trim();
        if (nevModositSzobrok.getText().isEmpty()){
            alert("A név megadása kötelező");
            return;
        }

        int magassag = 0;
        try {
            magassag = (int) magassagModositSzobrok.getValue();
        } catch (NullPointerException e) {
            alert("Az új magasság megadása kötelező");
            return;
        } catch (Exception e) {
            alert("A magasságnak 0 felett és 250 alatt kell lennie!");
            return;
        }
        if (magassag > 250) {
            alert("A magasságnak 250 alatt kell lennie!");
            return;
        }
        if (magassag < 0) {
            alert("A magasságnak 0 felett kell lennie!");
            return;
        }




        int ar = 0;
        try {
            ar = (int) arModositSzobrok.getValue();
        } catch (NullPointerException e) {
            alert("Az új ár megadása kötelező");
            return;
        } catch (Exception e) {
            alert("Az árnak 1000 felett és 1000000000 alatt kell lennie!");
            return;
        }
        if (ar > 1000000000) {
            alert("Az árnak 1000000000 alatt kell lennie!");
            return;
        }
        if (ar < 1000) {
            alert("Az árnak 1000 felett kell lennie!");
            return;
        }


        modositando.setPerson(nev);
        modositando.setHeight(magassag);
        modositando.setPrice(ar);


        try {
            Statues modositott = StatuesApi.put(modositando, modositando.getId());
            if (modositott != null) {
                alertWait("Sikeres módosítás");
            } else {
                alert("Sikertelen módosítás");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        nevModositSzobrok.setText("");
        magassagModositSzobrok.getValueFactory().setValue(100);
        arModositSzobrok.getValueFactory().setValue(2000);
        szoborListaFeltolt();
    }


    private void festmenyListaFeltolt() {
        torlesBTNFestmeny.setDisable(true);
        modositBTNFestmeny.setDisable(true);
        try {
            List<Paintings> list = PaintingsApi.get();
            listazasFestmeny.getItems().clear();
            for (Paintings paintings : list) {
                listazasFestmeny.getItems().add(paintings);
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }

    private void szoborListaFeltolt() {
        torlesBTNSzobrok.setDisable(true);
        modositBTNSzobrok.setDisable(true);
        try {
            List<Statues> list = StatuesApi.get();
            listazasSzobrok.getItems().clear();
            for (Statues statues : list) {
                listazasSzobrok.getItems().add(statues);
            }
        } catch (IOException e) {
            hibaKiir(e);
        }
    }


    public void initialize() {
        datePickerFestmeny.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,2022,2000,1));
        datePickerFestmenymodosit.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,2022,2000,1));
        magassagSzobrok.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,250,100,1));
        magassagModositSzobrok.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,250,100,1));
        arSzobrok.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000,1000000000,2000,1));
        arModositSzobrok.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1000,1000000000,2000,1));



        idFestmeny.setCellValueFactory(new PropertyValueFactory<>("id"));
        cimFestmeny.setCellValueFactory(new PropertyValueFactory<>("title"));
        keszultFestmeny.setCellValueFactory(new PropertyValueFactory<>("year"));
        kiallitva.setCellValueFactory(new PropertyValueFactory<>("on_display"));

        idSzobor.setCellValueFactory(new PropertyValueFactory<>("id"));
        nevSzobor.setCellValueFactory(new PropertyValueFactory<>("person"));
        magassagSzobor.setCellValueFactory(new PropertyValueFactory<>("height"));
        arSzobor.setCellValueFactory(new PropertyValueFactory<>("price"));

        szoborListaFeltolt();
        festmenyListaFeltolt();
    }

}
