--
-- File generated with SQLiteStudio v3.1.1 on Tue Sep 12 07:41:42 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSubjects
CREATE TABLE MapSubjects (MapSubjectId INTEGER PRIMARY KEY AUTOINCREMENT, MapId INTEGER REFERENCES Maps (MapId), SpawnSideId INTEGER, SpawnSideDescription VARCHAR, MapTipId INTEGER REFERENCES MapTips (MapTipId), SegmentId INTEGER REFERENCES MapSegments (SegmentId));

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
