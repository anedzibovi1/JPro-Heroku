package com.jpro.hellojpro.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.page.WeekPage;
import com.jfoenix.controls.JFXButton;
import com.jpro.hellojpro.StudyntDAO;
import com.jpro.hellojpro.model.Cas;
import com.jpro.hellojpro.model.Ispit;
import com.jpro.hellojpro.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class KalendarFXMLController implements Initializable {

    private StudyntDAO studyntDAO;
    private Student student;

    @FXML
    protected StackPane spKalendar;
    @FXML
    protected GridPane gpKalendar;

    @FXML
    protected Button btnGlavna;

    @FXML
    protected Button btnKalendar;

    @FXML
    protected Button btnIspiti;

    @FXML
    protected Button btnZadaci;

    @FXML
    protected Button btnRaspored;

    @FXML
    protected Button btnPretraga;

    @FXML
    protected JFXButton btnPostavke;

    public KalendarFXMLController(Student student, StudyntDAO model) {
        this.student = student;
        studyntDAO = model;
    }



    public void otvoriGlavnuStranicu(ActionEvent actionEvent) throws IOException {
        GlavnaStranicaFXMLController glavnaStranicaFXMLController = new GlavnaStranicaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/GlavnaStranica.fxml"));
        loader.setController(glavnaStranicaFXMLController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);
    }

    public void otvoriKalendar(ActionEvent actionEvent) throws IOException {
        KalendarFXMLController kalendarFXMLController = new KalendarFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Kalendar.fxml"));
        loader.setController(kalendarFXMLController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);
    }

    public void otvoriZadatke(ActionEvent actionEvent) throws IOException {
        ZadaciFXMLController zadaciFXMLController = new ZadaciFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Zadaci.fxml"));
        loader.setController(zadaciFXMLController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);
    }

    public void otvoriIspite(ActionEvent actionEvent) throws IOException {
        IspitiFXMLController ispitiFXMLController = new IspitiFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Ispiti.fxml"));
        loader.setController(ispitiFXMLController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);
    }

    public void otvoriRaspored(ActionEvent actionEvent) throws IOException {
        RasporedFXMLController rasporedFXMLController = new RasporedFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Raspored.fxml"));
        loader.setController(rasporedFXMLController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);
    }

    public void otvoriPretragu(ActionEvent actionEvent) throws IOException {
        PretragaFXMLController pretragaFXMLController = new PretragaFXMLController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/Pretraga.fxml"));
        loader.setController(pretragaFXMLController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);

        spKalendar.getScene().getWindow().setOnShown(pretragaFXMLController::adjustUI);

    }

    public void otvoriPostavkeProfila(ActionEvent actionEvent) throws IOException {
        ProfilStudentaController profilStudentaController  = new ProfilStudentaController(student, studyntDAO);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jpro/hellojpro/fxml/ProfilStudenta.fxml"));
        loader.setController(profilStudentaController);
        StackPane stackPane = loader.load();
        spKalendar.getChildren().setAll(stackPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnGlavna.setCursor(Cursor.HAND);
        btnZadaci.setCursor(Cursor.HAND);
        btnIspiti.setCursor(Cursor.HAND);
        btnKalendar.setCursor(Cursor.HAND);
        btnPretraga.setCursor(Cursor.HAND);
        btnRaspored.setCursor(Cursor.HAND);

        URL url = getClass().getResource(student.getSlika());
        Image imgStudent = new Image(String.valueOf(url), 60,60,true,true);
        ImageView viewStudent = new ImageView(imgStudent);
        btnPostavke.setGraphic(viewStudent);

        WeekPage page = new WeekPage();

        Calendar calendar = new Calendar("Schedule");

        /*Entry<String> ispit = new Entry<>("Ispit iz RA");
        ispit.setInterval(LocalTime.of(12,30), LocalTime.of(14,00));
        ispit.setRecurrenceRule("RRULE:FREQ=WEEKLY");

        Entry<String> ispit2 = new Entry<>("Ispit iz PIS");
        ispit2.setInterval(LocalTime.of(12,30), LocalTime.of(13,30));
        ispit2.setRecurrenceRule("RRULE:FREQ=WEEKLY");

        calendar.addEntry(ispit);
        calendar.addEntry(ispit2);
        */

        for(Cas c : studyntDAO.casovi(student.getId())) {
            Entry<String> e = new Entry<>();
            e.setTitle(c.getPredmet().getNaziv() + " " + c.getTipCasa());
            e.setInterval(c.getDatumOdrzavanja());
            e.setInterval(c.getVrijemePocetka(), c.getVrijemeKraja());
            if(c.getPonavljanje().equals("Da")) e.setRecurrenceRule("RRULE:FREQ=WEEKLY");

            calendar.addEntry(e);
        }

        for(Ispit i : studyntDAO.ispiti(student.getId())) {
            Entry<String> entry = new Entry<>();
            entry.setTitle(i.getNaziv());
            entry.setInterval(i.getDatumOdrzavanja());
            entry.setInterval(i.getVrijemePocetka(), i.getVrijemeKraja());

            calendar.addEntry(entry);
        }

        calendar.setReadOnly(true);
        calendar.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("Calendars");
        myCalendarSource.getCalendars().addAll(calendar);

        page.getCalendarSources().setAll(myCalendarSource);

        gpKalendar.add(page, 0, 1, 5, 2);

    }
}
