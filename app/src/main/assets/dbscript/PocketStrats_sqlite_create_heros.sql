--
-- File generated with SQLiteStudio v3.1.1 on Mon Sep 11 12:30:33 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Heros
DROP TABLE IF EXISTS Heros;

CREATE TABLE Heros (
    HeroId       INTEGER PRIMARY KEY AUTOINCREMENT,
    HeroName     VARCHAR,
    HeroIconName VARCHAR
);

INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (1, 'Ana', 'ic_ana.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (2, 'Bastion', 'ic_bastion.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (3, 'D.Va', 'ic_dva.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (4, 'Doomfist', 'ic_doomfist.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (5, 'Genji', 'ic_genji.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (6, 'Hanzo', 'ic_hanzo.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (7, 'Junkrat', 'ic_junkrat.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (8, 'Lúcio', 'ic_lucio.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (9, 'McCree', 'ic_mccree.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (10, 'Mei', 'ic_mei.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (11, 'Mercy', 'ic_mercy.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (12, 'Orisa', 'ic_orisa.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (13, 'Pharah', 'ic_pharah.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (14, 'Reaper', 'ic_reaper.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (15, 'Reinhardt', 'ic_reinhardt.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (16, 'Roadhog', 'ic_roadhog.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (17, 'Soldier: 76', 'ic_soldier76.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (18, 'Sombra', 'ic_sombra.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (19, 'Symmetra', 'ic_symmetra.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (20, 'Torbjörn', 'ic_torbjorn.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (21, 'Tracer', 'ic_tracer.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (22, 'Widowmaker', 'ic_widowmaker.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (23, 'Winston', 'ic_winston.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (24, 'Zarya', 'ic_zarya.png');
INSERT INTO Heros (HeroId, HeroName, HeroIconName) VALUES (25, 'Zenyatta', 'ic_zenyatta.png');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
