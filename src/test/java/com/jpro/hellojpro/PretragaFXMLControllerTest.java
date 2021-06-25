package com.jpro.hellojpro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.controller.PretragaFXMLController;
import com.jpro.hellojpro.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;


@ExtendWith(ApplicationExtension.class)
class PretragaFXMLControllerTest {

    Stage theStage;
    PretragaFXMLController ctrl;
    StudyntDAO dao;
    Student student;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        student = dao.getStudenti().get(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        ctrl = new PretragaFXMLController(student, dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Pretraga");
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
    void filtrira_li_se_ispravno(FxRobot robot) {

        JFXComboBox<String> filter = robot.lookup("#cbTip").queryAs(JFXComboBox.class);

        robot.clickOn("#cbTip");
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(0);
            }
        });
        JFXListView<String> lv = robot.lookup("#lvPretrage").queryAs(JFXListView.class);
        ObservableList<Cas> casovi = dao.getCasoviStudent(student.getId());
        Assertions.assertThat(lv.getItems().size()).isEqualTo(casovi.size());

        //drugi pokusaj
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(1);
            }
        });
        lv = robot.lookup("#lvPretrage").queryAs(JFXListView.class);
        ObservableList<Ispit> ispiti = dao.getIspitiStudent(student.getId());
        Assertions.assertThat(lv.getItems().size()).isEqualTo(ispiti.size());

        //drugi pokusaj
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(2);
            }
        });
        lv = robot.lookup("#lvPretrage").queryAs(JFXListView.class);
        ObservableList<Zadatak> zadaci = dao.getZadaciStudent(student.getId());
        Assertions.assertThat(lv.getItems().size()).isEqualTo(zadaci.size());

        //pokusaj sa unosom
        robot.interact(new Runnable() {
            @Override
            public void run() {
                filter.getSelectionModel().select(2);
            }
        });

        robot.clickOn("#tfPretraga");
        robot.write("RPR");
        lv = robot.lookup("#lvPretrage").queryAs(JFXListView.class);
        zadaci = dao.getZadaciStudent(student.getId()).stream().filter(z -> z.getPredmet().getNaziv().equals("RPR")).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        Assertions.assertThat(lv.getItems().size()).isEqualTo(zadaci.size());


    }


}
