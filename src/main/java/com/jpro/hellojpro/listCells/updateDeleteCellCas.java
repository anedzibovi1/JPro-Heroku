package com.jpro.hellojpro.listCells;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.model.Cas;
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

public class updateDeleteCellCas extends JFXListCell<Cas> {
    HBox hbox = new HBox();
    Label label = new Label("");
    Pane pane = new Pane();
    Button button = new Button();
    Button button1 = new Button();

    Label label1 = new Label("");

    VBox vbox = new VBox();



    public updateDeleteCellCas(StudyntDAO studyntDAO, StackPane spCas, JFXListView<Cas> lvCasovi) {
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

        button.setCursor(Cursor.HAND);
        button1.setCursor(Cursor.HAND);

        HBox.setHgrow(pane, Priority.ALWAYS);

        button.setOnAction(event -> {
            studyntDAO.obrisiCas(getItem());
            getListView().getItems().remove(getItem());
        });

        button1.setOnAction(event -> {
            Node d = spCas.lookup("#dpDatumO");
            Node sp = spCas.lookup("#sSatiP");
            Node mp = spCas.lookup("#sMinuteP");
            Node sk = spCas.lookup("#sSatiK");
            Node mk = spCas.lookup("#sMinuteK");
            Node p = spCas.lookup("#cbPredmet");
            Node da = spCas.lookup("#rbDa");
            Node ne = spCas.lookup("#rbNe");
            Node tc = spCas.lookup("#cbTipCasa");
            Node buttonD = spCas.lookup("#btnDodajCas");
            Node buttonU = spCas.lookup("#btnIzmijeniCas");

            Cas cas = lvCasovi.getSelectionModel().getSelectedItem();
            if(d != null && sp != null && sk != null && mp != null && mk != null && p != null) {
                ((DatePicker) d).setValue(cas.getDatumOdrzavanja());
                ((Spinner<Integer>) sp).getValueFactory().setValue(cas.getVrijemePocetka().getHour());
                ((Spinner<Integer>) mp).getValueFactory().setValue(cas.getVrijemePocetka().getMinute());
                ((Spinner<Integer>) sk).getValueFactory().setValue(cas.getVrijemeKraja().getHour());
                ((Spinner<Integer>) mk).getValueFactory().setValue(cas.getVrijemeKraja().getMinute());
                ((ChoiceBox<Predmet>) p).getSelectionModel().select(cas.getPredmet());
                if(cas.getPonavljanje().equals("Da")) ((RadioButton) da).setSelected(true);
                else ((RadioButton) ne).setSelected(true);
                ((ChoiceBox<String>) tc).getSelectionModel().select(cas.getTipCasa());
                buttonD.setVisible(false);
                buttonU.setVisible(true);
            }


        });
    }

    @Override
    protected void updateItem(Cas item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null && !empty) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
            String datum =  item.getDatumOdrzavanja().format(formatter);
            label.setText(item.getPredmet().getNaziv() + ": " + item.getTipCasa());
            label1.setText(item.getVrijemePocetka().toString()
                    + "-" + item.getVrijemeKraja().toString() + " " + datum);
            setGraphic(hbox);

        }
    }
}
