package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Optional;

public interface FacultyService {
    Faculty getFaculty(long id);
    Collection<Faculty> getFaculties();
    Collection<Faculty> getFacultiesByName(String name);
    Collection<Faculty> getFacultiesByColor(String color);
    Faculty addfaculty(Faculty faculty);
    void deleteFaculty(Long id);
    Faculty updatefaculty(Faculty faculty);
    Optional<Faculty> getFacultyTheLongestName();
}
