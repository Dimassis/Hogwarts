package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(studentRepository.findAll());
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(long id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isEmpty()) {
            return null;
        }

        Student studentToUpdate = existingStudent.get();
        studentToUpdate.setName(student.getName());
        studentToUpdate.setAge(student.getAge());

        return studentRepository.save(studentToUpdate);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
