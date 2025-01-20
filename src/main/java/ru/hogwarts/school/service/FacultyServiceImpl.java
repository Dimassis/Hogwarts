package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public Collection<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty addfaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
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

    @Override
    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getFacultiesByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
}
