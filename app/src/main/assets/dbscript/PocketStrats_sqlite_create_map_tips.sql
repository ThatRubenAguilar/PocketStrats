--
-- File generated with SQLiteStudio v3.1.1 on Tue Sep 12 07:41:27 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapTips
DROP TABLE IF EXISTS MapTips;

CREATE TABLE MapTips (
    MapTipId          INTEGER PRIMARY KEY,
    MapTipDescription TEXT,
    ParentMapTipId    INTEGER REFERENCES MapTips (MapTipId),
    MapSubjectId      INTEGER REFERENCES MapSubjects (MapSubjectId),
    OrderPrecedence   INTEGER NOT NULL
                              DEFAULT (1) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
