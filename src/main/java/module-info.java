module org.openjfx.gradle.javafx.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires jpro.webapi;
    requires com.jfoenix;
    requires com.calendarfx.view;
    requires java.sql;
    requires javax.mail.api;

    exports com.jpro.hellojpro;
}