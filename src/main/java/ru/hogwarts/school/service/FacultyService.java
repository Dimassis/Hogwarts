package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Map;

public interface FacultyService {
    Faculty getfaculty(int id);
    Map<Long, Faculty> getFacultiesByColor(String color);
    Faculty addfaculty(Faculty faculty);
    Faculty updatefaculty(long id, Faculty faculty);
    public Faculty deletefaculty(long id);
}
