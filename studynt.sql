BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "ispit" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"datum_odrzavanja"	TEXT,
	"vrijeme_pocetka"	TEXT,
	"vrijeme_kraja"	TEXT,
	"predmet_id"	INTEGER,
	FOREIGN KEY("predmet_id") REFERENCES "predmet"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "zadatak" (
	"id"	INTEGER,
	"datum_zavrsetka"	TEXT,
	"naziv"	TEXT,
	"opis"	TEXT,
	"predmet_id"	INTEGER,
	FOREIGN KEY("predmet_id") REFERENCES "predmet"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "predmet" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"student_id"	INTEGER,
	FOREIGN KEY("student_id") REFERENCES "student"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "cas" (
	"id"	INTEGER,
	"datum_odrzavanja"	TEXT,
	"vrijeme_pocetka"	TEXT,
	"vrijeme_kraja"	TEXT,
	"tip_casa"	TEXT,
	"predmet_id"	INTEGER,
	"ponavljanje"	TEXT,
	FOREIGN KEY("predmet_id") REFERENCES "predmet"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "student" (
	"id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"email"	TEXT,
	"lozinka"	TEXT,
	"slika"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "ispit" VALUES (0,'','2021-01-01','01:00','01:00',0);
INSERT INTO "ispit" VALUES (2,'II provjera znanja','2021-06-10','14:00','14:30',3);
INSERT INTO "ispit" VALUES (3,'Zavrsni','2021-06-23','12:00','13:15',3);
INSERT INTO "ispit" VALUES (4,'II parcijalaa','2021-06-21','14:45','16:00',1);
INSERT INTO "ispit" VALUES (6,'II parcijala','2021-06-08','10:00','11:30',2);
INSERT INTO "ispit" VALUES (7,'I parcijala','2021-06-28','08:00','09:30',6);
INSERT INTO "zadatak" VALUES (0,'2021-01-01','','',0);
INSERT INTO "zadatak" VALUES (1,'2021-07-08','Diplomski','Napisati do kraja implementaciju i poslati mentoru',1);
INSERT INTO "zadatak" VALUES (2,'2021-06-07','Ispit','Nauciti teoriju za ispit',2);
INSERT INTO "zadatak" VALUES (3,'2021-06-08','neki zadatak','valjda ce se pojaviti ',10);
INSERT INTO "predmet" VALUES (0,'',0);
INSERT INTO "predmet" VALUES (1,'RPR',2);
INSERT INTO "predmet" VALUES (2,'SI',2);
INSERT INTO "predmet" VALUES (3,'VI',2);
INSERT INTO "predmet" VALUES (4,'PIS',3);
INSERT INTO "predmet" VALUES (5,'OSP',3);
INSERT INTO "predmet" VALUES (6,'RG',1);
INSERT INTO "predmet" VALUES (7,'RPR',1);
INSERT INTO "predmet" VALUES (8,'RG',2);
INSERT INTO "predmet" VALUES (9,'DM',2);
INSERT INTO "predmet" VALUES (10,'IM1',4);
INSERT INTO "predmet" VALUES (11,'RPR',4);
INSERT INTO "cas" VALUES (0,'2021-01-01','01:00','01:00','',0,'');
INSERT INTO "cas" VALUES (1,'2021-06-05','13:15','14:00','Tutorijal',1,'Da');
INSERT INTO "cas" VALUES (2,'2021-06-08','15:15','16:15','Vjezbe',8,'Ne');
INSERT INTO "cas" VALUES (3,'2021-07-01','10:00','12:00','Tutorijal',1,'Da');
INSERT INTO "cas" VALUES (4,'2021-06-02','08:15','10:00','Predavanje',3,'Ne');
INSERT INTO "cas" VALUES (6,'2021-06-20','10:15','11:00','Predavanje',4,'Da');
INSERT INTO "cas" VALUES (7,'2021-06-16','15:30','17:00','Predavanje',5,'Ne');
INSERT INTO "cas" VALUES (8,'2021-06-22','08:00','10:45','Tutorijal',5,'Ne');
INSERT INTO "cas" VALUES (9,'2021-06-04','12:00','14:00','Vjezbe',2,'Da');
INSERT INTO "cas" VALUES (11,'2021-06-07','16:00','17:45','Konsultacije',8,'Ne');
INSERT INTO "cas" VALUES (12,'2021-06-09','19:00','22:30','Konsultacije',2,'Ne');
INSERT INTO "cas" VALUES (14,'2021-06-05','13:00','15:00','Vjezbe',10,'Da');
INSERT INTO "cas" VALUES (15,'2021-06-10','09:00','11:30','Konsultacije',11,'Ne');
INSERT INTO "cas" VALUES (16,'2021-06-14','07:00','09:00','Vjezbe',10,'Da');
INSERT INTO "cas" VALUES (17,'2021-06-17','15:00','16:05','Predavanje',9,'Ne');
INSERT INTO "student" VALUES (0,'','','','',NULL);
INSERT INTO "student" VALUES (1,'Meho','Kaspic','mkaspic1@etf.unsa.ba','mkaspic111','/com/jpro/hellojpro/img/001-boy.png');
INSERT INTO "student" VALUES (2,'Amila','Nedzibovic','anedzibovi1@etf.unsa.ba','amilan0712','/com/jpro/hellojpro/img/001-boy.png');
INSERT INTO "student" VALUES (3,'Nedo','Pravic','npravic1@etf.unsa.ba','npravic111','/com/jpro/hellojpro/img/001-boy.png');
INSERT INTO "student" VALUES (4,'Ledo','Pekmezic','lpekmezic1@etf.unsa.ba','lpekmezic111','/com/jpro/hellojpro/img/001-boy.png');
COMMIT;
