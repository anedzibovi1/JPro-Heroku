package com.jpro.hellojpro.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UnosEmailaController implements Initializable {

    public boolean btnOkPressed;

    @FXML
    protected Text tEmail;

    @FXML
    protected Text tEmail1;

    @FXML
    protected JFXTextField tfEmail;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnOkPressed = false;
        tEmail.setVisible(false);
        tEmail1.setVisible(false);
    }

    public void posaljiAction(ActionEvent actionEvent) {
        btnOkPressed = true;
    }

    public Text gettEmail() {
        return tEmail;
    }

    public void settEmail(Text tEmail) {
        this.tEmail = tEmail;
    }

    public Text gettEmail1() {
        return tEmail1;
    }

    public void settEmail1(Text tEmail1) {
        this.tEmail1 = tEmail1;
    }

    public JFXTextField getTfEmail() {
        return tfEmail;
    }

    public void setTfEmail(JFXTextField tfEmail) {
        this.tfEmail = tfEmail;
    }
}
