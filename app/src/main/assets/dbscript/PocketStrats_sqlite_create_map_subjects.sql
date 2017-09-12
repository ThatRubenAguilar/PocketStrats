--
-- File generated with SQLiteStudio v3.1.1 on Mon Sep 11 12:13:25 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSubjects
DROP TABLE IF EXISTS MapSubjects;

CREATE TABLE MapSubjects (
    SubjectId            INTEGER PRIMARY KEY AUTOINCREMENT,
    MapId                INTEGER REFERENCES Maps (MapId),
    SpawnSideId          INTEGER,
    SpawnSideDescription VARCHAR,
    SubjectDescription   VARCHAR,
    SegmentId            INTEGER REFERENCES MapSegments (SegmentId) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
