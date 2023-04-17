/*Initialisation*/

DROP DATABASE prevent_diabetes;
CREATE DATABASE prevent_diabetes;
USE prevent_diabetes;

CREATE TABLE patient
(
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  last_name VARCHAR(30),
  first_name VARCHAR(30),
  dob VARCHAR(30),
  sex VARCHAR(1),
  address VARCHAR(255),
  phone VARCHAR(128)
 );