ALTER TABLE students
-- Ограничение возраста
    ADD CONSTRAINT chk_age CHECK (age >= 16),
-- Уникальное имя
ADD CONSTRAINT unique_name UNIQUE (name),
ALTER COLUMN name SET NOT NULL,
-- Возраст по умолчанию
ALTER COLUMN age SET DEFAULT 20;


ALTER TABLE faculties
-- Уникальный факультет + цвет
    ADD CONSTRAINT unique_faculty_color UNIQUE (name, color);