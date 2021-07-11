package com.jpro.hellojpro.controller;

import com.jfoenix.controls.*;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.listCells.updateDeleteCellIspit;
import com.jpro.hellojpro.model.Ispit;
import com.jpro.hellojpro.model.Predmet;
import com.jpro.hellojpro.model.Student;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class IspitiFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spIspiti;

    @FXML
    protected StackPane spIspit;

    @FXML
    protected JFXTextField tfNaziv;

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
    protected ChoiceBox<Predmet> cbPredmet;

    @FXML
    protected JFXListView<Ispit> lvIspiti;

    @FXML
    protected JFXButton btnDodajIspit;

    @FXML
    protected JFXButton btnIzmijeniIspit;

    @FXML
    protected Button btnGlavna;

    @FXML
    protected Button btnKalendar;

    @FXML
    protected Button btnIspiti;

    @FXML
    protected Button btnZadaci;

    @FXML
    protected Button btnRaspored;

    @FXML
    protected Button btnPretraga;

    @FXML
    protected JFXButton btnPostavke;

    @FXML
    protected JFXComboBox<Predmet> cbFilter;


    public IspitiFXMLController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnGlavna.setCursor(Cursor.HAND);
        btnZadaci.setCursor(Cursor.HAND);
        btnIspiti.setCursor(Cursor.HAND);
        btnKalendar.setCursor(Cursor.HAND);
        btnPretraga.setCursor(Cursor.HAND);
        btnRaspored.setCursor(Cursor.HAND);

        URL url = getClass().getResource(student.getSlika());
        Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
        ImageView viewStudent = new ImageView(imgStudent);
        btnPostavke.setGraphic(viewStudent);

        spIspit.setVisible(false);

        spIspit.setVisible(true);

        ScaleTransition ft1 = new ScaleTransition(Duration.millis(900), spIspit);
        ft1.setFromX(0);
        ft1.setFromY(0);
        ft1.setToX(1);
        ft1.setToY(1);
        ft1.play();

        cbPredmet.setItems(studyntDAO.getPredmetiStudent(student.getId()));
        cbPredmet.getSelectionModel().selectFirst();

        lvIspiti.setItems(studyntDAO.getIspitiStudent(student.getId()));
        lvIspiti.setCellFactory(param -> new updateDeleteCellIspit(studyntDAO, spIspit, lvIspiti));

        cbFilter.getItems().add(new Predmet("Svi predmeti"));
        cbFilter.getItems().addAll(studyntDAO.getPredmetiStudent(student.getId()));
        cbFilter.setPromptText("Filtriraj po predmetu");
        cbFilter.setStyle("-fx-font-size: 20px;");


        cbFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected

            if (newVal != null && !cbFilter.getSelectionModel().isSelected(0)) {
                lvIspiti.setItems(studyntDAO.getIspitiStudent(student.getId()).stream().filter(p -> p.getPredmet().equals(newVal)).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
                lvIspiti.refresh();
            }
            else if(newVal != null && cbFilter.getSelectionModel().isSelected(0)) {
                lvIspiti.setItems(studyntDAO.getIspitiStudent(student.getId()));
                lvIspiti.refresh();
            }
        });


    }

    public void dodajIspitAction(ActionEvent actionEvent) {
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

        Ispit noviIspit = new Ispit(tfNaziv.getText(), noviDatum, novoVrijemeP, novoVrijemeK, cbPredmet.getValue());
        studyntDAO.dodajIspit(noviIspit);
        lvIspiti.getItems().add(noviIspit);
        lvIspiti.refresh();
    }

    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);
    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);


    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spIspiti.getChildren().setAll(stackPane);
    }


    public StudyntDAO getStudyntDAO() {
        return studyntDAO;
    }

    public void setStudyntDAO(StudyntDAO studyntDAO) {
        this.studyntDAO = studyntDAO;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void izmijeniIspitAction(ActionEvent actionEvent) {
        Ispit ispit = lvIspiti.getSelectionModel().getSelectedItem();

        Ispit noviIspit = new Ispit();
        noviIspit.setId(ispit.getId());
        noviIspit.setDatumOdrzavanja(dpDatumO.getValue());
        noviIspit.setVrijemePocetka(LocalTime.of(sSatiP.getValue(),sMinuteP.getValue()));
        noviIspit.setVrijemeKraja(LocalTime.of(sSatiK.getValue(),sMinuteK.getValue()));
        noviIspit.setNaziv(tfNaziv.getText());
        noviIspit.setPredmet(cbPredmet.getSelectionModel().getSelectedItem());

        studyntDAO.izmijeniIspit(noviIspit);
        lvIspiti.refresh();
    }

    public void obrisiUnoseAction(ActionEvent actionEvent) {
        btnDodajIspit.setVisible(true);
        btnIzmijeniIspit.setVisible(false);

        tfNaziv.setText("");
        cbPredmet.getSelectionModel().selectFirst();
        dpDatumO.getEditor().clear();
        sSatiP.getValueFactory().setValue(9);
        sSatiK.getValueFactory().setValue(9);
        sMinuteP.getValueFactory().setValue(0);
        sMinuteK.getValueFactory().setValue(0);
    }

}
