CREATE TABLE IF NOT EXISTS T_USER(
   ID INTEGER (5) AUTO_INCREMENT NOT NULL,
   EMAIL VARCHAR(20) NOT NULL,
   PASSWORD VARCHAR(20) NOT NULL,
   FIRST_NAME VARCHAR(20) NOT NULL,
   LAST_NAME VARCHAR(20) NOT NULL,
   ROLE VARCHAR(20) NOT NULL,
   PRIMARY KEY (ID)
)

INSERT INTO T_USER(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ROLE) VALUES ('admin@admin','admin','admin','admin','ADMIN');
INSERT INTO T_USER(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, ROLE) VALUES ('user@user','user','user','user','USER');