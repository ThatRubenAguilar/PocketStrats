--
-- File generated with SQLiteStudio v3.1.1 on Tue Sep 12 07:42:55 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapHeroPickTips
DROP TABLE IF EXISTS MapHeroPickTips;

CREATE TABLE MapHeroPickTips (
    MapPickTipId INTEGER PRIMARY KEY,
    HeroId       INTEGER REFERENCES Heros (HeroId),
    MapTipId     INTEGER REFERENCES MapTips (MapTipId) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
