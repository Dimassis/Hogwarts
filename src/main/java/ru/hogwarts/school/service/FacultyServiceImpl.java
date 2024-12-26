package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty getfaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public ArrayList<Faculty> getFaculties() {
        return new ArrayList<Faculty>(facultyRepository.findAll());
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(entry -> entry.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Faculty addfaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updatefaculty(Faculty faculty) {
        Optional<Faculty> expectFaculty = facultyRepository.findById(faculty.getId());

        if(expectFaculty.isEmpty()) {
            return null;
        }

        Faculty facultyUpdated = expectFaculty.get();
        facultyUpdated.setName(faculty.getName());
        facultyUpdated.setColor(faculty.getColor());

        return facultyRepository.save(facultyUpdated);

    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}
