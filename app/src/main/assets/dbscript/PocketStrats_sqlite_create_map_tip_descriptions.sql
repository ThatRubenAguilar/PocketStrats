--
-- File generated with SQLiteStudio v3.1.1 on Wed Oct 4 12:50:59 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapTipDescriptions
DROP TABLE IF EXISTS MapTipDescriptions;

CREATE TABLE MapTipDescriptions (
    MapTipDescriptionId   INTEGER PRIMARY KEY,
    MapTipDescriptionHash INTEGER,
    MapTipDescription     TEXT
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
