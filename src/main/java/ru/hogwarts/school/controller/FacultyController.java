package ru.hogwarts.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable int id) {
        Faculty faculty = facultyService.getfaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ArrayList<Faculty> getAllFaculties() {
        return facultyService.getFaculties();
    }

    @GetMapping("faculty/{faculty}")
    public List<Faculty> getAllFaculty(@PathVariable String faculty) {
        return facultyService.getFacultiesByColor(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addfaculty(faculty);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updatefaculty(faculty);
    }

    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
    }


}
