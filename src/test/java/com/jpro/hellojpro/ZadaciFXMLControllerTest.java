package com.jpro.hellojpro;

import com.jfoenix.controls.*;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.controller.KalendarFXMLController;
import com.jpro.hellojpro.controller.ZadaciFXMLController;
import com.jpro.hellojpro.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.stream.Collectors;


@ExtendWith(ApplicationExtension.class)
class ZadaciFXMLControllerTest {

    private Stage theStage;
    private ZadaciFXMLController ctrl;
    private StudyntDAO dao;
    private Student student;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        student = dao.getStudenti().get(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        ctrl = new ZadaciFXMLController(student, dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Zadaci");
        stage.setScene(new Scene(root, 1500, 800));
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
        ObservableList<Zadatak> zadaci = dao.getZadaciStudent(student.getId()).stream().filter(p -> p.getPredmet().getNaziv().equals("SI")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        JFXComboBox<Predmet> filter = robot.lookup("#cbFilter").queryAs(JFXComboBox.class);


        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(2);
            }
        });

        JFXListView lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);

        ArrayList<Zadatak> zadataks = new ArrayList<>(lvZadaci.getItems().sorted());

        String zadaci_string1 = zadataks.toString();
        ArrayList<Zadatak> zadataks1 = new ArrayList<>(zadaci.sorted());
        String zadaci_string2 = zadataks1.toString();

        Assertions.assertThat(zadaci_string1).isEqualTo(zadaci_string2);

        //drugi pokusaj
        zadaci = dao.getZadaciStudent(student.getId()).stream().filter(p -> p.getPredmet().getNaziv().equals("VI")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(3);
            }
        });

        lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);
        zadataks = new ArrayList<>(lvZadaci.getItems().sorted());
        zadaci_string1 = zadataks.toString();
        zadataks1 = new ArrayList<>(zadaci.sorted());
        zadaci_string2 = zadataks1.toString();
        Assertions.assertThat(zadaci_string1).isEqualTo(zadaci_string2);

        //svi predmeti
        zadaci = dao.getZadaciStudent(student.getId());
        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(0);
            }
        });

        lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);
        zadataks = new ArrayList<>(lvZadaci.getItems().sorted());
        zadaci_string1 = zadataks.toString();
        zadataks1 = new ArrayList<>(zadaci.sorted());
        zadaci_string2 = zadataks1.toString();
        Assertions.assertThat(zadaci_string1).isEqualTo(zadaci_string2);
    }

    @Test
    void dodaje_li_zadatak(FxRobot robot) {
        ChoiceBox<Predmet> predmeti = robot.lookup("#cbPredmet").queryAs(ChoiceBox.class);
        DatePicker datumZ = robot.lookup("#dpDatumZ").queryAs(DatePicker.class);
        JFXTextField naziv = robot.lookup("#tfNaziv").queryAs(JFXTextField.class);


        JFXListView<Zadatak> lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);

        JFXListView<Zadatak> finalLvZadaci = lvZadaci;
        robot.interact(new Runnable() {
            @Override
            public void run() {
                finalLvZadaci.getItems().remove(finalLvZadaci.getItems().size()-1);
                dao.obrisiZadatak(dao.getZadaciStudent(student.getId()).get(dao.getZadaciStudent(student.getId()).size()-1));
            }
        });



        robot.clickOn("#tfNaziv");
        robot.write("Novi zadatak");
        robot.clickOn("#taOpis");
        robot.write("Admir ne zna da programira");

        robot.clickOn("#cbPredmet");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                predmeti.getSelectionModel().select(2);
            }
        });

        robot.clickOn("#dpDatumZ");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                datumZ.setValue(LocalDate.now());
            }
        });

        robot.clickOn("#btnDodajZadatak");

        lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);

        Assertions.assertThat(lvZadaci.getItems().size()).isEqualTo(dao.getZadaciStudent(student.getId()).size());
    }

    @Test
    void otvara_li_update(FxRobot robot) {
        JFXListView lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvZadaci.getSelectionModel().select(0);
            }
        });

        robot.clickOn("#btnIzmijeni");
        JFXButton izmijeni = robot.lookup("#btnIzmijeniZadatak").queryAs(JFXButton.class);
        Assertions.assertThat(izmijeni).isVisible();
    }

    @Test
    void mijenja_li_zadatak(FxRobot robot) {
        JFXTextField naziv = robot.lookup("#tfNaziv").queryAs(JFXTextField.class);
        DatePicker datumZ = robot.lookup("#dpDatumZ").queryAs(DatePicker.class);

        JFXListView<Zadatak> lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvZadaci.getSelectionModel().select(0);
            }
        });


        robot.clickOn("#btnIzmijeni");
        robot.clickOn("#tfNaziv");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        if(naziv.getText().equals("drugi naziv"))
        robot.write("prvi naziv");
        else robot.write("drugi naziv");

        robot.clickOn("#dpDatumZ");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                datumZ.setValue(LocalDate.now());
            }
        });

        robot.clickOn("#btnIzmijeniZadatak");
        Assertions.assertThat(lvZadaci.getItems().get(0).getNaziv()).isEqualTo(dao.getZadaciStudent(student.getId()).get(0).getNaziv());
        Assertions.assertThat(lvZadaci.getItems().get(0).getDatumZavrsetka()).isEqualTo(dao.getZadaciStudent(student.getId()).get(0).getDatumZavrsetka());
    }

    @Test
    void brise_li_unose(FxRobot robot) {
        JFXListView lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvZadaci.getSelectionModel().select(0);
            }
        });

        robot.clickOn("#btnIzmijeni");
        JFXButton izmijeni = robot.lookup("#btnIzmijeniZadatak").queryAs(JFXButton.class);
        Assertions.assertThat(izmijeni).isVisible();

        robot.clickOn("#btnObrisiUnose");
        JFXButton dodaj = robot.lookup("#btnDodajZadatak").queryAs(JFXButton.class);
        Assertions.assertThat(dodaj).isVisible();
    }
}
