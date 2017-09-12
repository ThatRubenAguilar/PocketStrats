--
-- File generated with SQLiteStudio v3.1.1 on Mon Sep 11 12:14:27 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapHeroPickTips
DROP TABLE IF EXISTS MapHeroPickTips;

CREATE TABLE MapHeroPickTips (
    PickTipId          INTEGER PRIMARY KEY,
    SubjectId          INTEGER REFERENCES MapSubjects (SubjectId),
    HeroId             INTEGER REFERENCES Heros (HeroId),
    ParentTipId        INTEGER REFERENCES MapTips (TipId),
    PickTipDescription TEXT
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
