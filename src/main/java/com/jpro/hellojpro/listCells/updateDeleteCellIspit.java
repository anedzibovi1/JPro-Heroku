package com.jpro.hellojpro.listCells;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.model.Ispit;
import com.jpro.hellojpro.model.Predmet;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.format.DateTimeFormatter;

public class updateDeleteCellIspit extends JFXListCell<Ispit> {
    HBox hbox = new HBox();
    Label label = new Label("");
    Pane pane = new Pane();
    Button button = new Button();
    Button button1 = new Button();
    Label label1 = new Label("");

    VBox vbox = new VBox();


    public updateDeleteCellIspit(StudyntDAO studyntDAO, StackPane spIspit, JFXListView<Ispit> lvIspiti) {
        super();
        URL url = getClass().getResource("/com/jpro/hellojpro/img/edit-solid.png");
        Image imgEdit = new Image(String.valueOf(url), 20,20,true,true);
        ImageView viewEdit = new ImageView(imgEdit);
        url = getClass().getResource("/com/jpro/hellojpro/img/trash-alt-solid.png");
        Image imgDelete = new Image(String.valueOf(url),20,20,true,true);
        ImageView viewDelete = new ImageView(imgDelete);

        label.setStyle("-fx-font-size: 20px;");
        label1.setStyle("-fx-font-size: 14px;");

        button.setGraphic(viewDelete);
        button1.setGraphic(viewEdit);
        button.setPadding(new Insets(5,5,5,5));
        button1.setPadding(new Insets(5,5,7,5));

        button.setStyle("-fx-background-color: white");
        button1.setStyle("-fx-background-color: white");

        hbox.getChildren().addAll(vbox, pane, button1, button);
        hbox.setSpacing(5);
        vbox.getChildren().addAll(label,label1);

        HBox.setHgrow(pane, Priority.ALWAYS);

        button.setCursor(Cursor.HAND);
        button1.setCursor(Cursor.HAND);

        button.setId("btnObrisi");
        button1.setId("btnIzmijeni");

        button.setOnAction(event -> {
            studyntDAO.obrisiIspit(getItem());
            getListView().getItems().remove(getItem());
        });

        button1.setOnAction(event -> {
            Node n = spIspit.lookup("#tfNaziv");
            Node d = spIspit.lookup("#dpDatumO");
            Node sp = spIspit.lookup("#sSatiP");
            Node mp = spIspit.lookup("#sMinuteP");
            Node sk = spIspit.lookup("#sSatiK");
            Node mk = spIspit.lookup("#sMinuteK");
            Node p = spIspit.lookup("#cbPredmet");
            Node buttonD = spIspit.lookup("#btnDodajIspit");
            Node buttonU = spIspit.lookup("#btnIzmijeniIspit");

            Ispit ispit = lvIspiti.getSelectionModel().getSelectedItem();
            if(n != null && d != null && sp != null && sk != null && mp != null && mk != null && p != null) {
                ((JFXTextField) n).setText(ispit.getNaziv());
                ((DatePicker) d).setValue(ispit.getDatumOdrzavanja());
                ((Spinner<Integer>) sp).getValueFactory().setValue(ispit.getVrijemePocetka().getHour());
                ((Spinner<Integer>) mp).getValueFactory().setValue(ispit.getVrijemePocetka().getMinute());
                ((Spinner<Integer>) sk).getValueFactory().setValue(ispit.getVrijemeKraja().getHour());
                ((Spinner<Integer>) mk).getValueFactory().setValue(ispit.getVrijemeKraja().getMinute());
                ((ChoiceBox<Predmet>) p).getSelectionModel().select(ispit.getPredmet());
                buttonD.setVisible(false);
                buttonU.setVisible(true);
            }


        });
    }

    @Override
    protected void updateItem(Ispit item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null && !empty) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
            String datum = item.getDatumOdrzavanja().format(formatter);
            label.setText(item.getPredmet().getNaziv() + ": " + item.getNaziv());
            label1.setText(item.getVrijemePocetka().toString() + " " + datum);
            setGraphic(hbox);

        }
    }
}
