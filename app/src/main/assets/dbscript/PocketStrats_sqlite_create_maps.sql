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
    MapFileCompatName VARCHAR,
    MapTypeId    INTEGER NOT NULL ON CONFLICT ROLLBACK,
    MapType      VARCHAR NOT NULL ON CONFLICT ROLLBACK,
    MapTypeShort VARCHAR
);

INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (1, 'Temple of Anubis', 'Anubis', 'anubis', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (2, 'Horizon Lunar Colony', 'Horizon', 'horizon', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (3, 'Hanamura', 'Hanamura', 'hanamura', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (4, 'Volskaya Industries', 'Volskaya', 'volskaya', 1, 'Assault', '2CP');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (5, 'Illios', 'Illios', 'illios', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (6, 'Nepal', 'Nepal', 'nepal', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (7, 'Lijiang Tower', 'Lijiang', 'lijiang', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (8, 'Oasis', 'Oasis', 'oasis', 2, 'Control', 'KoTH');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (9, 'Dorado', 'Dorado', 'dorado', 3, 'Escort', 'Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (10, 'Watchpoint: Gibraltar', 'Gibraltar', 'gibraltar', 3, 'Escort', 'Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (11, 'Route: 66', 'Route: 66', 'route66', 3, 'Escort', 'Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (12, 'Numbani', 'Numbani', 'numbani', 4, 'Hybrid Assault/Escort', 'CP/Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (13, 'Hollywood', 'Hollywood', 'hollywood', 4, 'Hybrid Assault/Escort', 'CP/Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (14, 'King''s Row', 'King''s Row', 'kings_row', 4, 'Hybrid Assault/Escort', 'CP/Payload');
INSERT INTO Maps (MapId, MapName, MapNameShort, MapFileCompatName, MapTypeId, MapType, MapTypeShort) VALUES (15, 'Eichenwalde', 'Eichenwalde', 'eichenwalde', 4, 'Hybrid Assault/Escort', 'CP/Payload');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
