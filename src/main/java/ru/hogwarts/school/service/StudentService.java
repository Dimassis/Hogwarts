package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();

    Student getStudent(long id);

    List<Student> getStudentsByAge(int age);

    Student addStudent(Student student);

    Student updateStudent(long id, Student student);

    void deleteStudent(long id);
}
