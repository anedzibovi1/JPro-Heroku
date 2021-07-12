package com.jpro.hellojpro;

import com.jfoenix.controls.*;
import com.jpro.hellojpro.controller.IspitiFXMLController;
import com.jpro.hellojpro.controller.RasporedFXMLController;
import com.jpro.hellojpro.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@ExtendWith(ApplicationExtension.class)
public class RasporedFXMLControllerTest {

    private Stage theStage;
    private RasporedFXMLController ctrl;
    private StudyntDAO dao;
    private Student student;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        student = dao.getStudenti().get(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        ctrl = new RasporedFXMLController(student, dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Raspored");
        stage.setScene(new Scene(root, 1300, 800));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    void ucitava_li_se_meni(FxRobot robot) {
        Assertions.assertThat(robot.lookup("#btnGlavna").queryAs(Button.class)).isVisible();
        Assertions.assertThat(robot.lookup("#btnKalendar").queryAs(Button.class)).isVisible();
        Assertions.assertThat(robot.lookup("#btnIspiti").queryAs(Button.class)).isVisible();
        Assertions.assertThat(robot.lookup("#btnZadaci").queryAs(Button.class)).isVisible();
        Assertions.assertThat(robot.lookup("#btnRaspored").queryAs(Button.class)).isVisible();
        Assertions.assertThat(robot.lookup("#btnPretraga").queryAs(Button.class)).isVisible();
        Assertions.assertThat(robot.lookup("#btnPostavke").queryAs(Button.class)).isVisible();
    }

    @Test
    void otvara_li_se_kalendar(FxRobot robot) {
        robot.clickOn("#btnKalendar");
        Assertions.assertThat(robot.lookup("#spKalendar").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void otvara_li_se_zadaci(FxRobot robot) {
        robot.clickOn("#btnZadaci");
        Assertions.assertThat(robot.lookup("#spZadaci").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void otvara_li_se_ispiti(FxRobot robot) {
        robot.clickOn("#btnIspiti");
        Assertions.assertThat(robot.lookup("#spIspiti").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void otvara_li_se_raspored(FxRobot robot) {
        robot.clickOn("#btnRaspored");
        Assertions.assertThat(robot.lookup("#spRaspored").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void otvara_li_se_pretraga(FxRobot robot) {
        robot.clickOn("#btnPretraga");
        Assertions.assertThat(robot.lookup("#spPretraga").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void otvara_li_se_profil(FxRobot robot) {
        robot.clickOn("#btnPostavke");
        Assertions.assertThat(robot.lookup("#spProfilStudenta").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void otvara_li_se_glavna(FxRobot robot) {
        robot.clickOn("#btnGlavna");
        Assertions.assertThat(robot.lookup("#spGlavnaStr").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void filtrira_li_ispravno(FxRobot robot) throws InterruptedException {
        ObservableList<Cas> casovi = dao.getCasoviStudent(student.getId()).stream().filter(p -> p.getPredmet().getNaziv().equals("SI")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        JFXComboBox<Predmet> filter = robot.lookup("#cbFilter").queryAs(JFXComboBox.class);


        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(2);
            }
        });

        JFXListView lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        ArrayList<Cas> cass = new ArrayList<>(lvCasovi.getItems().sorted());

        String casovi_string1 = cass.toString();
        ArrayList<Cas> cass2 = new ArrayList<>(casovi.sorted());
        String casovi_string2 = cass2.toString();

        Assertions.assertThat(casovi_string1).isEqualTo(casovi_string2);

        //drugi pokusaj
        casovi = dao.getCasoviStudent(student.getId()).stream().filter(p -> p.getPredmet().getNaziv().equals("VI")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(3);
            }
        });

        lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        cass = new ArrayList<>(lvCasovi.getItems().sorted());

        casovi_string1 = cass.toString();
        cass2 = new ArrayList<>(casovi.sorted());
        casovi_string2 = cass2.toString();

        Assertions.assertThat(casovi_string1).isEqualTo(casovi_string2);

        //svi predmeti
        casovi = dao.getCasoviStudent(student.getId());
        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(0);
            }
        });

        lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        cass = new ArrayList<>(lvCasovi.getItems().sorted());

        casovi_string1 = cass.toString();
        cass2 = new ArrayList<>(casovi.sorted());
        casovi_string2 = cass2.toString();

        Assertions.assertThat(casovi_string1).isEqualTo(casovi_string2);
    }

    @Test
    void dodaje_li_cas(FxRobot robot) {
        ChoiceBox<Predmet> predmeti = robot.lookup("#cbPredmet").queryAs(ChoiceBox.class);
        ChoiceBox<String> tipovi = robot.lookup("#cbTipCasa").queryAs(ChoiceBox.class);
        DatePicker datumO = robot.lookup("#dpDatumO").queryAs(DatePicker.class);
        Spinner<Integer> sSatiP = robot.lookup("#sSatiP").queryAs(Spinner.class);
        Spinner<Integer> sSatiK = robot.lookup("#sSatiK").queryAs(Spinner.class);
        Spinner<Integer> sMinuteP = robot.lookup("#sMinuteP").queryAs(Spinner.class);
        Spinner<Integer> sMinuteK = robot.lookup("#sMinuteK").queryAs(Spinner.class);
        JFXRadioButton rbDa = robot.lookup("#rbDa").queryAs(JFXRadioButton.class);
        JFXRadioButton rbNe = robot.lookup("#rbNe").queryAs(JFXRadioButton.class);

        JFXListView<Cas> lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        JFXListView<Cas> finalLvCasovi = lvCasovi;
        robot.interact(new Runnable() {
            @Override
            public void run() {
                finalLvCasovi.getItems().remove(finalLvCasovi.getItems().size()-1);
                dao.obrisiCas(dao.getCasoviStudent(student.getId()).get(dao.getCasoviStudent(student.getId()).size()-1));
            }
        });

        robot.clickOn("#cbPredmet");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                predmeti.getSelectionModel().select(1);
            }
        });

        robot.clickOn("#dpDatumO");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                datumO.setValue(LocalDate.now());
            }
        });


        robot.interact(new Runnable() {
            @Override
            public void run() {
                sSatiP.getValueFactory().setValue(9);
                sMinuteP.getValueFactory().setValue(30);
                sSatiK.getValueFactory().setValue(11);
                sMinuteK.getValueFactory().setValue(0);
            }
        });

        robot.clickOn("#btnDodajCas");

        lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        Assertions.assertThat(lvCasovi.getItems().size()).isEqualTo(dao.getCasoviStudent(student.getId()).size());
    }

    @Test
    void otvara_li_update(FxRobot robot) {
        JFXListView<Cas> lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvCasovi.getSelectionModel().select(0);
            }
        });

        robot.clickOn("#btnIzmijeni");
        JFXButton izmijeni = robot.lookup("#btnIzmijeniCas").queryAs(JFXButton.class);
        Assertions.assertThat(izmijeni).isVisible();
    }

    @Test
    void mijenja_li_cas(FxRobot robot) {
        DatePicker datumO = robot.lookup("#dpDatumO").queryAs(DatePicker.class);
        Spinner<Integer> sSatiP = robot.lookup("#sSatiP").queryAs(Spinner.class);
        Spinner<Integer> sSatiK = robot.lookup("#sSatiK").queryAs(Spinner.class);
        Spinner<Integer> sMinuteP = robot.lookup("#sMinuteP").queryAs(Spinner.class);
        Spinner<Integer> sMinuteK = robot.lookup("#sMinuteK").queryAs(Spinner.class);

        JFXListView<Cas> lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvCasovi.getSelectionModel().select(0);
            }
        });


        robot.clickOn("#btnIzmijeni");

        robot.interact(new Runnable() {
            @Override
            public void run() {
                datumO.setValue(LocalDate.now());
                sSatiP.getValueFactory().setValue(15);
                sMinuteP.getValueFactory().setValue(20);
                sSatiK.getValueFactory().setValue(16);
                sMinuteK.getValueFactory().setValue(30);
            }
        });

        robot.clickOn("#btnIzmijeniCas");
        Assertions.assertThat(lvCasovi.getItems().get(0).getDatumOdrzavanja()).isEqualTo(dao.getCasoviStudent(student.getId()).get(0).getDatumOdrzavanja());
        Assertions.assertThat(lvCasovi.getItems().get(0).getVrijemePocetka()).isEqualTo(dao.getCasoviStudent(student.getId()).get(0).getVrijemePocetka());
        Assertions.assertThat(lvCasovi.getItems().get(0).getVrijemeKraja()).isEqualTo(dao.getCasoviStudent(student.getId()).get(0).getVrijemeKraja());

    }

    @Test
    void brise_li_unose(FxRobot robot) {
        JFXListView<Cas> lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvCasovi.getSelectionModel().select(0);
            }
        });

        robot.clickOn("#btnIzmijeni");
        JFXButton izmijeni = robot.lookup("#btnIzmijeniCas").queryAs(JFXButton.class);
        Assertions.assertThat(izmijeni).isVisible();

        robot.clickOn("#btnObrisiUnose");
        JFXButton dodaj = robot.lookup("#btnDodajCas").queryAs(JFXButton.class);
        Assertions.assertThat(dodaj).isVisible();
    }

    @Test
    void dodaje_li_predmet(FxRobot robot) {
        ChoiceBox<Predmet> predmeti = robot.lookup("#cbPredmet").queryAs(ChoiceBox.class);

        robot.clickOn("#tfNazivPredmeta");

        robot.write("Neki beze predmet");

        robot.clickOn("#btnDodajNoviPredmet");

        Assertions.assertThat(predmeti.getItems().size()).isEqualTo(dao.getPredmetiStudent(student.getId()).size());
        Assertions.assertThat(predmeti.getItems().get(predmeti.getItems().size()-1).getNaziv()).isEqualTo("Neki beze predmet");
    }

}
