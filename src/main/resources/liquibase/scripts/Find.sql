DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'student_name_idx') THEN
            CREATE INDEX student_name_idx ON students(name);
        END IF;
    END
$$;