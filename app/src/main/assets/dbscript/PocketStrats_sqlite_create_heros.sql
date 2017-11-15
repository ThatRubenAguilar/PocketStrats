--
-- File generated with SQLiteStudio v3.1.1 on Mon Sep 18 14:56:09 2017
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Heros
DROP TABLE IF EXISTS Heros;

CREATE TABLE Heros (
    HeroId        INTEGER PRIMARY KEY AUTOINCREMENT,
    HeroName      VARCHAR,
    HeroIconName  VARCHAR,
    HeroNameLatin TEXT
);

INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (1, 'Ana', 'ic_ana.png', 'Ana');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (2, 'Bastion', 'ic_bastion.png', 'Bastion');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (3, 'D.Va', 'ic_dva.png', 'D.Va');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (4, 'Doomfist', 'ic_doomfist.png', 'Doomfist');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (5, 'Genji', 'ic_genji.png', 'Genji');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (6, 'Hanzo', 'ic_hanzo.png', 'Hanzo');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (7, 'Junkrat', 'ic_junkrat.png', 'Junkrat');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (8, 'Lúcio', 'ic_lucio.png', 'Lucio');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (9, 'McCree', 'ic_mccree.png', 'McCree');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (10, 'Mei', 'ic_mei.png', 'Mei');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (11, 'Mercy', 'ic_mercy.png', 'Mercy');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (12, 'Orisa', 'ic_orisa.png', 'Orisa');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (13, 'Pharah', 'ic_pharah.png', 'Pharah');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (14, 'Reaper', 'ic_reaper.png', 'Reaper');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (15, 'Reinhardt', 'ic_reinhardt.png', 'Reinhardt');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (16, 'Roadhog', 'ic_roadhog.png', 'Roadhog');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (17, 'Soldier: 76', 'ic_soldier76.png', 'Soldier: 76');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (18, 'Sombra', 'ic_sombra.png', 'Sombra');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (19, 'Symmetra', 'ic_symmetra.png', 'Symmetra');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (20, 'Torbjörn', 'ic_torbjorn.png', 'Torbjorn');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (21, 'Tracer', 'ic_tracer.png', 'Tracer');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (22, 'Widowmaker', 'ic_widowmaker.png', 'Widowmaker');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (23, 'Winston', 'ic_winston.png', 'Winston');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (24, 'Zarya', 'ic_zarya.png', 'Zarya');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (25, 'Zenyatta', 'ic_zenyatta.png', 'Zenyatta');
INSERT INTO Heros (HeroId, HeroName, HeroIconName, HeroNameLatin) VALUES (26, 'Moira', 'ic_moira.png', 'Moira');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
