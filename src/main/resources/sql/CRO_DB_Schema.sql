CREATE TABLE IF NOT EXISTS COMPANY (
  ID           INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  NAME         VARCHAR(255)                   NOT NULL,
  ADDRESS      VARCHAR(255)                   NOT NULL,
  CITY         VARCHAR(255)                   NOT NULL,
  COUNTRY      VARCHAR(255)                   NOT NULL,
  EMAIL        VARCHAR(255) DEFAULT NULL,
  PHONE_NUMBER VARCHAR(255) DEFAULT NULL,
);
CREATE TABLE IF NOT EXISTS STAKEHOLDER (
  ID         INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  FIRST_NAME VARCHAR(255)                   NOT NULL,
  LAST_NAME  VARCHAR(255)                   NOT NULL,
);

CREATE TABLE IF NOT EXISTS BENEFICIAL_OWNER (
  ID             INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  COMPANY_ID     INT                            NOT NULL,
  STAKEHOLDER_ID INT                            NOT NULL,

  FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID),
  FOREIGN KEY (STAKEHOLDER_ID) REFERENCES STAKEHOLDER (ID)
);

-- User and Security tables.

CREATE TABLE IF NOT EXISTS ROLE (
  ID          INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  ROLE_NAME   VARCHAR(255)                   NOT NULL,
  DESCRIPTION VARCHAR(255) DEFAULT NULL
);


CREATE TABLE IF NOT EXISTS USER (
  ID         INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  FIRST_NAME VARCHAR(255)                   NOT NULL,
  LAST_NAME  VARCHAR(255)                   NOT NULL,
  PASSWORD   VARCHAR(255)                   NOT NULL,
  USERNAME   VARCHAR(255)                   NOT NULL
);


CREATE TABLE IF NOT EXISTS USER_ACCESS (
  ID      INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  USER_ID BIGINT(20)                     NOT NULL,
  ROLE_ID BIGINT(20)                     NOT NULL,
  FOREIGN KEY (USER_ID) REFERENCES USER (ID),
  FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID)
);

INSERT INTO ROLE (ROLE_NAME, DESCRIPTION) VALUES ('STANDARD_USER', 'STANDARD USER - HAS NO ADMIN RIGHTS');
INSERT INTO ROLE (ROLE_NAME, DESCRIPTION)
VALUES ('ADMIN_USER', 'ADMIN USER - HAS PERMISSION TO PERFORM ADMIN TASKS');

-- USER
-- NON-ENCRYPTED PASSWORD: jwtpass
INSERT INTO USER (FIRST_NAME, LAST_NAME, PASSWORD, USERNAME)
VALUES ('ghodrat', 'naderi', '{SHA-256}821f498d827d4edad2ed0960408a98edceb661d9f34287ceda2962417881231a', 'gnaderi');
INSERT INTO USER (FIRST_NAME, LAST_NAME, PASSWORD, USERNAME)
VALUES ('admin', 'admin', '{SHA-256}821f498d827d4edad2ed0960408a98edceb661d9f34287ceda2962417881231a', 'admin');


INSERT INTO USER_ACCESS (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ACCESS (USER_ID, ROLE_ID) VALUES (2, 1);
INSERT INTO USER_ACCESS (USER_ID, ROLE_ID) VALUES (2, 2);

