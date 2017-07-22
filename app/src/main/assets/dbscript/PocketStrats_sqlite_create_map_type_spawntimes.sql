--
-- File generated with SQLiteStudio v3.1.1 on Sat Jul 8 21:13:43 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapTypeSpawnTimes
DROP TABLE IF EXISTS MapTypeSpawnTimes;

CREATE TABLE MapTypeSpawnTimes (
    StatisticId       INTEGER PRIMARY KEY,
    MinSpawnTime      REAL    NOT NULL ON CONFLICT ROLLBACK,
    MaxSpawnTime      REAL    NOT NULL ON CONFLICT ROLLBACK,
    MapTypeId         INTEGER REFERENCES Maps (MapTypeId),
    OvertimeSpawnTime REAL    NOT NULL ON CONFLICT ROLLBACK
);

INSERT INTO MapTypeSpawnTimes (StatisticId, MinSpawnTime, MaxSpawnTime, MapTypeId, OvertimeSpawnTime) VALUES (1, 10, 15, 1, 2);
INSERT INTO MapTypeSpawnTimes (StatisticId, MinSpawnTime, MaxSpawnTime, MapTypeId, OvertimeSpawnTime) VALUES (2, 10, 15, 2, 2);
INSERT INTO MapTypeSpawnTimes (StatisticId, MinSpawnTime, MaxSpawnTime, MapTypeId, OvertimeSpawnTime) VALUES (3, 10, 15, 3, 2);
INSERT INTO MapTypeSpawnTimes (StatisticId, MinSpawnTime, MaxSpawnTime, MapTypeId, OvertimeSpawnTime) VALUES (4, 10, 15, 4, 2);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
