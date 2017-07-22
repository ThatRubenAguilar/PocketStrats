--
-- File generated with SQLiteStudio v3.1.1 on Fri Jul 7 20:19:22 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSegments
DROP TABLE IF EXISTS MapSegments;

CREATE TABLE MapSegments (
    SegmentId   INTEGER PRIMARY KEY AUTOINCREMENT,
    MapId       INTEGER REFERENCES Maps (MapId),
    SegmentName VARCHAR NOT NULL ON CONFLICT ROLLBACK
);

INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (1, 1, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (2, 1, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (3, 2, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (4, 2, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (5, 3, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (6, 3, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (7, 4, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (8, 4, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (9, 5, 'Ruins');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (10, 5, 'Lighthouse');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (11, 5, 'Well');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (12, 6, 'Shrine');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (13, 6, 'Sanctum');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (14, 6, 'Village');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (15, 7, 'Night Market');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (16, 7, 'Garden');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (17, 7, 'Control Center');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (18, 8, 'University');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (19, 8, 'City Center');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (20, 8, 'Garden');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (21, 9, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (22, 9, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (23, 9, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (24, 10, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (25, 10, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (26, 10, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (27, 11, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (28, 11, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (29, 11, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (30, 12, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (31, 12, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (32, 12, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (33, 13, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (34, 13, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (35, 13, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (36, 14, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (37, 14, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (38, 14, 'Last');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (39, 15, 'First');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (40, 15, 'Second');
INSERT INTO MapSegments (SegmentId, MapId, SegmentName) VALUES (41, 15, 'Last');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
