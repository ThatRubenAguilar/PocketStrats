--
-- File generated with SQLiteStudio v3.1.1 on Mon Sep 11 12:13:56 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapLocationImages
DROP TABLE IF EXISTS MapLocationImages;

CREATE TABLE MapLocationImages (
    ImageId    INTEGER PRIMARY KEY,
    LocationId INTEGER REFERENCES MapLocations (LocationId),
    ImageBlob  BLOB,
    ImageName  VARCHAR NOT NULL
);

INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (1, 1, NULL, 'Anubis_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (2, 2, NULL, 'Anubis_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (3, 3, NULL, 'Anubis_Last_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (4, 4, NULL, 'Anubis_Last_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (5, 5, NULL, 'Horizon_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (6, 6, NULL, 'Horizon_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (7, 7, NULL, 'Horizon_Last_High_Ground_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (8, 8, NULL, 'Horizon_Last_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (9, 9, NULL, 'Hanamura_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (10, 10, NULL, 'Hanamura_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (11, 11, NULL, 'Hanamura_Last_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (12, 12, NULL, 'Hanamura_Last_High_Ground_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (13, 13, NULL, 'Hanamura_Last_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (14, 14, NULL, 'Volskaya_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (15, 15, NULL, 'Volskaya_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (16, 16, NULL, 'Volskaya_Last_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (17, 17, NULL, 'Volskaya_Last_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (18, 18, NULL, 'Ruins_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (19, 19, NULL, 'Lighthouse_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (20, 20, NULL, 'Well_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (21, 21, NULL, 'Shrine_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (22, 22, NULL, 'Sanctum_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (23, 23, NULL, 'Village_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (24, 24, NULL, 'Night_Market_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (25, 25, NULL, 'L_Garden_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (26, 26, NULL, 'Control_Center_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (27, 27, NULL, 'University_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (28, 28, NULL, 'City_Center_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (29, 29, NULL, 'O_Garden_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (30, 30, NULL, 'Dorado_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (31, 31, NULL, 'Dorado_First_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (32, 32, NULL, 'Dorado_Second_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (33, 33, NULL, 'Dorado_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (34, 34, NULL, 'Dorado_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (35, 35, NULL, 'Dorado_Last_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (36, 36, NULL, 'Gibralter_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (37, 37, NULL, 'Gibralter_First_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (38, 38, NULL, 'Gibralter_Second_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (39, 39, NULL, 'Gibralter_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (40, 40, NULL, 'Gibralter_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (41, 41, NULL, 'Gibralter_Last_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (42, 42, NULL, 'Route66_First_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (43, 43, NULL, 'Route66_First_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (44, 44, NULL, 'Route66_Second_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (45, 45, NULL, 'Route66_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (46, 46, NULL, 'Route66_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (47, 47, NULL, 'Route66_Last_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (48, 48, NULL, 'Numbani_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (49, 49, NULL, 'Numbani_Second_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (50, 50, NULL, 'Numbani_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (51, 51, NULL, 'Numbani_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (52, 52, NULL, 'Numbani_Last_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (53, 53, NULL, 'Hollywood_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (54, 54, NULL, 'Hollywood_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (55, 55, NULL, 'Hollywood_Second_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (56, 56, NULL, 'Hollywood_Second_Streets_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (57, 57, NULL, 'Hollywood_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (58, 58, NULL, 'Hollywood_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (59, 59, NULL, 'Hollywood_Last_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (60, 60, NULL, 'Kings_Row_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (61, 61, NULL, 'Kings_Row_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (62, 62, NULL, 'Kings_Row_Second_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (63, 63, NULL, 'Kings_Row_Second_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (64, 64, NULL, 'Kings_Row_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (65, 65, NULL, 'Kings_Row_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (66, 66, NULL, 'Kings_Row_Last_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (67, 67, NULL, 'Eichenwalde_First_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (68, 68, NULL, 'Eichenwalde_First_Point_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (69, 69, NULL, 'Eichenwalde_Second_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (70, 70, NULL, 'Eichenwalde_Second_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (71, 71, NULL, 'Eichenwalde_Second_Checkpoint_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (72, 72, NULL, 'Eichenwalde_Last_Choke_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (73, 73, NULL, 'Eichenwalde_Last_Corner_1');
INSERT INTO MapLocationImages (ImageId, LocationId, ImageBlob, ImageName) VALUES (74, 74, NULL, 'Eichenwalde_Last_Checkpoint_1');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
