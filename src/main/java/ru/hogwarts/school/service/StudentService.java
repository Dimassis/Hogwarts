package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface StudentService {

     Map<Long, Student> getStudents();
    Student getStudent(long id);
    public Map<Long, Student> getStudentsByAge(int age);
    public Student addStudent(Student student);
    public Student updateStudent(long id, Student student);
    public Student deleteStudent(long id);
}
