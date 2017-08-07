--
-- File generated with SQLiteStudio v3.1.1 on Sun Aug 6 18:53:18 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapLocations
DROP TABLE IF EXISTS MapLocations;

CREATE TABLE MapLocations (
    LocationId          INTEGER PRIMARY KEY AUTOINCREMENT,
    LocationDescription VARCHAR,
    LocationTypeId      INTEGER NOT NULL ON CONFLICT ROLLBACK
                                DEFAULT (0),
    LocationImageName   VARCHAR,
    SegmentId           INTEGER REFERENCES MapSegments (SegmentId),
    MapId               INTEGER REFERENCES Maps (MapId) 
);

INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (1, 'Forward Choke', 1, 'Anubis_First_Choke_1', 1, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (2, 'Point', 5, 'Anubis_First_Point_1', 1, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (3, 'Forward Choke', 1, 'Anubis_Last_Choke_1', 2, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (4, 'Point', 6, 'Anubis_Last_Point_1', 2, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (5, 'Forward Choke', 1, 'Horizon_First_Choke_1', 3, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (6, 'Point', 5, 'Horizon_First_Point_1', 3, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (7, 'Forward High Ground', 4, 'Horizon_Last_High_Ground_1', 4, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (8, 'Point', 6, 'Horizon_Last_Point_1', 4, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (9, 'Forward Choke', 1, 'Hanamura_First_Choke_1', 5, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (10, 'Point', 5, 'Hanamura_First_Point_1', 5, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (11, 'Forward Choke', 1, 'Hanamura_Last_Choke_1', 6, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (12, 'High Ground', 4, 'Hanamura_Last_High_Ground_1', 6, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (13, 'Point', 6, 'Hanamura_Last_Point_1', 6, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (14, 'Forward Choke', 1, 'Volskaya_First_Choke_1', 7, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (15, 'Point', 5, 'Volskaya_First_Point_1', 7, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (16, 'Forward Choke', 1, 'Volskaya_Last_Choke_1', 8, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (17, 'Point', 6, 'Volskaya_Last_Point_1', 8, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (18, 'Point', 5, 'Ruins_Point_1', 9, 5);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (19, 'Point', 5, 'Lighthouse_Point_1', 10, 5);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (20, 'Point', 5, 'Well_Point_1', 11, 5);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (21, 'Point', 5, 'Shrine_Point_1', 12, 6);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (22, 'Point', 5, 'Sanctum_Point_1', 13, 6);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (23, 'Point', 5, 'Village_Point_1', 14, 6);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (24, 'Point', 5, 'Night_Market_Point_1', 15, 7);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (25, 'Point', 5, 'L_Garden_Point_1', 16, 7);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (26, 'Point', 5, 'Control_Center_Point_1', 17, 7);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (27, 'Point', 5, 'University_Point_1', 18, 8);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (28, 'Point', 5, 'City_Center_Point_1', 19, 8);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (29, 'Point', 5, 'O_Garden_Point_1', 20, 8);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (30, 'Forward Choke', 1, 'Dorado_First_Choke_1', 21, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (31, 'Checkpoint', 7, 'Dorado_First_Checkpoint_1', 21, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (32, 'Forward Corner', 3, 'Dorado_Second_Corner_1', 22, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (33, 'Checkpoint', 7, 'Dorado_Second_Checkpoint_1', 22, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (34, 'Forward Corner', 3, 'Dorado_Last_Corner_1', 23, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (35, 'Checkpoint', 8, 'Dorado_Last_Checkpoint_1', 23, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (36, 'Forward Choke', 1, 'Gibralter_First_Choke_1', 24, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (37, 'Checkpoint', 7, 'Gibralter_First_Checkpoint_1', 24, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (38, 'Forward Corner', 3, 'Gibralter_Second_Corner_1', 25, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (39, 'Checkpoint', 7, 'Gibralter_Second_Checkpoint_1', 25, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (40, 'Forward Corner', 3, 'Gibralter_Last_Corner_1', 26, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (41, 'Checkpoint', 8, 'Gibralter_Last_Checkpoint_1', 26, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (42, 'Forward Corner', 3, 'Route66_First_Corner_1', 27, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (43, 'Checkpoint', 7, 'Route66_First_Checkpoint_1', 27, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (44, 'Forward Corner', 3, 'Route66_Second_Corner_1', 28, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (45, 'Checkpoint', 7, 'Route66_Second_Checkpoint_1', 28, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (46, 'Forward Corner', 3, 'Route66_Last_Corner_1', 29, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (47, 'Checkpoint', 8, 'Route66_Last_Checkpoint_1', 29, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (48, 'Point', 5, 'Numbani_First_Point_1', 30, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (49, 'Forward Corner', 3, 'Numbani_Second_Corner_1', 31, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (50, 'Checkpoint', 7, 'Numbani_Second_Checkpoint_1', 31, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (51, 'Forward Corner', 3, 'Numbani_Last_Corner_1', 32, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (52, 'Checkpoint', 8, 'Numbani_Last_Checkpoint_1', 32, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (53, 'Forward Choke', 1, 'Hollywood_First_Choke_1', 33, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (54, 'Point', 5, 'Hollywood_First_Point_1', 33, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (55, 'Forward Choke', 1, 'Hollywood_Second_Choke_1', 34, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (56, 'Mid Streets', 2, 'Hollywood_Second_Streets_1', 34, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (57, 'Checkpoint', 7, 'Hollywood_Second_Checkpoint_1', 34, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (58, 'Forward Corner', 3, 'Hollywood_Last_Corner_1', 35, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (59, 'Checkpoint', 8, 'Hollywood_Last_Checkpoint_1', 35, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (60, 'Forward Choke', 1, 'Kings_Row_First_Choke_1', 36, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (61, 'Point', 5, 'Kings_Row_First_Point_1', 36, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (62, 'Forward Choke', 1, 'Kings_Row_Second_Choke_1', 37, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (63, 'Forward Corner', 3, 'Kings_Row_Second_Corner_1', 37, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (64, 'Checkpoint', 7, 'Kings_Row_Second_Checkpoint_1', 37, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (65, 'Forward Corner', 3, 'Kings_Row_Last_Corner_1', 38, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (66, 'Checkpoint', 8, 'Kings_Row_Last_Checkpoint_1', 38, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (67, 'Forward Choke', 1, 'Eichenwalde_First_Choke_1', 39, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (68, 'Point', 5, 'Eichenwalde_First_Point_1', 39, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (69, 'Forward Corner', 3, 'Eichenwalde_Second_Corner_1', 40, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (70, 'Mid Choke', 1, 'Eichenwalde_Second_Choke_1', 40, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (71, 'Checkpoint', 7, 'Eichenwalde_Second_Checkpoint_1', 40, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (72, 'Forward Choke', 1, 'Eichenwalde_Last_Choke_1', 41, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (73, 'Forward Corner', 3, 'Eichenwalde_Last_Corner_1', 41, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationTypeId, LocationImageName, SegmentId, MapId) VALUES (74, 'Checkpoint', 8, 'Eichenwalde_Last_Checkpoint_1', 41, 15);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
