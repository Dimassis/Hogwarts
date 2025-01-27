package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);
    Collection<Student> findStudentsByNameIgnoreCase(String name);

    Collection<Student> findAllByNameContainsIgnoreCase(String part);

    Collection<Student> findByAge(int age);

    Collection<Student> findAllByAgeBetween(Integer min, Integer max);

    @Query(value = "SELECT count (*) FROM students")
    Integer countStudents();

    @Query(value = "SELECT avg(age) FROM students")
    Double avgStudents();

    @Query(value = "SELECT * from students " +
            "ORDER BY id " +
            "DESC LIMIT 5", nativeQuery = true)
    List<Student> limitStudents();
}
