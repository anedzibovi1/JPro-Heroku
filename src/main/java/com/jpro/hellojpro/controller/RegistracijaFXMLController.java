package com.jpro.hellojpro.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.model.Student;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistracijaFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spReg;

    @FXML
    protected JFXTextField tfIme;

    @FXML
    protected JFXTextField tfPrezime;

    @FXML
    protected JFXTextField tfEmail;

    @FXML
    protected JFXPasswordField pfPassword;

    @FXML
    protected JFXPasswordField pfPassword2;

    @FXML
    protected Text tEmail;

    @FXML
    protected Text tPassword;

    @FXML
    protected Text tPolja;


    public RegistracijaFXMLController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pfPassword2.getStyleClass().removeAll("poljeNijeIspravno");
        pfPassword.getStyleClass().removeAll("poljeNijeIspravno");
        tfEmail.getStyleClass().removeAll("poljeNijeIspravno");
        tfIme.getStyleClass().removeAll("poljeNijeIspravno");
        tfPrezime.getStyleClass().removeAll("poljeNijeIspravno");

        tEmail.setVisible(false);
        tPassword.setVisible(false);
        tPolja.setVisible(false);
    }

    public void otvoriGlavuStranicu(ActionEvent actionEvent) throws IOException {

        tEmail.setVisible(false);
        tPassword.setVisible(false);
        tPolja.setVisible(false);

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tfEmail.getText());

        if(pfPassword.getText().equals(pfPassword2.getText()) && !pfPassword2.getText().isEmpty() && matcher.matches()) {
            student = new Student(tfIme.getText(), tfPrezime.getText(), tfEmail.getText(), pfPassword.getText());
            studyntDAO.dodajStudenta(student);
            GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
            loader.setController(glavnaStranicaFXMLController);
            StackPane stackPane = loader.load();
            spReg.getChildren().setAll(stackPane);
        }

        else if(tfIme.getText().isEmpty() || tfPrezime.getText().isEmpty() || pfPassword.getText().isEmpty() || tfEmail.getText().isEmpty()) {
            if(tfIme.getText().isEmpty()) tfIme.getStyleClass().add("poljeNijeIspravno");
            if(tfPrezime.getText().isEmpty()) tfPrezime.getStyleClass().add("poljeNijeIspravno");
            if(tfEmail.getText().isEmpty()) tfEmail.getStyleClass().add("poljeNijeIspravno");
            if(pfPassword.getText().isEmpty())  { pfPassword.getStyleClass().add("poljeNijeIspravno"); pfPassword2.getStyleClass().add("poljeNijeIspravno"); }
            tPolja.setVisible(true);

            FadeTransition ft = new FadeTransition(Duration.millis(300), tPolja);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            tfIme.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfIme.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });


            tfPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

            pfPassword.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    pfPassword.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

            pfPassword2.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    pfPassword2.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

            tfEmail.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfEmail.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

        }

        else if(!pfPassword.getText().equals(pfPassword2.getText())) {
            pfPassword.getStyleClass().add("poljeNijeIspravno");
            pfPassword2.getStyleClass().add("poljeNijeIspravno");

            tPassword.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(300), tPassword);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            pfPassword.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    pfPassword.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

            pfPassword2.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    pfPassword2.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });

        }


        else if(!matcher.matches()) {
            tfEmail.getStyleClass().add("poljeNijeIspravno");

            tEmail.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(300), tEmail);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

            tfEmail.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfEmail.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });
        }

    }

}
