package com.jpro.hellojpro.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Predmet {

    private SimpleIntegerProperty id;
    private SimpleStringProperty naziv;
    private SimpleObjectProperty<Student> student;

    public Predmet(int id, String naziv, Student student) {
        this.id = new SimpleIntegerProperty(id);
        this.naziv = new SimpleStringProperty(naziv);
        this.student = new SimpleObjectProperty<>(student);
    }

    public Predmet(String naziv, Student student) {
        this.id = new SimpleIntegerProperty(0);
        this.naziv = new SimpleStringProperty(naziv);
        this.student = new SimpleObjectProperty<>(student);
    }

    public Predmet() {
        this.id = new SimpleIntegerProperty(0);
        this.naziv = new SimpleStringProperty("");
        this.student = new SimpleObjectProperty<>(null);
    }

    public Predmet(String naziv) {
        this.id = new SimpleIntegerProperty(0);
        this.naziv = new SimpleStringProperty(naziv);
        this.student = new SimpleObjectProperty<>(null);
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

    public Student getStudent() {
        return student.get();
    }

    public SimpleObjectProperty<Student> studentProperty() {
        return student;
    }

    public void setStudent(Student student) {
        this.student.set(student);
    }

    @Override
    public String toString() {
        return naziv.getValue();
    }
}
