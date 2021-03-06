CREATE SEQUENCE SAMPLE_SEQ AS INTEGER START WITH 1 
CREATE TABLE DUAL(DUMMY VARCHAR)
CREATE TABLE CONF_EVENT(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,NAME VARCHAR(255) NOT NULL,YEAR INTEGER NOT NULL,LOCATION VARCHAR(255) NOT NULL)
CREATE TABLE CONF_SPONSOR(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,NAME VARCHAR(255) NOT NULL,AMOUNT_PAYED INTEGER,EVENT_ID INTEGER NOT NULL,CONSTRAINT FK_SPONSOR_EVENT FOREIGN KEY(EVENT_ID) REFERENCES CONF_EVENT(ID))
CREATE TABLE CONF_ATTENDEE(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,FIRST_NAME VARCHAR(80) NOT NULL,LAST_NAME VARCHAR(80) NOT NULL,AGE INTEGER,COMPANY VARCHAR(80),EVENT_ID INTEGER NOT NULL,CONSTRAINT FKATTENDEEEVENT FOREIGN KEY(EVENT_ID) REFERENCES CONF_EVENT(ID))
CREATE TABLE CONF_PRESENTATION(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,NAME VARCHAR(80) NOT NULL,DESCRIPTION VARCHAR(80),ROOM VARCHAR(80) NOT NULL,START_TIME TIMESTAMP,END_TIME TIMESTAMP,EVENT_ID INTEGER NOT NULL,CONSTRAINT FKPRESENTATIONEEVENT FOREIGN KEY(EVENT_ID) REFERENCES CONF_EVENT(ID))
CREATE TABLE CONF_ATTENDEE_COMMENT(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,ATTENDEE_ID INTEGER NOT NULL,PRESENTATION_ID INTEGER NOT NULL,COMMENT VARCHAR(200) NOT NULL,CONSTRAINT FKCOMMENTATTENDEE FOREIGN KEY(ATTENDEE_ID) REFERENCES CONF_ATTENDEE(ID),CONSTRAINT FKCOMMENTPRESENTATION FOREIGN KEY(PRESENTATION_ID) REFERENCES CONF_PRESENTATION(ID))
GRANT ALL ON CLASS "org.hsql.Library" TO PUBLIC
CREATE USER SA PASSWORD "" ADMIN
SET WRITE_DELAY 60
INSERT INTO DUAL VALUES(NULL)
SET TABLE DUAL READONLY TRUE
