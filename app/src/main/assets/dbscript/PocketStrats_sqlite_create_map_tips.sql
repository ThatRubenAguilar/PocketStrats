--
-- File generated with SQLiteStudio v3.1.1 on Wed Oct 4 12:54:12 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapTips
DROP TABLE IF EXISTS MapTips;

CREATE TABLE MapTips (
    MapTipId            INTEGER PRIMARY KEY,
    MapTipDescriptionId INTEGER REFERENCES MapTipDescriptions (MapTipDescriptionId),
    ParentMapTipId      INTEGER REFERENCES MapTips (MapTipId),
    MapSubjectId        INTEGER REFERENCES MapSubjects (MapSubjectId),
    OrderPrecedence     INTEGER NOT NULL
                                DEFAULT (1) 
);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
