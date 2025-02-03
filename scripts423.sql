-- студенты с аватарками
SELECT
    s.name AS student_name,
    s.age AS student_age
FROM students s JOIN Avatar a ON s.id = a.student_id;

-- информация о студентах и факультетах
SELECT
    s.name AS student_name,
    s.age AS student_age,
    f.name AS faculty_name
FROM students s JOIN faculties f ON s.faculty_id = f.id
WHERE f.name = 'Greefindor';
