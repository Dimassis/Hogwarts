package ru.hogwarts.school.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        logger.info("Get all students");
        return studentRepository.findAll();
    }

    @Override
    public Integer getCountStudents() {
        logger.info("Get count students");
        return studentRepository.countStudents();
    }



    @Override
    public List<Student> limitStudents() {
        logger.info("Get limit students");
        return studentRepository.limitStudents();
    }

    @Override
    public Optional<Student> getStudent(Long studentId) {
        try {
            return studentRepository.findById(studentId);
        }catch (EntityNotFoundException e) {
            logger.warn("Student not found with ID: {}", studentId);
             throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        logger.info("Get student by age: {}", age);
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Add student: {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        logger.info("Update student: {}", student);
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isEmpty()) {
            logger.warn("Student not found with ID: {}", id);
            return null;
        }

        Student studentToUpdate = existingStudent.get();
        studentToUpdate.setName(student.getName());
        studentToUpdate.setAge(student.getAge());

        return studentRepository.save(studentToUpdate);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Delete student: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentByName(String name) {
        logger.info("Find student by name:");
        return studentRepository.findStudentsByNameIgnoreCase(name);
    }

    public Collection<Student> findByPartName(String part) {
        logger.info("Find student by part name:");
        return studentRepository.findAllByNameContainsIgnoreCase(part);
    }

    public Collection<Student> findAllByBetweenAge(Integer age1, Integer age2) {
        logger.info("Find student by between age:");
        return studentRepository.findAllByAgeBetween(age1, age2);
    }

    @Override
    public List<Student> sortStudentsByAlphabet() {
        logger.info("Sorting student by alphabet");
        return studentRepository.findAll()
                .parallelStream()
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    @Override
    public Double avgAgeStudents() {
        logger.info("Get avg age students");
        return studentRepository.findAll()
                .parallelStream()
                .collect(Collectors.averagingDouble(Student::getAge));
    }
}