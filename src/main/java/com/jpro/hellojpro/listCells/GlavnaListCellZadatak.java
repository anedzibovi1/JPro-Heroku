package com.jpro.hellojpro.listCells;

import com.jfoenix.controls.JFXListCell;
import com.jpro.hellojpro.model.Zadatak;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class GlavnaListCellZadatak extends JFXListCell<Zadatak> {
    HBox hbox = new HBox();
    Label label = new Label("");
    Label label1 = new Label("");

    VBox vbox = new VBox();

    public GlavnaListCellZadatak() {
        super();

        label.setStyle("-fx-font-size: 22px;");
        label1.setStyle("-fx-font-size: 16px;");
        hbox.getChildren().addAll(vbox);
        vbox.getChildren().addAll(label,label1);
        hbox.setSpacing(5);
    }

    @Override
    protected void updateItem(Zadatak item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item != null && !empty) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
            String datum = item.getDatumZavrsetka().format(formatter);
            label.setText(item.getPredmet().getNaziv() + ": " + item.getNaziv());
            label1.setText(datum);
            setGraphic(hbox);

        }
    }
}
