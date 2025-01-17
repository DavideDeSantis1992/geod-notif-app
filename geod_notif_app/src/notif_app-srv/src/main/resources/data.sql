DROP SEQUENCE IF EXISTS PRATICHE_SEQUENCE;
DROP SEQUENCE IF EXISTS STORICO_SEQUENCE;
DROP TABLE IF EXISTS PRATICHE;
DROP TABLE IF EXISTS STORICO;

CREATE SEQUENCE PRATICHE_SEQUENCE
  START WITH 1
  INCREMENT BY 1;

CREATE SEQUENCE STORICO_SEQUENCE
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE PRATICHE (
    id INT NOT NULL PRIMARY KEY,
    pratica_name VARCHAR(250) NOT NULL,
    pratica_desc VARCHAR(250) NOT NULL,
    pratica_create_date DATE NOT NULL,
    pratica_creator VARCHAR(250) NOT NULL,
    pratica_modify_date DATE,
    pratica_modifier VARCHAR(250)
);

CREATE TABLE STORICO (
   id INT NOT NULL PRIMARY KEY,
   log_pratica_id INT NOT NULL,
   log_desc VARCHAR(250) NOT NULL,
   log_date DATE NOT NULL,
   username VARCHAR(250) NOT NULL
)