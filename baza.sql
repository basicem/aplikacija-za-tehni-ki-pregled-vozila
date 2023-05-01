BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "employee" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"password" TEXT,
	"user_name" TEXT,
	"birth_date" TEXT,
	"hire_date" TEXT,
	"admin"	BIT DEFAULT 0,
	PRIMARY KEY("id")
);
INSERT INTO "employee" VALUES (1,'Meho','Mehić','lozinka','mehomehic','2000-02-02','2000-09-02',1);
INSERT INTO "employee" VALUES (2,'Dado','Dadić','lozinka','dadodadic','1977-03-20','2000-09-02',0);
INSERT INTO "employee" VALUES (3,'Lana','Lanić','lozinka','lanalanic','1996-11-27','2000-09-02',0);
INSERT INTO "employee" VALUES (4,'Darko','Darkić','lozinka','darkodarkic','1994-01-01','2000-09-02',0);
INSERT INTO "employee" VALUES (5,'Mišo','Mišić','lozinka','misomisic','1990-01-14','2000-09-02',0);
CREATE TABLE IF NOT EXISTS "vehicle" (
	"id"	INTEGER,
	"type"	TEXT,
	"brand" TEXT,
	"model" TEXT,
	"year_of_production" INT,
	"registration" TEXT UNIQUE,
	"chassis_number" INT UNIQUE,
	"color" TEXT,
	"color_type" TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "vehicle" VALUES(1, 'Putnički','Suzuki','Ignis',2007,'A12-A-345','JS1VX51L982100325','Siva','Metalik');
INSERT INTO "vehicle" VALUES(2, 'Putnički','Fiat','Panda',2019,'E21-O-213','3C3CFFAR2ET358453','Crna','Folija');
INSERT INTO "vehicle" VALUES(3, 'Autobus','Volvo','S60',2000,'T00-T-208','4VGJDAWF0WN861479','Smeđa','Obična');
CREATE TABLE IF NOT EXISTS "customer" (
	"id"	INTEGER,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"address" TEXT,
    "phone_number" TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "customer" VALUES (1,'Patak','Patkić','Branilaca Sarajeva 21','062-222-222');
INSERT INTO "customer" VALUES (2,'Ivo','Ivić','Vladimira Valtera Perića 13','061-111-111');
INSERT INTO "customer" VALUES (3,'Huso','Husić','Grbavica 13','065-141-111');
CREATE TABLE IF NOT EXISTS "technical_inspection" (
	"id"	INTEGER,
	"date_of_inspection" TEXT,
	"vehicle_id"	INT,
	"customer_id" INT,
    "type_of_technical_inspection" TEXT,
    "status_of_technical_inspection" TEXT,
    "engine_type" TEXT,
    "engine_tact" TEXT,
    "type_of_fuel" TEXT,
    "type_of_gearbox" TEXT,
    "width" DOUBLE,
    "length" DOUBLE,
    "height" DOUBLE,
    "places_to_sit" INT,
    "places_to_stand" INT,
    "places_to_lie_down" INT,
    "comment" TEXT,
    "valid" BIT,
    "price" DOUBLE,
	PRIMARY KEY("id")
);
INSERT INTO "technical_inspection" VALUES (1,'2022-02-02', 1, 2,'Redovni','Zakazan', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "technical_inspection" VALUES (2,'2022-02-02', 3, 2,'Redovni','Otkazan', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "technical_inspection" VALUES (3,'2021-03-05', 2, 3,'Redovni','Kompletiran', 'Otto', 'Dvotaktni', 'Diesel', 'Automatski', 1000, 1000, 1000, 2, 2, 2, 'Sve OK!', 1, 200.00);
INSERT INTO "technical_inspection" VALUES (4,'2020-09-08', 1, 1,'Redovni','Kompletiran', 'Kombinovani pogon', 'Dvotaktni', 'Benzin','Automatski', 1200, 1200, 1100, 2, 2, 2, 'Sve OK!', 1, 200.00);
INSERT INTO "technical_inspection" VALUES (5,'2020-03-05', 2, 3,'Redovni','Kompletiran', 'Otto', 'Dvotaktni', 'Diesel', 'Automatski', 900, 900, 800, 0, 1, 1, 'Dimenzije vozila nisu po pravilima!', 0, 250.00);
CREATE TABLE IF NOT EXISTS "technical_inspection_team" (
    "technical_inspection_id" INT,
    "employee_id" INT
);
INSERT INTO "technical_inspection_team" VALUES (1,1);
INSERT INTO "technical_inspection_team" VALUES (3,1);
INSERT INTO "technical_inspection_team" VALUES (4,4);
INSERT INTO "technical_inspection_team" VALUES (3,4);
INSERT INTO "technical_inspection_team" VALUES (2,2);
INSERT INTO "technical_inspection_team" VALUES (3,2);
INSERT INTO "technical_inspection_team" VALUES (3,3);
INSERT INTO "technical_inspection_team" VALUES (5,4);
INSERT INTO "technical_inspection_team" VALUES (5,5);
COMMIT;
