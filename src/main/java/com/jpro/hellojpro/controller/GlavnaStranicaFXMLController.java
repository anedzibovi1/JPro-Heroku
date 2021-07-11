package com.jpro.hellojpro.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.listCells.GlavnaListCellCas;
import com.jpro.hellojpro.listCells.GlavnaListCellIspit;
import com.jpro.hellojpro.listCells.GlavnaListCellZadatak;
import com.jpro.hellojpro.model.*;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GlavnaStranicaFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spGlavnaStr;

    @FXML
    protected StackPane spCasovi;

    @FXML
    protected StackPane spIspiti;

    @FXML
    protected Text tDatum;

    @FXML
    protected Text tCasovi;

    @FXML
    protected Text tIspiti1;

    @FXML
    protected Text tZadaci;

    @FXML
    protected Text tIspiti2;

    @FXML
    protected Text tNemaCasova;

    @FXML
    protected Text tNemaIspita;

    @FXML
    protected JFXListView<Cas> lvCasovi;

    @FXML
    protected JFXListView<Ispit> lvIspiti1;

    @FXML
    protected JFXListView<Zadatak> lvZadaci;

    @FXML
    protected JFXListView<Ispit> lvIspiti2;

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


    public GlavnaStranicaFXMLController(Student student, StudyntDAO model) {
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

        tNemaCasova.setVisible(false);
        tNemaIspita.setVisible(false);

        lvCasovi.setItems(studyntDAO.getCasoviDanas(student));
        lvCasovi.setExpanded(true);
        lvCasovi.setCellFactory(param -> new GlavnaListCellCas());
        lvCasovi.depthProperty().set(1);

        lvIspiti1.setItems(studyntDAO.getIspitiDanas(student));
        lvIspiti1.setExpanded(true);
        lvIspiti1.depthProperty().set(1);
        lvIspiti1.setCellFactory(param -> new GlavnaListCellIspit());

        lvZadaci.setItems(studyntDAO.getZadaci(student));
        lvZadaci.setExpanded(true);
        lvZadaci.depthProperty().set(1);
        lvZadaci.setCellFactory(param -> new GlavnaListCellZadatak());

        lvIspiti2.setItems(studyntDAO.getIspiti(student));
        lvIspiti2.setExpanded(true);
        lvIspiti2.depthProperty().set(1);
        lvIspiti2.setCellFactory(param -> new GlavnaListCellIspit());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd");
        tDatum.setText(LocalDate.now().format(formatter));

        tCasovi.setText(String.valueOf(studyntDAO.casoviDanas(student.getId()).size()));
        tIspiti1.setText(String.valueOf(studyntDAO.ispitiDanas(student.getId()).size()));

        tZadaci.setText(String.valueOf(studyntDAO.zadaci(student.getId()).size()));
        tIspiti2.setText(String.valueOf(studyntDAO.ispiti(student.getId()).size()));

        if(lvCasovi.getItems().size() == 0) tNemaCasova.setVisible(true);
        if(lvIspiti1.getItems().size() == 0) tNemaIspita.setVisible(true);


        tZadaci.setVisible(true);
        tCasovi.setVisible(true);
        tIspiti1.setVisible(true);
        tIspiti2.setVisible(true);

        ScaleTransition ft1 = new ScaleTransition(Duration.millis(950), tZadaci);
        ft1.setFromX(0.5);
        ft1.setFromY(0.5);
        ft1.setToX(2);
        ft1.setToY(2);
        ft1.play();

        ScaleTransition ft2 = new ScaleTransition(Duration.millis(950), tCasovi);
        ft2.setFromX(0.1);
        ft2.setFromY(0.1);
        ft2.setToX(2);
        ft2.setToY(2);
        ft2.play();

        ScaleTransition ft3 = new ScaleTransition(Duration.millis(950), tIspiti1);
        ft3.setFromX(0.1);
        ft3.setFromY(0.1);
        ft3.setToX(2);
        ft3.setToY(2);
        ft3.play();

        ScaleTransition ft4 = new ScaleTransition(Duration.millis(950), tIspiti2);
        ft4.setFromX(0.5);
        ft4.setFromY(0.5);
        ft4.setToX(2);
        ft4.setToY(2);
        ft4.play();

    }

    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);


    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);

        spGlavnaStr.getScene().getWindow().setOnShown(pretragaFXMLController::adjustUI);

    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spGlavnaStr.getChildren().setAll(stackPane);
    }

}
