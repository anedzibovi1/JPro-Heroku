package com.jpro.hellojpro.controller;

import com.jfoenix.controls.*;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.listCells.updateDeleteCellCas;
import com.jpro.hellojpro.model.Cas;
import com.jpro.hellojpro.model.Predmet;
import com.jpro.hellojpro.model.Student;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RasporedFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spRaspored;

    @FXML
    protected StackPane spCas;

    @FXML
    protected StackPane spPredmet;

    @FXML
    protected ChoiceBox<Predmet> cbPredmet;

    @FXML
    protected ChoiceBox<String> cbTipCasa;

    @FXML
    protected JFXListView<Cas> lvCasovi;

    @FXML
    protected JFXTextField tfNazivPredmeta;

    @FXML
    protected DatePicker dpDatumO;

    @FXML
    protected Spinner<Integer> sSatiP;

    @FXML
    protected Spinner<Integer> sSatiK;

    @FXML
    protected Spinner<Integer> sMinuteP;

    @FXML
    protected Spinner<Integer> sMinuteK;

    @FXML
    protected JFXRadioButton rbDa;

    @FXML
    protected JFXRadioButton rbNe;

    @FXML
    protected ToggleGroup ponavljanje;

    @FXML
    protected JFXButton btnIzmijeniCas;

    @FXML
    protected JFXButton btnDodajCas;

    @FXML
    protected JFXButton btnPostavke;

    @FXML
    protected JFXComboBox<Predmet> cbFilter;


    public RasporedFXMLController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        URL url = getClass().getResource(student.getSlika());
        Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
        ImageView viewStudent = new ImageView(imgStudent);
        btnPostavke.setGraphic(viewStudent);

        spCas.setVisible(false);
        spPredmet.setVisible(false);

        spCas.setVisible(true);
        spPredmet.setVisible(true);
        ScaleTransition ft1 = new ScaleTransition(Duration.millis(900), spCas);
        ft1.setFromX(0);
        ft1.setFromY(0);
        ft1.setToX(1);
        ft1.setToY(1);
        ft1.play();

        ScaleTransition ft2 = new ScaleTransition(Duration.millis(900), spPredmet);
        ft2.setFromX(0);
        ft2.setFromY(0);
        ft2.setToX(1);
        ft2.setToY(1);
        ft2.play();

        cbPredmet.setItems(studyntDAO.getPredmetiStudent(student.getId()));
        cbPredmet.getSelectionModel().selectFirst();

        lvCasovi.setItems(studyntDAO.getCasoviStudent(student.getId()));
        lvCasovi.setCellFactory(param -> new updateDeleteCellCas(studyntDAO, spCas, lvCasovi));

        ArrayList<String> tipovi = new ArrayList<>();

        tipovi.add("Tutorijal");
        tipovi.add("Vjezbe");
        tipovi.add("Predavanje");
        tipovi.add("Konsultacije");

        ObservableList<String> tipoviCasova = tipovi.stream().collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        cbTipCasa.setItems(tipoviCasova);
        cbTipCasa.getSelectionModel().selectFirst();

        btnIzmijeniCas.setCursor(Cursor.HAND);
        btnDodajCas.setCursor(Cursor.HAND);


        cbFilter.getItems().add(new Predmet("Svi časovi"));
        cbFilter.getItems().addAll(studyntDAO.getPredmetiStudent(student.getId()));
        cbFilter.setPromptText("Časovi");
        cbFilter.setStyle("-fx-font-size: 20px;");


        cbFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected

            if (newVal != null && !cbFilter.getSelectionModel().isSelected(0)) {
                lvCasovi.setItems(studyntDAO.getCasoviStudent(student.getId()).stream().filter(p -> p.getPredmet().equals(newVal)).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
                lvCasovi.refresh();
            }
            else if(newVal != null && cbFilter.getSelectionModel().isSelected(0)) {
                lvCasovi.setItems(studyntDAO.getCasoviStudent(student.getId()));
                lvCasovi.refresh();
            }
        });

    }

    public void dodajPredmetAction(ActionEvent actionEvent) {
        Predmet noviPredmet = new Predmet(tfNazivPredmeta.getText(), student);
        if(studyntDAO.getPredmetiStudent(student.getId()).stream().noneMatch(t -> t.getNaziv().equals(noviPredmet.getNaziv()))) {
            studyntDAO.dodajPredmet(noviPredmet);
            cbPredmet.getItems().add(noviPredmet);
        }
    }

    public void dodajCasAction(ActionEvent actionEvent) {
        String noviDatumString = dpDatumO.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate noviDatum = LocalDate.parse(noviDatumString);
        LocalTime novoVrijemeP, novoVrijemeK;
        String satiP = sSatiP.getValue().toString();
        String satiK = sSatiK.getValue().toString();
        String minuteP = sMinuteP.getValue().toString();
        String minuteK = sMinuteK.getValue().toString();

        if(minuteP.length() == 1) {
            minuteP = "0" + minuteP;
        }
        if(minuteK.length() == 1) {
            minuteK = "0" + minuteK;
        }
        if(satiP.length() == 1) {
            satiP = "0" + satiP;
        }
        if(satiK.length() == 1) {
            satiK = "0" + satiK;
        }

        novoVrijemeP = LocalTime.parse(satiP + ":" + minuteP);
        novoVrijemeK = LocalTime.parse(satiK + ":" + minuteK);


        JFXRadioButton ponavljanjeRB = (JFXRadioButton) ponavljanje.getSelectedToggle();
        Cas noviCas = new Cas(noviDatum, novoVrijemeP, novoVrijemeK, cbTipCasa.getValue(), cbPredmet.getValue(), ponavljanjeRB.getText());
        studyntDAO.dodajCas(noviCas);
        lvCasovi.getItems().add(noviCas);
        lvCasovi.refresh();
    }

    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);
    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);

        spRaspored.getScene().getWindow().setOnShown(pretragaFXMLController::adjustUI);

    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spRaspored.getChildren().setAll(stackPane);
    }

    public void izmijeniCasAction(ActionEvent actionEvent) {
        Cas cas = lvCasovi.getSelectionModel().getSelectedItem();

        Cas noviCas = new Cas();
        noviCas.setId(cas.getId());
        noviCas.setDatumOdrzavanja(dpDatumO.getValue());
        noviCas.setVrijemePocetka(LocalTime.of(sSatiP.getValue(),sMinuteP.getValue()));
        noviCas.setVrijemeKraja(LocalTime.of(sSatiK.getValue(),sMinuteK.getValue()));
        noviCas.setTipCasa(cbTipCasa.getSelectionModel().getSelectedItem());
        noviCas.setPredmet(cbPredmet.getSelectionModel().getSelectedItem());
        RadioButton rb = (RadioButton) ponavljanje.getSelectedToggle();
        noviCas.setPonavljanje(rb.getText());

        studyntDAO.izmijeniCas(noviCas);
        lvCasovi.refresh();
    }

    public void obrisiUnoseAction(ActionEvent actionEvent) {
        btnDodajCas.setVisible(true);
        btnIzmijeniCas.setVisible(false);

        cbPredmet.getSelectionModel().selectFirst();
        cbTipCasa.getSelectionModel().selectFirst();
        dpDatumO.getEditor().clear();
        sSatiP.getValueFactory().setValue(9);
        sSatiK.getValueFactory().setValue(9);
        sMinuteP.getValueFactory().setValue(0);
        sMinuteK.getValueFactory().setValue(0);
        rbDa.setSelected(true);
    }

}
