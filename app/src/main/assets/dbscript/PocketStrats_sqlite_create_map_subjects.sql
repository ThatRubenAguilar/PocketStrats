--
-- File generated with SQLiteStudio v3.1.1 on Wed Oct 4 12:53:17 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSubjects
DROP TABLE IF EXISTS MapSubjects;

CREATE TABLE MapSubjects (
    MapSubjectId         INTEGER PRIMARY KEY AUTOINCREMENT,
    MapId                INTEGER REFERENCES Maps (MapId),
    SpawnSideId          INTEGER,
    SpawnSideDescription VARCHAR,
    SegmentId            INTEGER REFERENCES MapSegments (SegmentId),
    MapSubjectPrecedence INTEGER DEFAULT (1),
    MapTipDescriptionId  INTEGER REFERENCES MapTipDescriptions (MapTipDescriptionId) 
);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
