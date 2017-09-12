--
-- File generated with SQLiteStudio v3.1.1 on Tue Sep 12 07:42:08 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapTypeTips
DROP TABLE IF EXISTS MapTypeTips;

CREATE TABLE MapTypeTips (
    MapTypeTipId INTEGER PRIMARY KEY,
    MapTypeId    INTEGER REFERENCES Maps (MapTypeId),
    MapTipId     INTEGER REFERENCES MapTips (MapTipId) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
