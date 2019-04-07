DROP TABLE SchoopyAdmin;
DROP TABLE Lesson;
DROP TABLE TeacherSpecialization;
DROP TABLE Subject;
DROP TABLE PrivateFile;
DROP TABLE PublicFile;
DROP TABLE ChatMessage;
DROP TABLE Teacher;
DROP TABLE Student;
DROP TABLE Room;

DROP SEQUENCE seqMessage;
DROP SEQUENCE seqPublicFile;
DROP SEQUENCE seqPrivateFile;
DROP SEQUENCE seqSubject;
CREATE TABLE Room(
  roomNr varchar2(50) primary key,
  roomDescription varchar2(50),
  department VARCHAR2(50),
  roomCoordinates SDO_GEOMETRY;
  CONSTRAINT check_room CHECK (department is not null and (department = 'NONE' or department = 'MEDIATECHNOLOGY' or department = 'NETWORKTECHNOLOGY' or department = 'CIVILENGINEERING' or department = 'IT' or department = 'BUILDINGCONSTRUCTION'))
);

INSERT INTO user_sdo_geom_metadata
(	TABLE_NAME,
	COLUMN_NAME,
	DIMINFO,
	SRID
)
VALUES
(	'Room',
	'roomCoordinates',
	SDO_DIM_ARRAY( -- 20X20 grid
		SDO_DIM_ELEMENT('X', 0, 1000, 0.5),
		SDO_DIM_ELEMENT('Y', 0, 1000, 0.5)
	),
	NULL -- SRID
);

drop index room_idx;

CREATE INDEX room_idx
	ON Room(roomCoordinates)
	INDEXTYPE IS MDSYS.SPATIAL_INDEX;



CREATE TABLE Student(
  username varchar2(50) primary key,
  password varchar2(50),
  firstName varchar2(50), 
  lastName varchar2(50),
  schoolemail varchar2(50) unique,
  visitedClass varchar2(50),
  CONSTRAINT fk_room1 FOREIGN KEY(visitedClass) REFERENCES Room(roomNr)

);

CREATE TABLE Teacher(
  username varchar2(50) primary key,
  password varchar2(50),
  firstName varchar2(50), 
  lastName varchar2(50),
  schoolemail varchar2(50) unique

);

CREATE SEQUENCE seqMessage START WITH 1 INCREMENT BY 1;
CREATE TABLE ChatMessage(
  messageId INTEGER primary key,
  classNr varchar2(50),
  sender varchar2(50),
  message varchar2(50),
  sendDate timestamp,
  CONSTRAINT fk_room2 FOREIGN KEY(classNr) REFERENCES Room(roomNr),
  CONSTRAINT fk_student1 FOREIGN KEY(sender) REFERENCES Student(username)
  
);

CREATE SEQUENCE seqPublicFile START WITH 1 INCREMENT BY 1;
CREATE TABLE PublicFile(
  fileId INTEGER PRIMARY KEY,
  fileName varchar2(50),
  fileContent blob,
  publishDate date,
  publisherTeacher varchar2(50),
  CONSTRAINT fk_teacher1 FOREIGN KEY(publisherTeacher) REFERENCES Teacher(username)
);

CREATE SEQUENCE seqPrivateFile START WITH 1 INCREMENT BY 1;
CREATE TABLE PrivateFile(
  fileId INTEGER PRIMARY KEY,
  fileName varchar2(50),
  fileContent blob,
  publishDate date,
  publisherTeacher varchar2(50),
  publisherStudent varchar2(50),
  folderRoom varchar2(50),
  CONSTRAINT fk_teacher2 FOREIGN KEY(publisherTeacher) REFERENCES Teacher(username),
  CONSTRAINT fk_student2 FOREIGN KEY(publisherStudent) REFERENCES Student(username),
  CONSTRAINT fk_room3 FOREIGN KEY(folderRoom) REFERENCES Room(roomNr),
  CONSTRAINT check_publisher CHECK ((publisherTeacher is not null and publisherStudent is null) or (publisherTeacher is null and publisherStudent is not null))
);

CREATE SEQUENCE seqSubject START WITH 1 INCREMENT BY 1;
CREATE TABLE Subject(
   subjectId integer primary key,
   subjectName varchar2(50) unique,
   subjectShortcut varchar2(50) unique
);

CREATE TABLE TeacherSpecialization(
  teacherUN varchar2(50),
  subjectId integer,
  CONSTRAINT pk_ts PRIMARY KEY (teacherUN,subjectId),
  CONSTRAINT fk_teacher3 FOREIGN KEY(teacherUN) REFERENCES Teacher(username),
  CONSTRAINT fk_subject1 FOREIGN KEY(subjectId) REFERENCES Subject(subjectId)
  
);

CREATE TABLE Lesson(
    schoolRoom varchar2(50),
    teachingRoom varchar2(50),
    teacherUN varchar2(50),
    teachingSubject integer,
    weekDay varchar2(50),
    schoolHour integer,
    CONSTRAINT pk_l PRIMARY KEY (schoolRoom,schoolHour,weekDay), 
    CONSTRAINT fk_room4 FOREIGN KEY(schoolRoom) REFERENCES Room(roomNr),
    CONSTRAINT fk_room5 FOREIGN KEY(teachingRoom) REFERENCES Room(roomNr),
    CONSTRAINT fk_fs FOREIGN KEY(teacherUN,teachingSubject) REFERENCES TeacherSpecialization(teacherUN,subjectId),
    CONSTRAINT check_weekDay CHECK (weekDay is not null and (weekDay = 'MONDAY' or weekDay = 'TUESDAY' or weekDay = 'WEDNESDAY' or weekDay = 'THURSDAY' or weekDay = 'FRIDAY')),
    CONSTRAINT check_schoolHour CHECK (schoolHour is not null and (schoolHour >= 1 and  schoolHour <= 16)),
    CONSTRAINT UC_Teacher1 UNIQUE (teacherUN,schoolHour,weekDay),
    CONSTRAINT UC_Room UNIQUE(teachingRoom,schoolHour,weekDay)
);

CREATE TABLE SchoopyAdmin(
  username varchar2(50) primary key,
  password varchar2(50)
);

COMMIT;
