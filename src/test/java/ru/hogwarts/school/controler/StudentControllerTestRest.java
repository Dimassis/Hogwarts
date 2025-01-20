package ru.hogwarts.school.controler;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull();
    }

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Test Student");
        student.setAge(25);

        String url = "http://localhost:" + port + "/student";

        ResponseEntity<Student> response = restTemplate.postForEntity(url, student, Student.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getName()).isEqualTo(student.getName());
        Assertions.assertThat(response.getBody().getAge()).isEqualTo(student.getAge());
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Assertions.assertThat(studentRepository).isNotNull();
        Assertions
                .assertThat(studentRepository.findAll()).hasSize(2);
    }

    @Test
    public void testGetFindByIdAndName() throws Exception {
        Assertions.assertThat(studentRepository).isNotNull();
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/1", Student.class);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getId()).isEqualTo(1L);
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("Harry Potter");
    }

    @Test
    public void testGetFindByBetweenAge() throws Exception {
        Assertions.assertThat(studentRepository).isNotNull();
        ResponseEntity<Student[]> responseEntity = restTemplate.getForEntity(
                "/student?ageMin=13&ageMax=20",
                Student[].class
        );
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).hasSize(1);
    }

    @Test
    public void testGetFindByPartName() throws Exception {
        Assertions.assertThat(studentRepository).isNotNull();
        ResponseEntity<Student[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student?part=harry", Student[].class);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).hasSize(1);
    }

    @Test
    public void testGetFindByAge() throws Exception {
        Assertions.assertThat(studentRepository).isNotNull();
        ResponseEntity<Student[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/age/15", Student[].class);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).hasSize(1);


    }

    @Test
    public void testPostAddStudent() throws Exception {
        Student student = new Student();
        student.setId(101L);
        student.setName("Test");
        student.setAge(123);

        ResponseEntity<Student> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/student", student, Student.class);

        System.out.println("Response status: " + responseEntity.getStatusCode());
        System.out.println("Response body: " + responseEntity.getBody());


        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(responseEntity.getBody().getId()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("Test");
        Assertions.assertThat(responseEntity.getBody().getAge()).isEqualTo(123);

        studentRepository.deleteById(student.getId());
    }

    @Test
    public void testPutUpdateStudent() throws Exception {
        Assertions.assertThat(studentRepository).isNotNull();

        Student updatedStudent = new Student();
        updatedStudent.setId(102L);
        updatedStudent.setName("TestName");
        updatedStudent.setAge(100);
        studentRepository.save(updatedStudent);

        Assertions.assertThat(studentRepository.findById(102L)).isNotNull();
        Assertions.assertThat(studentRepository.findById(102L).get().getName()).isEqualTo("TestName");

        updatedStudent.setName("updatedName");
        updatedStudent.setAge(102);

        ResponseEntity<Student> responseEntity = restTemplate.exchange(
                "/student", HttpMethod.PUT,
                new HttpEntity<>(updatedStudent),
                Student.class
        );

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo("updatedName");
        Assertions.assertThat(responseEntity.getBody().getAge()).isEqualTo(102);
        Assertions.assertThat(responseEntity.getBody().getId()).isEqualTo(102L);

        studentRepository.deleteById(102L);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setId(102L);
        student.setName("TestName");
        student.setAge(20);
        studentRepository.save(student);
        Assertions.assertThat(studentRepository.findById(102L)).isPresent();

        restTemplate.delete("/student/{id}", 102L);

        Assertions.assertThat(studentRepository.findById(102L)).isNotPresent();
    }
}