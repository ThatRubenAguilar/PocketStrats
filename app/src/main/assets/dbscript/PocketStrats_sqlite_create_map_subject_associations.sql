--
-- File generated with SQLiteStudio v3.1.1 on Sun Oct 1 15:29:26 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: MapSubjectAssociations
DROP TABLE IF EXISTS MapSubjectAssociations;

CREATE TABLE MapSubjectAssociations (
    MapSubjectId           INTEGER REFERENCES MapSubjects (MapSubjectId),
    AssociatedMapSubjectId INTEGER REFERENCES MapSubjects (MapSubjectId) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
