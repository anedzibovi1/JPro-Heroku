package com.jpro.hellojpro.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PretragaFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;


    @FXML
    protected StackPane spPretraga;

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
    protected JFXComboBox<String> cbTip;

    @FXML
    protected JFXTextField tfPretraga;

    @FXML
    protected JFXListView<String> lvPretrage;

    @FXML
    protected JFXButton btnPostavke;


    public PretragaFXMLController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        URL url = getClass().getResource(student.getSlika());
        Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
        ImageView viewStudent = new ImageView(imgStudent);
        btnPostavke.setGraphic(viewStudent);


        ArrayList<String> objekti = studyntDAO.getCasoviStudent(student.getId()).stream().map(c -> "Cas: " + c.toString()).collect(Collectors.toCollection(ArrayList::new));
        objekti.addAll(studyntDAO.getIspitiStudent(student.getId()).stream().map(i -> "Ispit: " + i.toString()).collect(Collectors.toList()));
        objekti.addAll(studyntDAO.getZadaciStudent(student.getId()).stream().map(z -> "Zadatak: " + z.toString()).collect(Collectors.toList()));

        FilteredList<String> objects = new FilteredList<>(objekti.stream().collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)), data -> true);

        lvPretrage.setItems(objects);

        ArrayList<String> tipovi = new ArrayList<>();

        tipovi.add("Casovi");
        tipovi.add("Ispiti");
        tipovi.add("Zadaci");

        ObservableList<String> ciz = tipovi.stream().collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        cbTip.setItems(ciz);

        tfPretraga.textProperty().addListener(((observable, oldValue, newValue) -> {
                switch (cbTip.getValue())//Switch on choiceBox value
                {
                    case "Casovi":
                        objects.setPredicate(p -> p.toLowerCase().contains("cas: " + newValue.toLowerCase().trim()));
                        break;
                    case "Ispiti":
                        objects.setPredicate(p -> p.toLowerCase().contains("ispit: " + newValue.toLowerCase().trim()));
                        break;
                    case "Zadaci":
                        objects.setPredicate(p -> p.toLowerCase().contains("zadatak: " + newValue.toLowerCase().trim()));
                        break;
                }
                //String lowerCaseSearch = newValue.toLowerCase();
                //return Boolean.parseBoolean(String.valueOf(data.contains(lowerCaseSearch)));
        }));

        cbTip.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected
            if (newVal != null) {
                tfPretraga.setText("");
            }

            if (newVal != null) {
                switch (newVal)
                {
                    case "Casovi":
                        objects.setPredicate(p -> p.toLowerCase().contains("cas: " ));
                        break;
                    case "Ispiti":
                        objects.setPredicate(p -> p.toLowerCase().contains("ispit: "));
                        break;
                    case "Zadaci":
                        objects.setPredicate(p -> p.toLowerCase().contains("zadatak: "));
                        break;
                }
            }
        });
    }

    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spPretraga.getChildren().setAll(stackPane);
    }
}
