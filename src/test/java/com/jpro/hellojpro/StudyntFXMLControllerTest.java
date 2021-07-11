package com.jpro.hellojpro;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.controller.StudyntFXMLController;
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
import java.util.Stack;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class StudyntFXMLControllerTest {

    Stage theStage;
    StudyntFXMLController ctrl;
    StudyntDAO dao;
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        dao = StudyntDAO.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/HelloJPro.fxml"));
        ctrl = new StudyntFXMLController();
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, 500, 800));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;

    }

    @Test
    void ucitavaju_se_buttoni(FxRobot robot) {
        Assertions.assertThat(robot.lookup("#btnGlavna").queryAs(JFXButton.class)).hasText("Prijavite se");
        Assertions.assertThat(robot.lookup("#btnRegistracija").queryAs(Button.class)).hasText("Nemate nalog? Registrujte se ovdje");
    }

    @Test
    void prijavljuje_li_gresku_za_email(FxRobot robot) {
        JFXTextField email = robot.lookup("#tfEmail").queryAs(JFXTextField.class);
        Text el = robot.lookup("#tEmailLozinka").queryAs(Text.class);

        robot.clickOn("#tfEmail");
        robot.write("anedzibovi1@etfs");

        robot.clickOn("#pfPassword");
        robot.write("amilan0712");

        robot.clickOn("#btnGlavna");

        Background bg = email.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains(Color.LIGHTPINK.toString()))
                colorFound = true;

        Assertions.assertThat(colorFound).isTrue();
        Assertions.assertThat(el).isVisible();
    }

    @Test
    void prijavljuje_li_gresku_za_lozinku(FxRobot robot) {
        JFXPasswordField pw = robot.lookup("#pfPassword").queryAs(JFXPasswordField.class);
        Text el = robot.lookup("#tEmailLozinka").queryAs(Text.class);

        robot.clickOn("#tfEmail");
        robot.write("anedzibovi1@etf.unsa.ba");
        robot.clickOn("#pfPassword");
        robot.write("amila");

        robot.clickOn("#btnGlavna");

        Background bg = pw.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains(Color.LIGHTPINK.toString()))
                colorFound = true;

        Assertions.assertThat(colorFound).isTrue();
        Assertions.assertThat(el).isVisible();
    }

    @Test
    void otvara_li_registraciju(FxRobot robot) {

        robot.clickOn("#btnRegistracija");
        Assertions.assertThat(robot.lookup("#btnPrihvati").queryAs(JFXButton.class)).hasText("Prihvati");
    }

    @Test
    void otvara_li_glavnu(FxRobot robot) {

        robot.clickOn("#tfEmail");
        robot.write("anedzibovi1@etf.unsa.ba");
        robot.clickOn("#pfPassword");
        robot.write("amilan0712");

        robot.clickOn("#btnGlavna");

        Assertions.assertThat(robot.lookup("#spGlavnaStr").queryAs(StackPane.class)).isVisible();
    }

}

