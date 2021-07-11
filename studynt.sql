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
INSERT INTO "ispit" VALUES (3,'Zavrsni','2021-06-23','12:00','13:15',3);
INSERT INTO "ispit" VALUES (4,'II parcijala','2021-06-21','14:45','16:00',1);
INSERT INTO "ispit" VALUES (6,'II parcijala','2021-06-08','10:00','11:30',2);
INSERT INTO "ispit" VALUES (8,'Integralni','2021-09-15','13:00','14:30',9);
INSERT INTO "ispit" VALUES (9,'II provjera znanja','2021-07-23','10:00','11:15',8);
INSERT INTO "ispit" VALUES (10,'nez','2021-07-16','08:00','09:10',2);
INSERT INTO "zadatak" VALUES (0,'2021-01-01','','',0);
INSERT INTO "zadatak" VALUES (1,'2021-07-08','Diplomski','Napisati do kraja implementaciju i poslati mentoru',1);
INSERT INTO "zadatak" VALUES (2,'2021-06-07','Ispit','Nauciti teoriju za ispit',2);
INSERT INTO "predmet" VALUES (0,'',0);
INSERT INTO "predmet" VALUES (1,'RPR',2);
INSERT INTO "predmet" VALUES (2,'SI',2);
INSERT INTO "predmet" VALUES (3,'VI',2);
INSERT INTO "predmet" VALUES (8,'RG',2);
INSERT INTO "predmet" VALUES (9,'DM',2);
INSERT INTO "cas" VALUES (0,'2021-01-01','01:00','01:00','',0,'');
INSERT INTO "cas" VALUES (1,'2021-06-05','13:15','14:00','Tutorijal',1,'Da');
INSERT INTO "cas" VALUES (2,'2021-06-08','15:15','16:15','Vjezbe',8,'Ne');
INSERT INTO "cas" VALUES (3,'2021-07-01','10:00','12:00','Tutorijal',1,'Da');
INSERT INTO "cas" VALUES (9,'2021-06-04','12:00','14:00','Vjezbe',2,'Da');
INSERT INTO "cas" VALUES (11,'2021-06-07','16:00','17:45','Konsultacije',8,'Ne');
INSERT INTO "cas" VALUES (12,'2021-06-09','19:00','22:30','Konsultacije',2,'Ne');
INSERT INTO "student" VALUES (0,'','','','',NULL);
INSERT INTO "student" VALUES (2,'Amila','Nedzibovic','anedzibovi1@etf.unsa.ba','amilan0712','/com/jpro/hellojpro/img/008-woman.png');
COMMIT;
