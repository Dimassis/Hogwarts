package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FacultyService {
    Faculty getFaculty(long id);
    ArrayList<Faculty> getFaculties();
    List<Faculty> getFacultiesByColor(String color);
    Faculty addfaculty(Faculty faculty);
    void deleteFaculty(Long id);
    Faculty updatefaculty(Faculty faculty);
}
