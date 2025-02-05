package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false) Integer ageMin,
                                                              @RequestParam(required = false) Integer ageMax,
                                                              @RequestParam(required = false) String part) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(studentService.findStudentByName(name));
        }

        if (ageMin != null && ageMax != null && ageMin <= ageMax) {
            return ResponseEntity.ok(studentService.findAllByBetweenAge(ageMin, ageMax));
        }

        if (part != null && !part.isBlank()) {
            return ResponseEntity.ok(studentService.findByPartName(part));
        }
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> findById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudent(id);
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("age/{age}")
    public List<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.getStudentsByAge(age);
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        if (student.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Student updatedStudent = studentService.updateStudent(student.getId(), student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public Integer getCountStudents() {
        return studentService.getCountStudents();
    }

    @GetMapping("/avg")
    public Double getAverageStudents() {
        return studentService.avgAgeStudents();
    }

    @GetMapping("/limit")
    public List<Student> getLimitStudents() {
        return studentService.limitStudents();
    }
}
