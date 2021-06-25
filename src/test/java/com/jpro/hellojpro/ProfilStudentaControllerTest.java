package com.jpro.hellojpro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.controller.PretragaFXMLController;
import com.jpro.hellojpro.controller.ProfilStudentaController;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
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
class ProfilStudentaControllerTest {

    Stage theStage;
    ProfilStudentaController ctrl;
    StudyntDAO dao;
    Student student;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        dao.dodajStudenta(new Student("Nina", "Ninic", "nninic1@gmail.com", "nninic123"));
        student = dao.getStudenti().get(dao.getStudenti().size()-1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        ctrl = new ProfilStudentaController(student, dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Postavke profila");
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
    @Order(9)
    void brise_li_se_profil(FxRobot robot) {
        robot.clickOn("#btnObrisiProfil");
        Assertions.assertThat(robot.lookup("#rootPane").queryAs(StackPane.class)).isVisible();
    }

    @Test
    void mijenja_li_se_profil(FxRobot robot) {
        JFXTextField ime = robot.lookup("#tfIme").queryAs(JFXTextField.class);
        JFXTextField prezime = robot.lookup("#tfPrezime").queryAs(JFXTextField.class);

        robot.clickOn("#tfIme");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("Ninica");

        robot.clickOn("#tfPrezime");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("Ninicevic");

        robot.clickOn("#btnSpasi");

        Assertions.assertThat(dao.getStudenti().get(dao.getStudenti().size()-1).getIme()).isEqualTo("Ninica");
        Assertions.assertThat(dao.getStudenti().get(dao.getStudenti().size()-1).getPrezime()).isEqualTo("Ninicevic");
    }



}