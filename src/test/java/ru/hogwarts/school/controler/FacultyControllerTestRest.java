package ru.hogwarts.school.controler;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("yellow");
        facultyRepository.save(newFaculty);
        Assertions.assertThat(facultyRepository).isNotNull();
        Assertions.assertThat(facultyRepository.findById(newFaculty.getId())).isNotNull();

        facultyRepository.delete(newFaculty);
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        int initialCount = (int) facultyRepository.count();
        Assertions.assertThat(facultyRepository).isNotNull();
        Assertions.assertThat(facultyRepository.findAll()).hasSize(initialCount);

        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("yellow");

        facultyRepository.save(newFaculty);

        Assertions.assertThat(facultyRepository.findAll()).hasSize(initialCount + 1);

        facultyRepository.delete(newFaculty);
    }

    @Test
    public void testGetAllNameFaculties() throws Exception {
        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("yellow");
        facultyRepository.save(newFaculty);

        ResponseEntity<Collection<Faculty>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty?name=Test Faculty",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).hasSize(2);
        Assertions.assertThat(responseEntity.getBody().iterator().next().getName()).isEqualTo(newFaculty.getName());

        facultyRepository.delete(newFaculty);
    }

    @Test
    public void testGetAllColor() throws Exception {
        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("yellow");
        facultyRepository.save(newFaculty);

        ResponseEntity<Collection<Faculty>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty?color=yellow",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).hasSize(1);
        Assertions.assertThat(responseEntity.getBody().iterator().next().getColor()).isEqualTo(newFaculty.getColor());

        facultyRepository.delete(newFaculty);
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Assertions.assertThat(facultyRepository).isNotNull();
        int initialCount = (int) facultyRepository.count();
        Assertions.assertThat(facultyRepository.findAll()).hasSize(initialCount);

        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("yellow");

        String url = "http://localhost:" + port + "/student";

        ResponseEntity<Faculty> responseEntity = testRestTemplate.postForEntity(
                url,
                newFaculty,
                Faculty.class
        );

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isNotNull();
        Assertions.assertThat(responseEntity.getBody().getName()).isEqualTo(newFaculty.getName());


    }
    @Test
    public void testUpdateFaculty() throws Exception {
        Assertions.assertThat(facultyRepository).isNotNull();

        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("yellow");
        facultyRepository.save(newFaculty);

        Assertions.assertThat(facultyRepository.findById(99L)).isNotNull();
        Assertions.assertThat(facultyRepository.findById(99L).get().getName()).isEqualTo("Test Faculty");

        newFaculty.setName("Updated Faculty");
        newFaculty.setColor("Updated yellow");

        ResponseEntity<Faculty> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(newFaculty),
                Faculty.class
        );

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        Faculty updatedFaculty = facultyRepository.findById(99L).orElseThrow();
        Assertions.assertThat(updatedFaculty.getName()).isEqualTo("Updated Faculty");
        Assertions.assertThat(updatedFaculty.getColor()).isEqualTo("Updated yellow");

        facultyRepository.delete(newFaculty);
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        Faculty newFaculty = new Faculty();
        newFaculty.setId(99L);
        newFaculty.setName("Test Faculty");
        newFaculty.setColor("Green");
        facultyRepository.save(newFaculty);

        Assertions.assertThat(facultyRepository.findById(99L)).isPresent();

        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/99",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(facultyRepository.findById(99L)).isNotPresent();
    }
}
