--
-- File generated with SQLiteStudio v3.1.1 on Tue Sep 12 07:41:12 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSpecificTips
DROP TABLE IF EXISTS MapSpecificTips;

CREATE TABLE MapSpecificTips (
    MapSpecificTipId INTEGER PRIMARY KEY,
    MapTipId         INTEGER REFERENCES MapTips (MapTipId) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
