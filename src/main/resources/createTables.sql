DROP TABLE IF EXISTS classRoom, faculties, courses, groups, students, groupsLessons, lessons, subjects, employees, positions;
DROP TABLE IF EXISTS groupsLessons;

CREATE TABLE classroom
(
    id          SERIAL PRIMARY KEY,
    name        CHARACTER(25) NOT NULL UNIQUE,
    description CHARACTER(100)
);

CREATE TABLE faculties
(
    id          SERIAL PRIMARY KEY,
    name        CHARACTER(35)  NOT NULL,
    description CHARACTER(100) NOT NULL
);

CREATE TABLE courses
(
    id            SERIAL PRIMARY KEY,
    number_course int NOT NULL,
    faculty_id    int NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculties (id) ON DELETE CASCADE
);

CREATE TABLE groups
(
    id           SERIAL PRIMARY KEY,
    number_group int NOT NULL,
    course_id    int NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE
);

CREATE TABLE students
(
    id        SERIAL PRIMARY KEY,
    name      CHARACTER(25) NOT NULL,
    last_name CHARACTER(35) NOT NULL,
    group_id  int           NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);

CREATE TABLE positions
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER(35) NOT NULL UNIQUE
);

CREATE TABLE employees
(
    id          SERIAL PRIMARY KEY,
    name        CHARACTER(25) NOT NULL,
    last_name   CHARACTER(35) NOT NULL,
    position_id int           NOT NULL,
    salary      int           NOT NULL,
    FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE CASCADE
);

CREATE TABLE subjects
(
    id             SERIAL PRIMARY KEY,
    name           CHARACTER(35) NOT NULL,
    description    CHARACTER(55) NOT NULL,
    amount_lessons int           NOT NULL,
    employee_id    int,
    FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE SET NULL
);

CREATE TABLE lessons
(
    id           SERIAL PRIMARY KEY,
    subject_id   int       NOT NULL,
    date_time    TIMESTAMP NOT NULL,
    duration     int       NOT NULL,
    classroom_id int       NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subjects (id) ON DELETE SET NULL,
    FOREIGN KEY (classroom_id) REFERENCES classroom (id) ON DELETE SET NULL
);

CREATE TABLE groups_lessons
(
    id_group  int NOT NULL,
    id_lesson int NOT NULL,
    PRIMARY KEY (id_group, id_lesson),
    FOREIGN KEY (id_group) REFERENCES groups (id) ON DELETE CASCADE,
    FOREIGN KEY (id_lesson) REFERENCES lessons (id) ON DELETE CASCADE
);
