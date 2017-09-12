--
-- File generated with SQLiteStudio v3.1.1 on Mon Sep 11 12:12:57 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapTips
DROP TABLE IF EXISTS MapTips;

CREATE TABLE MapTips (
    TipId          INTEGER PRIMARY KEY,
    SubjectId      INTEGER REFERENCES MapSubjects (SubjectId),
    TipDescription TEXT    NOT NULL,
    ParentTipId    INTEGER REFERENCES MapTips (TipId) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
