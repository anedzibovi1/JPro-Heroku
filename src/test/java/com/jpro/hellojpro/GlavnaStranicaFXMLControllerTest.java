package com.jpro.hellojpro;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.controller.GlavnaStranicaFXMLController;
import com.jpro.hellojpro.model.Cas;
import com.jpro.hellojpro.model.Ispit;
import com.jpro.hellojpro.model.Student;
import com.jpro.hellojpro.model.Zadatak;
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
class GlavnaStranicaFXMLControllerTest {

    Stage theStage;
    GlavnaStranicaFXMLController ctrl;
    StudyntDAO dao;
    Student student;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        student = dao.getStudenti().get(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        ctrl = new GlavnaStranicaFXMLController(student, dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Glavna");
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
    void ucitava_li_se_lista_danasnjih_casova(FxRobot robot) {
        ObservableList<Cas> casovi = dao.getCasoviDanas(student);
        JFXListView lvCasovi = robot.lookup("#lvCasovi").queryAs(JFXListView.class);
        Assertions.assertThat(lvCasovi.getItems()).isEqualTo(casovi);

    }

    @Test
    void ucitava_li_se_lista_danasnjih_ispita(FxRobot robot) {
        ObservableList<Ispit> ispiti = dao.getIspitiDanas(student);
        JFXListView lvIspiti = robot.lookup("#lvIspiti1").queryAs(JFXListView.class);
        Assertions.assertThat(lvIspiti.getItems()).isEqualTo(ispiti);
    }

    @Test
    void ucitava_li_se_lista_zadataka(FxRobot robot) {
        ObservableList<Zadatak> zadaci = dao.getZadaciStudent(student.getId());
        JFXListView lvZadaci = robot.lookup("#lvZadaci").queryAs(JFXListView.class);
        ArrayList<Zadatak> zadataks = new ArrayList<>(lvZadaci.getItems().sorted());

        String zadaci_string1 = zadataks.toString();
        ArrayList<Zadatak> zadataks1 = new ArrayList<>(zadaci.sorted());
        String zadaci_string2 = zadataks1.toString();

        Assertions.assertThat(zadaci_string1).isEqualTo(zadaci_string2);
    }

    @Test
    void ucitava_li_se_lista_ispita(FxRobot robot) {
        ObservableList<Ispit> ispiti = dao.getIspitiStudent(student.getId());
        JFXListView lvIspiti = robot.lookup("#lvIspiti2").queryAs(JFXListView.class);
        ArrayList<Ispit> ispits = new ArrayList<>(lvIspiti.getItems().sorted());
        String ispiti_string = ispits.toString();
        ArrayList<Ispit> ispits2 = new ArrayList<>(ispiti.sorted());
        String ispiti_string2 = ispits2.toString();
        Assertions.assertThat(ispiti_string).isEqualTo(ispiti_string2);

    }

}
