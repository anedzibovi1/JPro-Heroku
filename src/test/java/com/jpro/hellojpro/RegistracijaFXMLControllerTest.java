package com.jpro.hellojpro;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.controller.RegistracijaFXMLController;
import com.jpro.hellojpro.controller.StudyntFXMLController;
import com.jpro.hellojpro.model.Student;
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
class RegistracijaFXMLControllerTest {

    Stage theStage;
    RegistracijaFXMLController ctrl;
    StudyntDAO dao;

    @Start
    private void start(Stage stage) throws IOException {
        StudyntDAO.removeInstance();
        File dbfile = new File("studynt.db");
        dao = StudyntDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Registracija.fxml"));
        ctrl = new RegistracijaFXMLController(new Student(), dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Registracija");
        stage.setScene(new Scene(root, 1500, 800));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    void prijavljuje_li_gresku_za_prazna_polja(FxRobot robot) {
        Text el = robot.lookup("#tPolja").queryAs(Text.class);
        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();
    }

    @Test
    void prijavljuje_li_gresku_za_email(FxRobot robot) {
        Text el = robot.lookup("#tEmail").queryAs(Text.class);

        robot.clickOn("#tfIme");
        robot.write("Novo");
        robot.clickOn("#tfPrezime");
        robot.write("Novic");
        robot.clickOn("#tfEmail");
        robot.write("nnovigmail.com");
        robot.clickOn("#pfPassword");
        robot.write("nnekic1");
        robot.clickOn("#pfPassword2");
        robot.write("nnekic1");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

        //drugi pokusaj
        robot.clickOn("#tfEmail");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovi@.com");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

        //treci pokusaj
        robot.clickOn("#tfEmail");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("@gmail.com");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

        //cetvrti pokusaj
        robot.clickOn("#tfEmail");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovi123@gmail.");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

        //peti pokusaj
        robot.clickOn("#tfEmail");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovi123@gmail");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

    }

    @Test
    void prijavljuje_li_gresku_za_lozinku(FxRobot robot) {
        Text el = robot.lookup("#tPassword").queryAs(Text.class);

        robot.clickOn("#tfIme");
        robot.write("Novo");
        robot.clickOn("#tfPrezime");
        robot.write("Novic");
        robot.clickOn("#tfEmail");
        robot.write("nnovic123@gmail.com");
        robot.clickOn("#pfPassword");
        robot.write("nnovic1");
        robot.clickOn("#pfPassword2");
        robot.write("");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

        //drugi pokusaj
        robot.clickOn("#pfPassword");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovic1");

        robot.clickOn("#pfPassword2");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovic13");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

        //treci pokusaj
        robot.clickOn("#pfPassword2");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovic1");

        robot.clickOn("#pfPassword");
        robot.press(KeyCode.COMMAND).press(KeyCode.A).release(KeyCode.A).release(KeyCode.COMMAND);
        robot.write("nnovic333");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(el).isVisible();

    }

    @Test
    void dodaje_li_novog_studenta(FxRobot robot) {
        JFXTextField email = robot.lookup("#tfEmail").queryAs(JFXTextField.class);
        JFXPasswordField pw = robot.lookup("#pfPassword").queryAs(JFXPasswordField.class);

        robot.clickOn("#tfIme");
        robot.write("Novo");
        robot.clickOn("#tfPrezime");
        robot.write("Novic");
        robot.clickOn("#tfEmail");
        robot.write("nnovic123@gmail.com");
        robot.clickOn("#pfPassword");
        robot.write("nnovic123");
        robot.clickOn("#pfPassword2");
        robot.write("nnovic123");

        robot.clickOn("#btnPrihvati");
        Assertions.assertThat(robot.lookup("#spGlavnaStr").queryAs(StackPane.class)).isVisible();
        Student student = dao.nadjiStudenta(email.getText(), pw.getText());
        Assertions.assertThat(student).isNotNull();

    }

}