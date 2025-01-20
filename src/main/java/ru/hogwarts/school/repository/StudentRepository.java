package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);
    Collection<Student> findStudentsByNameIgnoreCase(String name);

    Collection<Student> findAllByNameContainsIgnoreCase(String part);

    Collection<Student> findByAge(int age);

    Collection<Student> findAllByAgeBetween(Integer min, Integer max);

}
