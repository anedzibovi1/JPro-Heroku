package com.jpro.hellojpro.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Cas {
    private SimpleIntegerProperty id;
    private SimpleStringProperty tipCasa;
    private SimpleObjectProperty<LocalDate> datumOdrzavanja;
    private SimpleObjectProperty<LocalTime> vrijemePocetka, vrijemeKraja;
    private SimpleObjectProperty<Predmet> predmet;
    private SimpleStringProperty ponavljanje;

    public Cas(int id, LocalDate datumOdrzavanja, LocalTime vrijemePocetka, LocalTime vrijemeKraja, String tipCasa, Predmet predmet, String ponavljanje) {
        this.id = new SimpleIntegerProperty(id);
        this.datumOdrzavanja = new SimpleObjectProperty<>(datumOdrzavanja);
        this.vrijemePocetka = new SimpleObjectProperty<>(vrijemePocetka);
        this.vrijemeKraja = new SimpleObjectProperty<>(vrijemeKraja);
        this.tipCasa = new SimpleStringProperty(tipCasa);
        this.predmet = new SimpleObjectProperty<>(predmet);
        this.ponavljanje = new SimpleStringProperty(ponavljanje);
    }

    public Cas(LocalDate datumOdrzavanja, LocalTime vrijemePocetka, LocalTime vrijemeKraja, String tipCasa, Predmet predmet, String ponavljanje) {
        this.id = new SimpleIntegerProperty(0);
        this.datumOdrzavanja = new SimpleObjectProperty<>(datumOdrzavanja);
        this.vrijemePocetka = new SimpleObjectProperty<>(vrijemePocetka);
        this.vrijemeKraja = new SimpleObjectProperty<>(vrijemeKraja);
        this.tipCasa = new SimpleStringProperty(tipCasa);
        this.predmet = new SimpleObjectProperty<>(predmet);
        this.ponavljanje = new SimpleStringProperty(ponavljanje);
    }


    public Cas() {
        this.id = new SimpleIntegerProperty(0);
        this.datumOdrzavanja = new SimpleObjectProperty<>(LocalDate.of(2021,1,1));
        this.vrijemePocetka = new SimpleObjectProperty<>(LocalTime.of(1,00));
        this.vrijemeKraja = new SimpleObjectProperty<>(LocalTime.of(1,00));
        this.tipCasa = new SimpleStringProperty("");
        this.predmet = new SimpleObjectProperty<>(null);
        this.ponavljanje = new SimpleStringProperty("");
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

    public String getTipCasa() {
        return tipCasa.get();
    }

    public SimpleStringProperty tipCasaProperty() {
        return tipCasa;
    }

    public void setTipCasa(String tipCasa) {
        this.tipCasa.set(tipCasa);
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

    public String getPonavljanje() {
        return ponavljanje.get();
    }

    public SimpleStringProperty ponavljanjeProperty() {
        return ponavljanje;
    }

    public void setPonavljanje(String ponavljanje) {
        this.ponavljanje.set(ponavljanje);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd");
        String datum = datumOdrzavanja.getValue().format(formatter);
        return predmet.getValue().getNaziv() + ": " + tipCasa.getValue() + "\n" + vrijemePocetka.getValue().toString()
                + "-" + vrijemeKraja.getValue().toString() + " " + datum;
    }

}
