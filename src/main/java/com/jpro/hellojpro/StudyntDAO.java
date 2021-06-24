package com.jpro.hellojpro;

import com.jpro.hellojpro.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


public class StudyntDAO {

    private static StudyntDAO instance;

    private Connection con;

    private PreparedStatement dajStudenteUpit, dajPredmeteUpit, dajCasoveUpit, dajIspiteUpit, dajZadatkeUpit, dajStudentaUpit, dajPredmetUpit, dodajStudentaUpit,
            odrediIDStudentaUpit, odrediIDPredmetaUpit, odrediIDCasaUpit, odrediIDIspitaUpit, odrediIDZadatkaUpit,
            dajPredmetZaStudentaUpit, dajCasZaStudentaUpit, dajIspitZaStudentaUpit,
            dajZadatakZaStudentaUpit, dajIspitZaPredmetUpit, dajCasZaPredmetUpit, dajZadatakZaPredmetUpit,
            dodajPredmetUpit, dodajCasUpit, dodajZadatakUpit, dodajIspitUpit,
            nadjiStudentaUpit, nadjiStudentaZaEmailUpit, obrisiIspitUpit, obrisiZadatakUpit, obrisiCasUpit, obrisiStudentaUpit, obrisiPredmetUpit,
            obrisiIspitePredmetaUpit, obrisiCasovePredmetaUpit, obrisiZadatkePredmetaUpit,
            izmijeniCasUpit, izmijeniPredmetUpit, izmijeniStudentaUpit, izmijeniIspitUpit, izmijeniZadatakUpit;

    private ObservableList<Student> studenti = FXCollections.observableArrayList();
    private ObservableList<Predmet> predmeti = FXCollections.observableArrayList();
    private ObservableList<Cas> casovi = FXCollections.observableArrayList();
    private ObservableList<Zadatak> zadaci = FXCollections.observableArrayList();
    private ObservableList<Ispit> ispiti = FXCollections.observableArrayList();


    public static StudyntDAO getInstance() {
        if(instance == null) instance = new StudyntDAO();
        return instance;
    }

    public static void removeInstance() {
        if(instance == null) return;
        instance.close();
        instance = null;
    }

    private void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StudyntDAO() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + "/IdeaProjects/HelloJPro/studynt.db");
            //con = DriverManager.getConnection("jdbc:sqlite:studynt.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dajStudenteUpit = con.prepareStatement("select * from student");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                dajStudenteUpit = con.prepareStatement("select * from student");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            dajStudenteUpit = con.prepareStatement("select * from student");
            dajPredmeteUpit = con.prepareStatement("select * from predmet");
            dajCasoveUpit = con.prepareStatement("select * from cas");
            dajIspiteUpit = con.prepareStatement("select * from ispit");
            dajZadatkeUpit = con.prepareStatement("select * from zadatak");

            dajStudentaUpit = con.prepareStatement("select * from student where id=?");
            dajPredmetUpit = con.prepareStatement("select * from predmet where id=?");

            dodajStudentaUpit = con.prepareStatement("insert into student values(?,?,?,?,?,?)");
            dodajPredmetUpit = con.prepareStatement("insert into predmet values(?,?,?)");
            dodajCasUpit = con.prepareStatement("insert into cas values(?,?,?,?,?,?,?)");
            dodajZadatakUpit = con.prepareStatement("insert into zadatak values(?,?,?,?,?)");
            dodajIspitUpit = con.prepareStatement("insert into ispit values(?,?,?,?,?,?)");

            odrediIDStudentaUpit = con.prepareStatement("select max(id)+1 from student");
            odrediIDPredmetaUpit = con.prepareStatement("select max(id)+1 from predmet");
            odrediIDCasaUpit = con.prepareStatement("select max(id)+1 from cas");
            odrediIDIspitaUpit = con.prepareStatement("select max(id)+1 from ispit");
            odrediIDZadatkaUpit = con.prepareStatement("select max(id)+1 from zadatak");

            dajPredmetZaStudentaUpit = con.prepareStatement("select * from predmet where student_id = ?");
            dajIspitZaPredmetUpit = con.prepareStatement("select * from ispit where predmet_id = ?");
            dajZadatakZaPredmetUpit = con.prepareStatement("select * from zadatak where predmet_id = ?");
            dajCasZaPredmetUpit = con.prepareStatement("select * from cas where predmet_id = ?");

            nadjiStudentaUpit = con.prepareStatement("select * from student where email = ? and lozinka = ?");
            nadjiStudentaZaEmailUpit = con.prepareStatement("select * from student where email = ?");

            obrisiIspitUpit = con.prepareStatement("delete from ispit where id=?");
            obrisiZadatakUpit = con.prepareStatement("delete from zadatak where id=?");
            obrisiCasUpit = con.prepareStatement("delete from cas where id=?");
            obrisiStudentaUpit = con.prepareStatement("delete from student where id=?");
            obrisiPredmetUpit = con.prepareStatement("delete from predmet where id=?");

            obrisiIspitePredmetaUpit = con.prepareStatement("delete from ispit where predmet_id=?");
            obrisiCasovePredmetaUpit = con.prepareStatement("delete from cas where predmet_id=?");
            obrisiZadatkePredmetaUpit = con.prepareStatement("delete from zadatak where predmet_id=?");

            izmijeniCasUpit = con.prepareStatement("update cas set datum_odrzavanja=?, vrijeme_pocetka=?, vrijeme_kraja=?, tip_casa=?, predmet_id=?, ponavljanje=? where id=?");
            izmijeniIspitUpit = con.prepareStatement("update ispit set naziv=?, datum_odrzavanja=?, vrijeme_pocetka=?, vrijeme_kraja=?, predmet_id=? where id=?");
            izmijeniZadatakUpit = con.prepareStatement("update zadatak set datum_zavrsetka=?, naziv=?, opis=?, predmet_id=? where id=?");
            izmijeniPredmetUpit = con.prepareStatement("update predmet set naziv=?, student_id=? where id=?");
            izmijeniStudentaUpit = con.prepareStatement("update student set ime=?, prezime=?, email=?, lozinka=?, slika=? where id=?");

            napuniStudente();
            napuniPredmete();
            napuniCasove();
            napuniIspite();
            napuniZadatke();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileInputStream("/IdeaProjects/HelloJPro/studynt.sql"));
            StringBuilder sqlUpit = new StringBuilder("");
            while (scanner.hasNext()) {

                sqlUpit.append(scanner.nextLine());
                if (sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement statement = con.createStatement();
                        statement.execute(sqlUpit.toString());
                        sqlUpit = new StringBuilder();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void napuniStudente() {
        try {
            ArrayList<Student> students = new ArrayList<>();

            ResultSet rs = dajStudenteUpit.executeQuery();
            if(!rs.next()) {

                students.add(new Student("Meho", "Kaspic", "mkaspic1@etf.unsa.ba", "mkaspic111"));
                students.add(new Student("Amila", "Nedzibovic", "anedzibovi1@etf.unsa.ba", "amilan0712"));
                students.add(new Student("Nedo", "Pravic", "npravic1@etf.unsa.ba", "npravic111"));
                students.add(new Student("Ledo", "Pekmezic", "lpekmezic1@etf.unsa.ba", "lpekmezic111"));

                dodajStudenta(new Student());
                dodajStudenta(students.get(0));
                dodajStudenta(students.get(1));
                dodajStudenta(students.get(2));
                dodajStudenta(students.get(3));

            }
            else {

                while (rs.next()) {
                    studenti.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void napuniPredmete() {
        try {
            ArrayList<Predmet> predmets = new ArrayList<>();

            ResultSet rs = dajPredmeteUpit.executeQuery();
            if(!rs.next()) {

                predmets.add(new Predmet("RPR", studenti.get(2)));
                predmets.add(new Predmet("SI", studenti.get(2)));
                predmets.add(new Predmet("VI", studenti.get(2)));
                predmets.add(new Predmet("PIS", studenti.get(3)));
                predmets.add(new Predmet("OSP", studenti.get(3)));
                predmets.add(new Predmet("RG", studenti.get(1)));
                predmets.add(new Predmet("RPR", studenti.get(1)));

                dodajPredmet(new Predmet());
                dodajPredmet(predmets.get(0));
                dodajPredmet(predmets.get(1));
                dodajPredmet(predmets.get(2));
                dodajPredmet(predmets.get(3));
                dodajPredmet(predmets.get(4));
                dodajPredmet(predmets.get(5));
                dodajPredmet(predmets.get(6));

            }
            else {

                while (rs.next()) {
                    Student s = studenti.stream()
                            .filter(x -> {
                                try {
                                    return rs.getInt(3) == x.getId();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            })
                            .findFirst().get();
                    predmeti.add(new Predmet(rs.getInt(1), rs.getString(2), s));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void napuniCasove() {
        try {
            ArrayList<Cas> cass = new ArrayList<>();

            ResultSet rs = dajCasoveUpit.executeQuery();
            if(!rs.next()) {

                cass.add(new Cas(LocalDate.of(2021,6,5), LocalTime.of(13,15), LocalTime.of(14,00), "Tutorijal", predmeti.get(1), "Da"));
                cass.add(new Cas(LocalDate.of(2021,6,5), LocalTime.of(14,15), LocalTime.of(15,00), "Predavanje", predmeti.get(2), "Da"));
                cass.add(new Cas(LocalDate.of(2021,7,1), LocalTime.of(10,00), LocalTime.of(12,00), "Tutorijal", predmeti.get(1), "Da"));
                cass.add(new Cas(LocalDate.of(2021,6,2), LocalTime.of(8,15), LocalTime.of(10,00), "Predavanje", predmeti.get(3), "Ne"));
                cass.add(new Cas(LocalDate.of(2021,5,25), LocalTime.of(12,15), LocalTime.of(13,15), "Tutorijal", predmeti.get(3), "Da"));

                cass.add(new Cas(LocalDate.of(2021,6,20), LocalTime.of(10,15), LocalTime.of(11,00), "Predavanje", predmeti.get(4), "Da"));
                cass.add(new Cas(LocalDate.of(2021,6,16), LocalTime.of(15,30), LocalTime.of(17,00), "Predavanje", predmeti.get(5), "Ne"));
                cass.add(new Cas(LocalDate.of(2021,6,22), LocalTime.of(8,00), LocalTime.of(10,45), "Tutorijal", predmeti.get(5), "Ne"));


                dodajCas(new Cas());
                dodajCas(cass.get(0));
                dodajCas(cass.get(1));
                dodajCas(cass.get(2));
                dodajCas(cass.get(3));
                dodajCas(cass.get(4));
                dodajCas(cass.get(5));
                dodajCas(cass.get(6));
                dodajCas(cass.get(7));

            }
            else {

                while (rs.next()) {
                    Predmet p = predmeti.stream()
                            .filter(x -> {
                                try {
                                    return rs.getInt(6) == x.getId();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            })
                            .findFirst().get();
                    casovi.add(new Cas(rs.getInt(1),
                            LocalDate.parse(rs.getString(2)),
                            LocalTime.parse(rs.getString(3)),
                            LocalTime.parse(rs.getString(4)),
                            rs.getString(5), p,
                            rs.getString(7)));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void napuniIspite() {
        try {
            ArrayList<Ispit> ispits = new ArrayList<>();

            ResultSet rs = dajIspiteUpit.executeQuery();
            if(!rs.next()) {

                ispits.add(new Ispit("II parcijala", LocalDate.of(2021,6,8), LocalTime.of(10,00), LocalTime.of(11,00), predmeti.get(2)));
                ispits.add(new Ispit("II provjera znanja", LocalDate.of(2021,6,10), LocalTime.of(14,00), LocalTime.of(14,30), predmeti.get(3)));
                ispits.add(new Ispit("Zavrsni", LocalDate.of(2021,6,23), LocalTime.of(11,00), LocalTime.of(12,15), predmeti.get(3)));
                ispits.add(new Ispit("II parcijala", LocalDate.of(2021,6,20), LocalTime.of(13,45), LocalTime.of(15,00), predmeti.get(1)));

                dodajIspit(new Ispit());
                dodajIspit(ispits.get(0));
                dodajIspit(ispits.get(1));
                dodajIspit(ispits.get(2));
                dodajIspit(ispits.get(3));

            }
            else {

                while (rs.next()) {
                    Predmet p = predmeti.stream()
                            .filter(x -> {
                                try {
                                    return rs.getInt(6) == x.getId();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            })
                            .findFirst().get();
                    ispiti.add(new Ispit(rs.getInt(1),
                            rs.getString(2),
                            LocalDate.parse(rs.getString(3)),
                            LocalTime.parse(rs.getString(4)),
                            LocalTime.parse(rs.getString(5)), p));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void napuniZadatke() {
        try {
            ArrayList<Zadatak> zadataks = new ArrayList<>();

            ResultSet rs = dajZadatkeUpit.executeQuery();
            if(!rs.next()) {

                zadataks.add(new Zadatak(LocalDate.of(2021,7,1), "Diplomski", "Napisati do kraja implementaciju i poslati profesoru", predmeti.get(1)));
                zadataks.add(new Zadatak(LocalDate.of(2021,6,7), "Ispit", "Nauciti teoriju za ispit", predmeti.get(2)));

                dodajZadatak(new Zadatak());
                dodajZadatak(zadataks.get(0));
                dodajZadatak(zadataks.get(1));

            }
            else {

                while (rs.next()) {
                    Predmet p = predmeti.stream()
                            .filter(x -> {
                                try {
                                    return rs.getInt(5) == x.getId();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                return false;
                            })
                            .findFirst().get();
                    zadaci.add(new Zadatak(rs.getInt(1),
                            LocalDate.parse(rs.getString(2)),
                            rs.getString(3), rs.getString(4), p));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void dodajStudenta(Student student) {
        try {
                ResultSet rs = odrediIDStudentaUpit.executeQuery();
                int id = 1;
                if (rs.next()) id = rs.getInt(1);
                student.setId(id);
                dodajStudentaUpit.setInt(1, student.getId());
                dodajStudentaUpit.setString(2, student.getIme());
                dodajStudentaUpit.setString(3, student.getPrezime());
                dodajStudentaUpit.setString(4, student.getEmail());
                dodajStudentaUpit.setString(5, student.getPassword());
                dodajStudentaUpit.setString(6, student.getSlika());
                dodajStudentaUpit.executeUpdate();
                studenti.add(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajPredmet(Predmet predmet) {
        try {
            ResultSet rs = odrediIDPredmetaUpit.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            predmet.setId(id);
            dodajPredmetUpit.setInt(1, predmet.getId());
            dodajPredmetUpit.setString(2, predmet.getNaziv());
            if(predmet.getStudent() == null ) dodajPredmetUpit.setInt(3, 0);
            else dodajPredmetUpit.setInt(3, predmet.getStudent().getId());
            dodajPredmetUpit.executeUpdate();
            predmeti.add(predmet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajCas(Cas cas) {
        try {
            ResultSet rs = odrediIDCasaUpit.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            cas.setId(id);
            dodajCasUpit.setInt(1,cas.getId());
            dodajCasUpit.setString(2, cas.getDatumOdrzavanja().toString());
            dodajCasUpit.setString(3, cas.getVrijemePocetka().toString());
            dodajCasUpit.setString(4, cas.getVrijemeKraja().toString());
            dodajCasUpit.setString(5, cas.getTipCasa());
            if(cas.getPredmet() == null ) dodajCasUpit.setInt(6, 0);
            else dodajCasUpit.setInt(6, cas.getPredmet().getId());
            dodajCasUpit.setString(7, cas.getPonavljanje());
            dodajCasUpit.executeUpdate();
            casovi.add(cas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajIspit(Ispit ispit) {
        try {
            ResultSet rs = odrediIDIspitaUpit.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            ispit.setId(id);
            dodajIspitUpit.setInt(1, ispit.getId());
            dodajIspitUpit.setString(2, ispit.getNaziv());
            dodajIspitUpit.setString(3, ispit.getDatumOdrzavanja().toString());
            dodajIspitUpit.setString(4, ispit.getVrijemePocetka().toString());
            dodajIspitUpit.setString(5, ispit.getVrijemeKraja().toString());
            if(ispit.getPredmet() == null ) dodajIspitUpit.setInt(6, 0);
            else dodajIspitUpit.setInt(6, ispit.getPredmet().getId());
            dodajIspitUpit.executeUpdate();
            ispiti.add(ispit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajZadatak(Zadatak zadatak) {
        try {
            ResultSet rs = odrediIDZadatkaUpit.executeQuery();
            int id = 1;
            if (rs.next()) id = rs.getInt(1);
            zadatak.setId(id);
            dodajZadatakUpit.setInt(1, zadatak.getId());
            dodajZadatakUpit.setString(2, zadatak.getDatumZavrsetka().toString());
            dodajZadatakUpit.setString(3, zadatak.getNaziv());
            dodajZadatakUpit.setString(4, zadatak.getOpis());
            if(zadatak.getPredmet() == null ) dodajZadatakUpit.setInt(5, 0);
            else dodajZadatakUpit.setInt(5, zadatak.getPredmet().getId());
            dodajZadatakUpit.executeUpdate();
            zadaci.add(zadatak);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //lista elemenata koja se dobije za id studenta
    public ArrayList<Predmet> predmeti(int id) {
        ArrayList<Predmet> predmeti = new ArrayList<>();

        try {
            dajPredmetZaStudentaUpit.setInt(1,id);
            ResultSet resultSet = dajPredmetZaStudentaUpit.executeQuery();
            while(resultSet.next()) {
                Predmet predmet = dajPredmetIzResultSeta(resultSet);
                predmeti.add(predmet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return predmeti;
    }

    public ArrayList<Ispit> ispiti(int id) {
        ArrayList<Ispit> ispiti = new ArrayList<>();

        ArrayList<Predmet> predmeti = predmeti(id);

        try {
            for(Predmet p : predmeti) {
                dajIspitZaPredmetUpit.setInt(1,p.getId());
                ResultSet resultSet = dajIspitZaPredmetUpit.executeQuery();
                while(resultSet.next()) {
                    Ispit ispit = dajIspitIzResultSeta(resultSet);
                    ispiti.add(ispit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ispiti;
    }

    public ArrayList<Zadatak> zadaci(int id) {
        ArrayList<Zadatak> zadaci = new ArrayList<>();

        ArrayList<Predmet> predmeti = predmeti(id);

        try {
            for (Predmet p : predmeti) {
                dajZadatakZaPredmetUpit.setInt(1, p.getId());
                ResultSet resultSet = dajZadatakZaPredmetUpit.executeQuery();
                while (resultSet.next()) {
                    Zadatak zadatak = dajZadatakIzResultSeta(resultSet);
                    zadaci.add(zadatak);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zadaci;
    }

    public ArrayList<Cas> casovi(int id) {
        ArrayList<Cas> casovi = new ArrayList<>();

        ArrayList<Predmet> predmeti = predmeti(id);

        try {
            for(Predmet p : predmeti) {
                dajCasZaPredmetUpit.setInt(1,p.getId());
                ResultSet resultSet = dajCasZaPredmetUpit.executeQuery();
                while(resultSet.next()) {
                    Cas cas = dajCasIzResultSeta(resultSet);
                    casovi.add(cas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return casovi;
    }

    public ArrayList<Cas> casoviDanas(int id) {
        ArrayList<Cas> casovi = new ArrayList<>();

        ArrayList<Predmet> predmeti = predmeti(id);

        try {
            for(Predmet p : predmeti) {
                dajCasZaPredmetUpit.setInt(1,p.getId());
                ResultSet resultSet = dajCasZaPredmetUpit.executeQuery();
                while(resultSet.next()) {
                    Cas cas = dajCasIzResultSeta(resultSet);
                    if(cas.getDatumOdrzavanja().equals(LocalDate.now())) casovi.add(cas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return casovi;
    }

    public ObservableList<Cas> getCasoviDanas(Student student) {
        return casovi(student.getId()).stream()
                .filter(x -> x.getDatumOdrzavanja().equals(LocalDate.now())).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public ObservableList<Ispit> getIspitiDanas(Student student) {
        return ispiti(student.getId()).stream()
                .filter(x -> x.getDatumOdrzavanja().equals(LocalDate.now())).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public ObservableList<Predmet> getPredmetiStudent(int id) {
        return predmeti.stream()
                .filter(x -> x.getStudent().getId() == id).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public ObservableList<Cas> getCasoviStudent(int id) {
        return casovi.stream()
                .filter(x -> x.getPredmet().getStudent().getId() == id).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public ObservableList<Ispit> getIspitiStudent(int id) {
        return ispiti.stream()
                .filter(x -> x.getPredmet().getStudent().getId() == id).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public ObservableList<Zadatak> getZadaciStudent(int id) {
        return zadaci.stream()
                .filter(x -> x.getPredmet().getStudent().getId() == id).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public ArrayList<Ispit> ispitiDanas(int id) {
        ArrayList<Ispit> ispiti = new ArrayList<>();

        ArrayList<Predmet> predmeti = predmeti(id);

        try {
            for(Predmet p : predmeti) {
                dajIspitZaPredmetUpit.setInt(1,p.getId());
                ResultSet resultSet = dajIspitZaPredmetUpit.executeQuery();
                while(resultSet.next()) {
                    Ispit ispit = dajIspitIzResultSeta(resultSet);
                    if(ispit.getDatumOdrzavanja().equals(LocalDate.now())) ispiti.add(ispit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ispiti;
    }


    //element koji se dobije iz resultseta
    private Cas dajCasIzResultSeta(ResultSet resultSet) throws SQLException {
        if(resultSet.isClosed()) return new Cas();
        Cas cas = new Cas(resultSet.getInt(1),
                null, null, null,
                resultSet.getString(5), null, resultSet.getString(7));

        LocalDate datumOdrzavanja = LocalDate.parse(resultSet.getString(2));
        LocalTime vrijemePocetka = LocalTime.parse(resultSet.getString(3));
        LocalTime vrijemeKraja = LocalTime.parse(resultSet.getString(4));

        cas.setPredmet(dajPredmet(resultSet.getInt(6)));
        cas.setDatumOdrzavanja(datumOdrzavanja);
        cas.setVrijemePocetka(vrijemePocetka);
        cas.setVrijemeKraja(vrijemeKraja);


        return cas;
    }

    private Zadatak dajZadatakIzResultSeta(ResultSet resultSet) throws SQLException {
        if(resultSet.isClosed()) return new Zadatak();
        Zadatak zadatak = new Zadatak(resultSet.getInt(1),
                null,
                resultSet.getString(3), resultSet.getString(4), null);

        LocalDate datumZavrsetka = LocalDate.parse(resultSet.getString(2));

        zadatak.setPredmet(dajPredmet(resultSet.getInt(5)));
        zadatak.setDatumZavrsetka(datumZavrsetka);

        return zadatak;
    }

    private Ispit dajIspitIzResultSeta(ResultSet resultSet) throws SQLException {
        if(resultSet.isClosed()) return new Ispit();
        Ispit ispit = new Ispit(resultSet.getInt(1),
                resultSet.getString(2), null,
                null, null, null);

        LocalDate datumOdrzavanja = LocalDate.parse(resultSet.getString(3));
        LocalTime vrijemePocetka = LocalTime.parse(resultSet.getString(4));
        LocalTime vrijemeKraja = LocalTime.parse(resultSet.getString(5));

        ispit.setPredmet(dajPredmet(resultSet.getInt(6)));
        ispit.setDatumOdrzavanja(datumOdrzavanja);
        ispit.setVrijemePocetka(vrijemePocetka);
        ispit.setVrijemeKraja(vrijemeKraja);

        return ispit;
    }

    private Predmet dajPredmetIzResultSeta(ResultSet resultSet) throws SQLException {
        if(resultSet.isClosed()) return new Predmet();
        Predmet predmet = new Predmet(resultSet.getInt(1),
                resultSet.getString(2),
                null);

        predmet.setStudent(dajStudenta(resultSet.getInt(3)));

        return predmet;
    }

    private Student dajStudenta(int id) {
        try {
            dajStudentaUpit.setInt(1, id);
            ResultSet resultSet = dajStudentaUpit.executeQuery();
            if(!resultSet.next()) return null;
            return new Student(id, resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5),resultSet.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Predmet dajPredmet(int id) {
        try {
            dajPredmetUpit.setInt(1, id);
            ResultSet resultSet = dajPredmetUpit.executeQuery();
            if(!resultSet.next()) return null;
            return dajPredmetIzResultSeta(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Student nadjiStudenta(String email, String password) {
        try {
            nadjiStudentaUpit.setString(1, email);
            nadjiStudentaUpit.setString(2, password);
            ResultSet rs = nadjiStudentaUpit.executeQuery();
            if(!rs.next()) return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studenti.stream()
                .filter(x -> email.equals(x.getEmail()) && password.equals(x.getPassword()))
                .findFirst().get();
    }



    public Connection getConn() {
        return con;
    }

    public ObservableList<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(ObservableList<Student> studenti) {
        this.studenti = studenti;
    }

    public ObservableList<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(ObservableList<Predmet> predmeti) {
        this.predmeti = predmeti;
    }

    public ObservableList<Cas> getCasovi() {
        return casovi;
    }

    public void setCasovi(ObservableList<Cas> casovi) {
        this.casovi = casovi;
    }

    public ObservableList<Zadatak> getZadaci(Student student) {
        return zadaci(student.getId()).stream().collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public void setZadaci(ObservableList<Zadatak> zadaci) {
        this.zadaci = zadaci;
    }

    public ObservableList<Ispit> getIspiti(Student student) {
        return ispiti(student.getId()).stream().collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
    }

    public void setIspiti(ObservableList<Ispit> ispiti) {
        this.ispiti = ispiti;
    }



    public Student nadjiStudentaZaEmail(String email) {
        try {
            nadjiStudentaZaEmailUpit.setString(1, email);
            ResultSet rs = nadjiStudentaZaEmailUpit.executeQuery();
            if(!rs.next()) return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studenti.stream()
                .filter(x -> email.equals(x.getEmail()))
                .findFirst().get();
    }

    public void obrisiIspit(Ispit ispit) {
        try {
            obrisiIspitUpit.setInt(1,ispit.getId());
            obrisiIspitUpit.executeUpdate();
            ispiti.remove(ispit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiZadatak(Zadatak zadatak) {
        try {
            obrisiZadatakUpit.setInt(1,zadatak.getId());
            obrisiZadatakUpit.executeUpdate();
            zadaci.remove(zadatak);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiCas(Cas cas) {
        try {
            obrisiCasUpit.setInt(1,cas.getId());
            obrisiCasUpit.executeUpdate();
            casovi.remove(cas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void obrisiStudenta(Student student) {
        try {
            Student s = dajStudenta(student.getId());
            if(s == null) return;
            do {
                obrisiPredmet(predmeti(s.getId()).get(predmeti(s.getId()).size() - 1));
            } while (predmeti(student.getId()).size() != 0);

            obrisiStudentaUpit.setInt(1,student.getId());
            obrisiStudentaUpit.executeUpdate();
            studenti.remove(student);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiPredmet(Predmet predmet) {
        try {
            Predmet p = dajPredmet(predmet.getId());
            if(p == null) return;
            obrisiCasovePredmetaUpit.setInt(1, p.getId());
            obrisiIspitePredmetaUpit.setInt(1, p.getId());
            obrisiZadatkePredmetaUpit.setInt(1, p.getId());

            obrisiCasovePredmetaUpit.executeUpdate();
            obrisiIspitePredmetaUpit.executeUpdate();
            obrisiZadatkePredmetaUpit.executeUpdate();

            obrisiPredmetUpit.setInt(1,p.getId());
            obrisiPredmetUpit.executeUpdate();
            predmeti.remove(predmet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniCas(Cas cas) {
        try {
            izmijeniCasUpit.setString(1, cas.getDatumOdrzavanja().toString());
            izmijeniCasUpit.setString(2,cas.getVrijemePocetka().toString());
            izmijeniCasUpit.setString(3, cas.getVrijemeKraja().toString());
            izmijeniCasUpit.setString(4, cas.getTipCasa());
            izmijeniCasUpit.setInt(5, cas.getPredmet().getId());
            izmijeniCasUpit.setString(6, cas.getPonavljanje());
            izmijeniCasUpit.setInt(7, cas.getId());
            izmijeniCasUpit.executeUpdate();
            casovi = casovi.stream()
                    .peek(i -> {
                        if(i.getId() == cas.getId()) {
                            i.setDatumOdrzavanja(cas.getDatumOdrzavanja());
                            i.setVrijemePocetka(cas.getVrijemePocetka());
                            i.setVrijemeKraja(cas.getVrijemeKraja());
                            i.setTipCasa(cas.getTipCasa());
                            i.setPredmet(cas.getPredmet());
                            i.setPonavljanje(cas.getPonavljanje());
                        }
                    }).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void izmijeniIspit(Ispit ispit) {
        try {
            izmijeniIspitUpit.setString(1, ispit.getNaziv());
            izmijeniIspitUpit.setString(2, ispit.getDatumOdrzavanja().toString());
            izmijeniIspitUpit.setString(3, ispit.getVrijemePocetka().toString());
            izmijeniIspitUpit.setString(4, ispit.getVrijemeKraja().toString());
            izmijeniIspitUpit.setInt(5, ispit.getPredmet().getId());
            izmijeniIspitUpit.setInt(6, ispit.getId());
            izmijeniIspitUpit.executeUpdate();
            ispiti = ispiti.stream()
                    .peek(i -> {
                        if(i.getId() == ispit.getId()) {
                            i.setNaziv(ispit.getNaziv());
                            i.setDatumOdrzavanja(ispit.getDatumOdrzavanja());
                            i.setVrijemePocetka(ispit.getVrijemePocetka());
                            i.setVrijemeKraja(ispit.getVrijemeKraja());
                            i.setPredmet(ispit.getPredmet());
                        }
                    }).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void izmijeniPredmet(Predmet predmet) {
        try {
            izmijeniPredmetUpit.setString(1, predmet.getNaziv());
            izmijeniPredmetUpit.setInt(2, predmet.getStudent().getId());
            izmijeniPredmetUpit.setInt(3, predmet.getId());
            izmijeniPredmetUpit.executeUpdate();
            predmeti = predmeti.stream()
                    .peek(i -> {
                        if(i.getId() == predmet.getId()) {
                            i.setNaziv(predmet.getNaziv());
                            i.setStudent(predmet.getStudent());
                        }
                    }).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniZadatak(Zadatak zadatak) {
        try {
            izmijeniZadatakUpit.setString(1, zadatak.getDatumZavrsetka().toString());
            izmijeniZadatakUpit.setString(2, zadatak.getNaziv());
            izmijeniZadatakUpit.setString(3, zadatak.getOpis());
            izmijeniZadatakUpit.setInt(4, zadatak.getPredmet().getId());
            izmijeniZadatakUpit.setInt(5, zadatak.getId());
            izmijeniZadatakUpit.executeUpdate();
            zadaci = zadaci.stream()
                    .peek(i -> {
                        if(i.getId() == zadatak.getId()) {
                            i.setNaziv(zadatak.getNaziv());
                            i.setDatumZavrsetka(zadatak.getDatumZavrsetka());
                            i.setOpis(zadatak.getOpis());
                            i.setPredmet(zadatak.getPredmet());
                        }
                    }).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniStudenta(Student student) {
        try {
            izmijeniStudentaUpit.setString(1, student.getIme());
            izmijeniStudentaUpit.setString(2, student.getPrezime());
            izmijeniStudentaUpit.setString(3, student.getEmail());
            izmijeniStudentaUpit.setString(4, student.getPassword());
            izmijeniStudentaUpit.setString(5, student.getSlika());
            izmijeniStudentaUpit.setInt(6, student.getId());
            izmijeniStudentaUpit.executeUpdate();
            studenti = studenti.stream()
                    .peek(i -> {
                        if(i.getId() == student.getId()) {
                            i.setIme(student.getIme());
                            i.setPrezime(student.getPrezime());
                            i.setEmail(student.getEmail());
                            i.setPassword(student.getPassword());
                            i.setSlika(student.getSlika());
                        }
                    }).collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}