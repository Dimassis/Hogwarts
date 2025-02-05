-- liquibase formatted sql

-- changeset Dmitry:1
CREATE
    INDEX student_name_idx
    ON students(name);

-- changeset Dmitry:2
CREATE
    INDEX faculty_name_color_idx
    ON faculties(name, color);

