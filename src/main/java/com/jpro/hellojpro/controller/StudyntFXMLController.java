package com.jpro.hellojpro.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.model.Student;
import com.jpro.webapi.JProApplication;
import com.jpro.webapi.WebAPI;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.mail.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudyntFXMLController implements Initializable {

    private StudyntDAO model;
    public Label platformLabel;
    @FXML
    protected StackPane rootPane;

    @FXML
    protected JProApplication jProApplication;

    @FXML
    protected Button btnRegistracija;

    @FXML
    protected JFXTextField tfEmail;

    @FXML
    protected JFXPasswordField pfPassword;

    @FXML
    protected Text tEmailLozinka;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = StudyntDAO.getInstance();
        tEmailLozinka.setVisible(false);
        //tfEmail.setText(model.getStudenti().get(1).getEmail());
        //pfPassword.setText(model.getStudenti().get(1).getPassword());
        platformLabel.setText(String.format("Platform: %s", WebAPI.isBrowser() ? "Browser" : "Desktop"));
    }

    public void init(JProApplication jProApplication) {
        this.jProApplication = jProApplication;
    }


    public void otvoriRegistraciju(ActionEvent actionEvent) throws IOException {
        RegistracijaFXMLController registracijaFXMLController = new RegistracijaFXMLController(new Student(), model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Registracija.fxml"));
        loader.setController(registracijaFXMLController);
        StackPane stackPane = loader.load();
        rootPane.getChildren().setAll(stackPane);
    }

    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        Student student = model.nadjiStudenta(tfEmail.getText(), pfPassword.getText());
        if (student != null) {
            tfEmail.getStyleClass().remove("poljeNijeIspravno");
            GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, model);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
            loader.setController(glavnaStranicaFXMLController);
            StackPane stackPane = loader.load();
            rootPane.getChildren().setAll(stackPane);
        } else {
            tfEmail.getStyleClass().add("poljeNijeIspravno");
            pfPassword.getStyleClass().add("poljeNijeIspravno");
            tEmailLozinka.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(300), tEmailLozinka);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            tfEmail.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfEmail.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

            pfPassword.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    pfPassword.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });
            //WebAPI.getWebAPI(rootPane.getScene()).openStageAsPopup(createTestStage());
        }
    }

}