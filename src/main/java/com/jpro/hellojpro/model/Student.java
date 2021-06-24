package com.jpro.hellojpro.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

    private SimpleIntegerProperty id;
    private SimpleStringProperty ime, prezime, email, password, slika;

    public Student(String ime, String prezime, String email, String password) {

        this.id = new SimpleIntegerProperty(0);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.slika = new SimpleStringProperty("/com/jpro/hellojpro/img/001-boy.png");
    }

    public Student(int id, String ime, String prezime, String email, String password, String slika) {

        this.id = new SimpleIntegerProperty(id);
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.slika = new SimpleStringProperty(slika);
    }

    public Student() {
        this.id = new SimpleIntegerProperty(0);
        this.ime = new SimpleStringProperty("");
        this.prezime = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.slika = new SimpleStringProperty("/com/jpro/hellojpro/img/001-boy.png");
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
    public String getIme() {
        return ime.get();
    }

    public SimpleStringProperty imeProperty() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime.set(ime);
    }

    public String getPrezime() {
        return prezime.get();
    }

    public SimpleStringProperty prezimeProperty() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime.set(prezime);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getSlika() {
        return slika.get();
    }

    public SimpleStringProperty slikaProperty() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika.set(slika);
    }

    @Override
    public String toString() {
        return id.getValue() + " " + ime.getValue() + " " + prezime.getValue();
    }
}
