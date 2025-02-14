package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getStudents();

    Optional<Student> getStudent(Long id);

    List<Student> getStudentsByAge(int age);

    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Collection<Student> findStudentByName(String name);

    Collection<Student> findByPartName(String part);

    Collection<Student> findAllByBetweenAge(Integer age1, Integer age2);

    Integer getCountStudents();

    Double avgAgeStudents();

    List<Student> limitStudents();

    List<Student> sortStudentsByAlphabet();
}
