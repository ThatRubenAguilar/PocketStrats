--
-- File generated with SQLiteStudio v3.1.1 on Fri Jul 7 20:19:00 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Maps
DROP TABLE IF EXISTS Maps;

CREATE TABLE Maps (
    MapId        INTEGER PRIMARY KEY AUTOINCREMENT,
    MapName      VARCHAR NOT NULL ON CONFLICT ROLLBACK,
    MapNameShort VARCHAR,
    MapTypeId    INTEGER NOT NULL ON CONFLICT ROLLBACK,
    MapType      VARCHAR NOT NULL ON CONFLICT ROLLBACK,
    MapTypeShort VARCHAR
);

INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (1, 'Temple of Anubis', 'Anubis', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (2, 'Horizon Lunar Colony', 'Horizon', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (3, 'Hanamura', 'Hanamura', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (4, 'Volskaya Industries', 'Volskaya', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (5, 'Illios', 'Illios', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (6, 'Nepal', 'Nepal', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (7, 'Lijiang Tower', 'Lijiang', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (8, 'Oasis', 'Oasis', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (9, 'Dorado', 'Dorado', 3, 'Escort', 'Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (10, 'Watchpoint: Gibralter', 'Gibralter', 3, 'Escort', 'Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (11, 'Route: 66', 'Route: 66', 3, 'Escort', 'Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (12, 'Numbani', 'Numbani', 4, 'Hybrid Assault/Escort', 'CP/Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (13, 'Hollywood', 'Hollywood', 4, 'Hybrid Assault/Escort', 'CP/Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (14, 'King''s Row', 'King''s Row', 4, 'Hybrid Assault/Escort', 'CP/Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapTypeId, MapType, MapTypeShort) VALUES (15, 'Eichenwalde', 'Eichenwalde', 4, 'Hybrid Assault/Escort', 'CP/Payload');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
