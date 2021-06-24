package com.jpro.hellojpro.controller;

import com.jfoenix.controls.*;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.listCells.updateDeleteCellZadatak;
import com.jpro.hellojpro.model.Predmet;
import com.jpro.hellojpro.model.Student;
import com.jpro.hellojpro.model.Zadatak;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ZadaciFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spZadaci;

    @FXML
    protected StackPane spZadatak;

    @FXML
    protected ChoiceBox<Predmet> cbPredmet;

    @FXML
    protected JFXListView<Zadatak> lvZadaci;

    @FXML
    protected JFXTextField tfNaziv;

    @FXML
    protected DatePicker dpDatumZ;

    @FXML
    protected JFXTextArea taOpis;

    @FXML
    protected JFXButton btnObrisiUnose;

    @FXML
    protected JFXButton btnDodajZadatak;

    @FXML
    protected JFXButton btnIzmijeniZadatak;

    @FXML
    protected JFXButton btnPostavke;

    @FXML
    protected JFXComboBox<Predmet> cbFilter;

    public ZadaciFXMLController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        URL url = getClass().getResource(student.getSlika());
        Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
        ImageView viewStudent = new ImageView(imgStudent);
        btnPostavke.setGraphic(viewStudent);

        spZadatak.setVisible(false);
        spZadatak.setVisible(true);

        ScaleTransition ft1 = new ScaleTransition(Duration.millis(900), spZadatak);
        ft1.setFromX(0);
        ft1.setFromY(0);
        ft1.setToX(1);
        ft1.setToY(1);
        ft1.play();

        cbPredmet.setItems(studyntDAO.getPredmetiStudent(student.getId()));
        cbPredmet.getSelectionModel().selectFirst();
        lvZadaci.setItems(studyntDAO.getZadaciStudent(student.getId()));

        lvZadaci.setCellFactory(param -> new updateDeleteCellZadatak(studyntDAO, spZadatak, lvZadaci));

        cbFilter.getItems().add(new Predmet("Svi predmeti"));
        cbFilter.getItems().addAll(studyntDAO.getPredmetiStudent(student.getId()));
        cbFilter.setPromptText("Filtriraj po predmetu");
        cbFilter.setStyle("-fx-font-size: 20px;");

        cbFilter.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected

            if (newVal != null && !cbFilter.getSelectionModel().isSelected(0)) {
                lvZadaci.setItems(studyntDAO.getZadaciStudent(student.getId()).stream().filter(p -> p.getPredmet().equals(newVal)).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList)));
                lvZadaci.refresh();
            }
            else if(newVal != null && cbFilter.getSelectionModel().isSelected(0)) {
                lvZadaci.setItems(studyntDAO.getZadaciStudent(student.getId()));
                lvZadaci.refresh();
            }
        });
    }

    public void dodajZadatakAction(ActionEvent actionEvent) {
        String noviDatumString = dpDatumZ.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate noviDatum = LocalDate.parse(noviDatumString);
        Zadatak noviZadatak = new Zadatak(noviDatum, tfNaziv.getText(), taOpis.getText(), cbPredmet.getValue());
        studyntDAO.dodajZadatak(noviZadatak);
        lvZadaci.getItems().add(noviZadatak);
        lvZadaci.refresh();
    }

    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spZadaci.getChildren().setAll(stackPane);
    }

    public void izmijeniZadatakAction(ActionEvent actionEvent) {
        Zadatak zadatak = lvZadaci.getSelectionModel().getSelectedItem();
        btnObrisiUnose.setVisible(true);

        Zadatak noviZadatak = new Zadatak();
        noviZadatak.setId(zadatak.getId());
        noviZadatak.setNaziv(tfNaziv.getText());
        noviZadatak.setDatumZavrsetka(dpDatumZ.getValue());
        noviZadatak.setOpis(taOpis.getText());
        noviZadatak.setPredmet(cbPredmet.getSelectionModel().getSelectedItem());
        studyntDAO.izmijeniZadatak(noviZadatak);
        lvZadaci.refresh();
    }

    public void obrisiUnoseAction(ActionEvent actionEvent) {
        btnDodajZadatak.setVisible(true);
        btnIzmijeniZadatak.setVisible(false);

        tfNaziv.setText("");
        cbPredmet.getSelectionModel().selectFirst();
        dpDatumZ.getEditor().clear();
        taOpis.setText("");
    }

}
