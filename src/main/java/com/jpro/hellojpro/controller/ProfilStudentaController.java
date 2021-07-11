package com.jpro.hellojpro.controller;

import com.jfoenix.controls.*;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.listCells.ImageListCell;
import com.jpro.hellojpro.model.Student;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilStudentaController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spProfilStudenta;

    @FXML
    protected StackPane spProfil;

    @FXML
    protected JFXTextField tfIme;

    @FXML
    protected JFXTextField tfPrezime;

    @FXML
    protected JFXTextField tfEmail;

    @FXML
    protected JFXButton btnPostavke;

    @FXML
    protected GridPane gpKorisnik;

    @FXML
    protected ImageView ivSlikaProfila;

    @FXML
    protected JFXComboBox<Image> cbNovaIkona;

    @FXML
    protected Text tPolja;


    public ProfilStudentaController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfIme.setText(student.getIme());
        tfPrezime.setText(student.getPrezime());
        tfEmail.setText(student.getEmail());
        tfEmail.setDisable(true);
        tPolja.setVisible(false);

        URL url = getClass().getResource(student.getSlika());
        Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
        ImageView viewStudent = new ImageView(imgStudent);
        btnPostavke.setGraphic(viewStudent);
        ivSlikaProfila.setImage(imgStudent);

        final ObservableList<Image> images = fetchImages();
        populateComboBox(images);


        cbNovaIkona.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Image image = new Image(cbNovaIkona.getValue().getUrl());
                ivSlikaProfila.setImage(image);
            }
        });

    }

    private ObservableList<Image> fetchImages() {
        final ObservableList<Image> data = FXCollections.observableArrayList();
        // icon license: CC Attribution-Noncommercial-Share Alike 3.0
        // iconset homepage: http://vincentburton.deviantart.com/art/Iconos-Diaguitas-216196385
        //String regex = "^0{1,2}[0-9]{1,2}-?(boy|girl|student|woman|bussines man|female|working man|working woman|male|user)\\.(png)$";

        //Pattern pattern = Pattern.compile(regex);
        // Matcher matcher = pattern.matcher(tfEmail.getText());

        URL url = getClass().getResource("/com/jpro/hellojpro/img/001-boy.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/002-student.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/005-boy.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/007-woman.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/008-woman.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/009-working-man.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/010-female.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/013-man.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/014-student.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/017-working-man.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/021-working-woman.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/022-girl.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/023-student.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/024-girl.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/025-girl.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        url = getClass().getResource("/com/jpro/hellojpro/img/029-boy.png");
        data.add(new Image(String.valueOf(url),70,70,true,true));
        return data;
    }

    private void populateComboBox(ObservableList<Image> data) {
        cbNovaIkona.getItems().addAll(data);
        cbNovaIkona.setPromptText("Odaberite novu ikonu");
        cbNovaIkona.setButtonCell(new ImageListCell());
        cbNovaIkona.setCellFactory(listView -> new ImageListCell());
        cbNovaIkona.getSelectionModel().selectFirst();
    }



    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);


    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

    public void izmijeniStudentaAction(ActionEvent actionEvent) throws IOException {
        if(tfIme.getText().isEmpty()) {
            tfIme.getStyleClass().add("poljeNijeIspravno");
            tfIme.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfIme.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });
            tPolja.setVisible(true);

            FadeTransition ft = new FadeTransition(Duration.millis(300), tPolja);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        }
        if(tfPrezime.getText().isEmpty()) {
            tfPrezime.getStyleClass().add("poljeNijeIspravno");
            tfPrezime.textProperty().addListener((obs, oldIme, newIme) -> {
                if (!newIme.isEmpty()) {
                    tfPrezime.getStyleClass().removeAll("poljeNijeIspravno");
                }
            });
            tPolja.setVisible(true);

            FadeTransition ft = new FadeTransition(Duration.millis(300), tPolja);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
        }
        else if(!tfPrezime.getText().isEmpty() && !tfIme.getText().isEmpty()) {
            tPolja.setVisible(false);
            Student noviStudent = new Student();
            noviStudent.setId(student.getId());
            noviStudent.setEmail(tfEmail.getText());
            noviStudent.setPassword(student.getPassword());
            noviStudent.setPrezime(tfPrezime.getText());
            noviStudent.setIme(tfIme.getText());
            noviStudent.setSlika(cbNovaIkona.getValue().getUrl().substring(cbNovaIkona.getValue().getUrl().indexOf("/com")));
            studyntDAO.izmijeniStudenta(noviStudent);
            URL url = getClass().getResource(noviStudent.getSlika());
            Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
            ImageView viewStudent = new ImageView(imgStudent);
            btnPostavke.setGraphic(viewStudent);
        }
    }

    public void obrisiProfilAction(ActionEvent actionEvent) throws IOException {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Brisanje profila"));
        dialogLayout.setBody(new Text("Da li ste sigurni da želite obrisati profil?"));
        JFXButton da = new JFXButton("Da");
        JFXButton ne = new JFXButton("Ne");

        da.setId("btnDa");
        ne.setId("btnNe");

        da.setStyle("-fx-background-color: lightgreen");
        ne.setStyle("-fx-background-color: lightpink");

        dialogLayout.setActions(da,ne);

        JFXDialog dialog = new JFXDialog(spProfilStudenta, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
        dialog.setId("jfxDialog");
        da.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                studyntDAO.obrisiStudenta(student);
                StudyntFXMLController studyntFXMLController = new StudyntFXMLController();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/HelloJPro.fxml"));
                loader.setController(studyntFXMLController);
                StackPane stackPane = null;
                try {
                    stackPane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                spProfilStudenta.getChildren().setAll(stackPane);
            }
        });

        ne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        dialog.show();
    }

    public void odjaviSeAction(ActionEvent actionEvent) throws IOException {
        StudyntFXMLController studyntFXMLController = new StudyntFXMLController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/HelloJPro.fxml"));
        loader.setController(studyntFXMLController);
        StackPane stackPane = loader.load();
        spProfilStudenta.getChildren().setAll(stackPane);
    }

}
