--
-- File generated with SQLiteStudio v3.1.1 on Fri Jul 7 20:19:35 2017
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
    LocationImageName   VARCHAR,
    SegmentId           INTEGER REFERENCES MapSegments (SegmentId),
    MapId               INTEGER REFERENCES Maps (MapId) 
);

INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (1, 'Forward Choke', 'Anubis_First_Choke_1', 1, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (2, 'Point', 'Anubis_First_Point_1', 1, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (3, 'Forward Choke', 'Anubis_Last_Choke_1', 2, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (4, 'Point', 'Anubis_Last_Point_1', 2, 1);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (5, 'Forward Choke', 'Horizon_First_Choke_1', 3, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (6, 'Point', 'Horizon_First_Point_1', 3, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (7, 'Forward High Ground', 'Horizon_Last_High_Ground_1', 4, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (8, 'Point', 'Horizon_Last_Point_1', 4, 2);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (9, 'Forward Choke', 'Hanamura_First_Choke_1', 5, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (10, 'Point', 'Hanamura_First_Point_1', 5, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (11, 'Forward Choke', 'Hanamura_Last_Choke_1', 6, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (12, 'High Ground', 'Hanamura_Last_High_Ground_1', 6, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (13, 'Point', 'Hanamura_Last_Point_1', 6, 3);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (14, 'Forward Choke', 'Volskaya_First_Choke_1', 7, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (15, 'Point', 'Volskaya_First_Point_1', 7, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (16, 'Forward Choke', 'Volskaya_Last_Choke_1', 8, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (17, 'Point', 'Volskaya_Last_Point_1', 8, 4);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (18, 'Point', 'Ruins_Point_1', 9, 5);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (19, 'Point', 'Lighthouse_Point_1', 10, 5);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (20, 'Point', 'Well_Point_1', 11, 5);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (21, 'Point', 'Shrine_Point_1', 12, 6);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (22, 'Point', 'Sanctum_Point_1', 13, 6);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (23, 'Point', 'Village_Point_1', 14, 6);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (24, 'Point', 'Night_Market_Point_1', 15, 7);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (25, 'Point', 'L_Garden_Point_1', 16, 7);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (26, 'Point', 'Control_Center_Point_1', 17, 7);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (27, 'Point', 'University_Point_1', 18, 8);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (28, 'Point', 'City_Center_Point_1', 19, 8);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (29, 'Point', 'O_Garden_Point_1', 20, 8);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (30, 'Forward Choke', 'Dorado_First_Choke_1', 21, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (31, 'Checkpoint', 'Dorado_First_Checkpoint_1', 21, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (32, 'Forward Corner', 'Dorado_Second_Corner_1', 22, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (33, 'Checkpoint', 'Dorado_Second_Checkpoint_1', 22, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (34, 'Forward Corner', 'Dorado_Last_Corner_1', 23, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (35, 'Checkpoint', 'Dorado_Last_Checkpoint_1', 23, 9);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (36, 'Forward Choke', 'Gibralter_First_Choke_1', 24, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (37, 'Checkpoint', 'Gibralter_First_Checkpoint_1', 24, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (38, 'Forward Corner', 'Gibralter_Second_Corner_1', 25, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (39, 'Checkpoint', 'Gibralter_Second_Checkpoint_1', 25, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (40, 'Forward Corner', 'Gibralter_Last_Corner_1', 26, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (41, 'Checkpoint', 'Gibralter_Last_Checkpoint_1', 26, 10);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (42, 'Forward Corner', 'Route66_First_Corner_1', 27, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (43, 'Checkpoint', 'Route66_First_Checkpoint_1', 27, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (44, 'Forward Corner', 'Route66_Second_Corner_1', 28, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (45, 'Checkpoint', 'Route66_Second_Checkpoint_1', 28, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (46, 'Forward Corner', 'Route66_Last_Corner_1', 29, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (47, 'Checkpoint', 'Route66_Last_Checkpoint_1', 29, 11);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (48, 'Point', 'Numbani_First_Point_1', 30, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (49, 'Forward Corner', 'Numbani_Second_Corner_1', 31, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (50, 'Checkpoint', 'Numbani_Second_Checkpoint_1', 31, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (51, 'Forward Corner', 'Numbani_Last_Corner_1', 32, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (52, 'Checkpoint', 'Numbani_Last_Checkpoint_1', 32, 12);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (53, 'Forward Choke', 'Hollywood_First_Choke_1', 33, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (54, 'Point', 'Hollywood_First_Point_1', 33, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (55, 'Forward Choke', 'Hollywood_Second_Choke_1', 34, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (56, 'Mid Streets', 'Hollywood_Second_Streets_1', 34, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (57, 'Checkpoint', 'Hollywood_Second_Checkpoint_1', 34, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (58, 'Forward Corner', 'Hollywood_Last_Corner_1', 35, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (59, 'Checkpoint', 'Hollywood_Last_Checkpoint_1', 35, 13);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (60, 'Forward Choke', 'Kings_Row_First_Choke_1', 36, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (61, 'Point', 'Kings_Row_First_Point_1', 36, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (62, 'Forward Choke', 'Kings_Row_Second_Choke_1', 37, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (63, 'Forward Corner', 'Kings_Row_Second_Corner_1', 37, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (64, 'Checkpoint', 'Kings_Row_Second_Checkpoint_1', 37, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (65, 'Forward Corner', 'Kings_Row_Last_Corner_1', 38, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (66, 'Checkpoint', 'Kings_Row_Last_Checkpoint_1', 38, 14);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (67, 'Forward Choke', 'Eichenwalde_First_Choke_1', 39, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (68, 'Point', 'Eichenwalde_First_Point_1', 39, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (69, 'Forward Corner', 'Eichenwalde_Second_Corner_1', 40, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (70, 'Mid Choke', 'Eichenwalde_Second_Choke_1', 40, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (71, 'Checkpoint', 'Eichenwalde_Second_Checkpoint_1', 40, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (72, 'Forward Choke', 'Eichenwalde_Last_Choke_1', 41, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (73, 'Forward Corner', 'Eichenwalde_Last_Corner_1', 41, 15);
INSERT INTO MapLocations (LocationId, LocationDescription, LocationImageName, SegmentId, MapId) VALUES (74, 'Checkpoint', 'Eichenwalde_Last_Checkpoint_1', 41, 15);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
