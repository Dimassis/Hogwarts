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
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    @Override
    public ArrayList<Faculty> getFaculties() {
        return new ArrayList<Faculty>(facultyRepository.findAll());
    }

    @Override
    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(entry -> entry.getColor().equals(color))
                .collect(Collectors.toList());
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
}
