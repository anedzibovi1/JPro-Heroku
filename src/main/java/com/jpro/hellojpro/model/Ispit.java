package com.jpro.hellojpro.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ispit {

    private SimpleIntegerProperty id;
    private SimpleStringProperty naziv;
    private SimpleObjectProperty<LocalDate> datumOdrzavanja;
    private SimpleObjectProperty<LocalTime> vrijemePocetka, vrijemeKraja;
    private SimpleObjectProperty<Predmet> predmet;

    public Ispit(int id, String naziv, LocalDate datumOdrzavanja, LocalTime vrijemePocetka, LocalTime vrijemeKraja, Predmet predmet) {
        this.id = new SimpleIntegerProperty(id);
        this.naziv = new SimpleStringProperty(naziv);
        this.datumOdrzavanja = new SimpleObjectProperty<>(datumOdrzavanja);
        this.vrijemePocetka = new SimpleObjectProperty<>(vrijemePocetka);
        this.vrijemeKraja = new SimpleObjectProperty<>(vrijemeKraja);
        this.predmet = new SimpleObjectProperty<>(predmet);
    }

    public Ispit(String naziv, LocalDate datumOdrzavanja, LocalTime vrijemePocetka, LocalTime vrijemeKraja, Predmet predmet) {
        this.id = new SimpleIntegerProperty(0);
        this.naziv = new SimpleStringProperty(naziv);
        this.datumOdrzavanja = new SimpleObjectProperty<>(datumOdrzavanja);
        this.vrijemePocetka = new SimpleObjectProperty<>(vrijemePocetka);
        this.vrijemeKraja = new SimpleObjectProperty<>(vrijemeKraja);
        this.predmet = new SimpleObjectProperty<>(predmet);
    }

    public Ispit() {
        this.id = new SimpleIntegerProperty(0);
        this.naziv = new SimpleStringProperty("");
        this.datumOdrzavanja = new SimpleObjectProperty<>(LocalDate.of(2021,1,1));
        this.vrijemePocetka = new SimpleObjectProperty<>(LocalTime.of(1,00));
        this.vrijemeKraja = new SimpleObjectProperty<>(LocalTime.of(1,00));
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

    public String getNaziv() {
        return naziv.get();
    }

    public SimpleStringProperty nazivProperty() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv.set(naziv);
    }

    public LocalDate getDatumOdrzavanja() {
        return datumOdrzavanja.get();
    }

    public SimpleObjectProperty<LocalDate> datumOdrzavanjaProperty() {
        return datumOdrzavanja;
    }

    public void setDatumOdrzavanja(LocalDate datumOdrzavanja) {
        this.datumOdrzavanja.set(datumOdrzavanja);
    }

    public LocalTime getVrijemePocetka() {
        return vrijemePocetka.get();
    }

    public SimpleObjectProperty<LocalTime> vrijemePocetkaProperty() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(LocalTime vrijemePocetka) {
        this.vrijemePocetka.set(vrijemePocetka);
    }

    public LocalTime getVrijemeKraja() {
        return vrijemeKraja.get();
    }

    public SimpleObjectProperty<LocalTime> vrijemeKrajaProperty() {
        return vrijemeKraja;
    }

    public void setVrijemeKraja(LocalTime vrijemeKraja) {
        this.vrijemeKraja.set(vrijemeKraja);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
        String datum = datumOdrzavanja.getValue().format(formatter);
        return predmet.getValue().getNaziv() + ": " + naziv.getValue() + "\n"
                + vrijemePocetka.getValue().toString() + " " + datum;
    }
}
