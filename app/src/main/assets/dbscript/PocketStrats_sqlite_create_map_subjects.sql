--
-- File generated with SQLiteStudio v3.1.1 on Tue Sep 19 10:45:21 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSubjects
DROP TABLE IF EXISTS MapSubjects;

CREATE TABLE MapSubjects (
    MapSubjectId          INTEGER PRIMARY KEY AUTOINCREMENT,
    MapId                 INTEGER REFERENCES Maps (MapId),
    SpawnSideId           INTEGER,
    SpawnSideDescription  VARCHAR,
    SegmentId             INTEGER REFERENCES MapSegments (SegmentId),
    MapSubjectPrecedence  INTEGER DEFAULT (1),
    MapSubjectDescription TEXT
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
