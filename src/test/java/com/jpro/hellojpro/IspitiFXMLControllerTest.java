package com.jpro.hellojpro;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.controller.IspitiFXMLController;
import com.jpro.hellojpro.model.Ispit;
import com.jpro.hellojpro.model.Predmet;
import com.jpro.hellojpro.model.Student;
import com.jpro.hellojpro.model.Zadatak;
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
public class IspitiFXMLControllerTest {

    private Stage theStage;
    private IspitiFXMLController ctrl;
    private StudyntDAO dao;
    private Student student;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        student = dao.getStudenti().get(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        ctrl = new IspitiFXMLController(student, dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Ispiti");
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
        ObservableList<Ispit> ispiti = dao.getIspitiStudent(student.getId()).stream().filter(p -> p.getPredmet().getNaziv().equals("SI")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        JFXComboBox<Predmet> filter = robot.lookup("#cbFilter").queryAs(JFXComboBox.class);


        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(2);
            }
        });

        JFXListView lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);

        ArrayList<Ispit> ispits = new ArrayList<>(lvIspiti.getItems().sorted());

        String ispiti_string1 = ispits.toString();
        ArrayList<Ispit> ispits2 = new ArrayList<>(ispiti.sorted());
        String ispiti_string2 = ispits2.toString();

        Assertions.assertThat(ispiti_string1).isEqualTo(ispiti_string2);

        //drugi pokusaj
        ispiti = dao.getIspitiStudent(student.getId()).stream().filter(p -> p.getPredmet().getNaziv().equals("VI")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(3);
            }
        });

        lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);
        ispits = new ArrayList<>(lvIspiti.getItems().sorted());
        ispiti_string1 = ispits.toString();
        ispits2 = new ArrayList<>(ispiti.sorted());
        ispiti_string2 = ispits2.toString();
        Assertions.assertThat(ispiti_string1).isEqualTo(ispiti_string2);

        //svi predmeti
        ispiti = dao.getIspitiStudent(student.getId());
        robot.clickOn("#cbFilter");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(0);
            }
        });

        lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);
        ispits = new ArrayList<>(lvIspiti.getItems().sorted());
        ispiti_string1 = ispits.toString();
        ispits2 = new ArrayList<>(ispiti.sorted());
        ispiti_string2 = ispits2.toString();
        Assertions.assertThat(ispiti_string1).isEqualTo(ispiti_string2);
    }

    @Test
    void dodaje_li_ispit(FxRobot robot) {
        ChoiceBox<Predmet> predmeti = robot.lookup("#cbPredmet").queryAs(ChoiceBox.class);
        DatePicker datumZ = robot.lookup("#dpDatumO").queryAs(DatePicker.class);
        Spinner<Integer> sSatiP = robot.lookup("#sSatiP").queryAs(Spinner.class);
        Spinner<Integer> sSatiK = robot.lookup("#sSatiK").queryAs(Spinner.class);
        Spinner<Integer> sMinuteP = robot.lookup("#sMinuteP").queryAs(Spinner.class);
        Spinner<Integer> sMinuteK = robot.lookup("#sMinuteK").queryAs(Spinner.class);


        JFXListView<Ispit> lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);

        JFXListView<Ispit> finalLvIspiti = lvIspiti;
        robot.interact(new Runnable() {
            @Override
            public void run() {
                finalLvIspiti.getItems().remove(finalLvIspiti.getItems().size()-1);
                dao.obrisiIspit(dao.getIspitiStudent(student.getId()).get(dao.getIspitiStudent(student.getId()).size()-1));
            }
        });


        robot.clickOn("#tfNaziv");
        robot.write("Novi ispit");

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
                datumZ.setValue(LocalDate.now());
            }
        });


        robot.interact(new Runnable() {
            @Override
            public void run() {
                sSatiP.getValueFactory().setValue(10);
                sMinuteP.getValueFactory().setValue(30);
                sSatiK.getValueFactory().setValue(12);
                sMinuteK.getValueFactory().setValue(0);
            }
        });

        robot.clickOn("#btnDodajIspit");

        lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);

        Assertions.assertThat(lvIspiti.getItems().size()).isEqualTo(dao.getIspitiStudent(student.getId()).size());
    }

    @Test
    void otvara_li_update(FxRobot robot) {
        JFXListView<Ispit> lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvIspiti.getSelectionModel().select(0);
            }
        });

        robot.clickOn("#btnIzmijeni");
        JFXButton izmijeni = robot.lookup("#btnIzmijeniIspit").queryAs(JFXButton.class);
        Assertions.assertThat(izmijeni).isVisible();
    }

    @Test
    void mijenja_li_ispit(FxRobot robot) {
        JFXTextField naziv = robot.lookup("#tfNaziv").queryAs(JFXTextField.class);
        DatePicker datumO = robot.lookup("#dpDatumO").queryAs(DatePicker.class);
        Spinner<Integer> sSatiP = robot.lookup("#sSatiP").queryAs(Spinner.class);
        Spinner<Integer> sSatiK = robot.lookup("#sSatiK").queryAs(Spinner.class);
        Spinner<Integer> sMinuteP = robot.lookup("#sMinuteP").queryAs(Spinner.class);
        Spinner<Integer> sMinuteK = robot.lookup("#sMinuteK").queryAs(Spinner.class);

        JFXListView<Ispit> lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvIspiti.getSelectionModel().select(0);
            }
        });


        robot.clickOn("#btnIzmijeni");
        robot.clickOn("#tfNaziv");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        if(naziv.getText().equals("drugi naziv"))
            robot.write("prvi naziv");
        else robot.write("drugi naziv");

        robot.interact(new Runnable() {
            @Override
            public void run() {
                datumO.setValue(LocalDate.now());
                sSatiP.getValueFactory().setValue(9);
                sMinuteP.getValueFactory().setValue(30);
                sSatiK.getValueFactory().setValue(11);
                sMinuteK.getValueFactory().setValue(15);
            }
        });

        robot.clickOn("#btnIzmijeniIspit");
        Assertions.assertThat(lvIspiti.getItems().get(0).getNaziv()).isEqualTo(dao.getIspitiStudent(student.getId()).get(0).getNaziv());
        Assertions.assertThat(lvIspiti.getItems().get(0).getDatumOdrzavanja()).isEqualTo(dao.getIspitiStudent(student.getId()).get(0).getDatumOdrzavanja());
        Assertions.assertThat(lvIspiti.getItems().get(0).getVrijemePocetka()).isEqualTo(dao.getIspitiStudent(student.getId()).get(0).getVrijemePocetka());
        Assertions.assertThat(lvIspiti.getItems().get(0).getVrijemeKraja()).isEqualTo(dao.getIspitiStudent(student.getId()).get(0).getVrijemeKraja());

    }

    @Test
    void brise_li_unose(FxRobot robot) {
        JFXListView<Ispit> lvIspiti = robot.lookup("#lvIspiti").queryAs(JFXListView.class);

        robot.interact(new Runnable() {
            @Override
            public void run() {
                lvIspiti.getSelectionModel().select(0);
            }
        });

        robot.clickOn("#btnIzmijeni");
        JFXButton izmijeni = robot.lookup("#btnIzmijeniIspit").queryAs(JFXButton.class);
        Assertions.assertThat(izmijeni).isVisible();

        robot.clickOn("#btnObrisiUnose");
        JFXButton dodaj = robot.lookup("#btnDodajIspit").queryAs(JFXButton.class);
        Assertions.assertThat(dodaj).isVisible();
    }
}
