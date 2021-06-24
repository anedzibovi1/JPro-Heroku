package com.jpro.hellojpro.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Zadatak {

    private SimpleIntegerProperty id;
    private SimpleObjectProperty<LocalDate> datumZavrsetka;
    private SimpleStringProperty naziv, opis;
    private SimpleObjectProperty<Predmet> predmet;

    public Zadatak(int id, LocalDate datumZavrsetka, String naziv, String opis, Predmet predmet) {
        this.id = new SimpleIntegerProperty(id);
        this.datumZavrsetka = new SimpleObjectProperty<>(datumZavrsetka);
        this.naziv = new SimpleStringProperty(naziv);
        this.opis = new SimpleStringProperty(opis);
        this.predmet = new SimpleObjectProperty<>(predmet);
    }

    public Zadatak(LocalDate datumZavrsetka, String naziv, String opis, Predmet predmet) {
        this.id = new SimpleIntegerProperty(0);
        this.datumZavrsetka = new SimpleObjectProperty<>(datumZavrsetka);
        this.naziv = new SimpleStringProperty(naziv);
        this.opis = new SimpleStringProperty(opis);
        this.predmet = new SimpleObjectProperty<>(predmet);
    }

    public Zadatak() {
        this.id = new SimpleIntegerProperty(0);
        this.datumZavrsetka = new SimpleObjectProperty<>(LocalDate.of(2021,1,1));
        this.naziv = new SimpleStringProperty("");
        this.opis = new SimpleStringProperty("");
        this.predmet = new SimpleObjectProperty<>(null);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getDatumZavrsetka() {
        return datumZavrsetka.get();
    }

    public SimpleObjectProperty<LocalDate> datumZavrsetkaProperty() {
        return datumZavrsetka;
    }

    public void setDatumZavrsetka(LocalDate datumZavrsetka) {
        this.datumZavrsetka.set(datumZavrsetka);
    }

    public String getNaziv() {
        return naziv.get();
    }

    public SimpleStringProperty nazivProperty() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public String getOpis() {
        return opis.get();
    }

    public SimpleStringProperty opisProperty() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis.set(opis);
    }

    public Predmet getPredmet() {
        return predmet.get();
    }

    public SimpleObjectProperty<Predmet> predmetProperty() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet.set(predmet);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        String datum = datumZavrsetka.getValue().format(formatter);
        return predmet.getValue().getNaziv() + ": " + naziv.getValue() + "\n" + datum;
    }

}
