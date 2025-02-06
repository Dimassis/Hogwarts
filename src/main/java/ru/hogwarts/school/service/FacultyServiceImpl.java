package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
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
        logger.info("get faculties: ");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty addfaculty(Faculty faculty) {
        logger.info("add faculty: ");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty updatefaculty(Faculty faculty) {
        logger.info("update faculty: ");
        Optional<Faculty> expectFaculty = facultyRepository.findById(faculty.getId());

        if(expectFaculty.isEmpty()) {
            logger.debug("faculty not found");
            return null;
        }

        Faculty facultyUpdated = expectFaculty.get();
        facultyUpdated.setName(faculty.getName());
        facultyUpdated.setColor(faculty.getColor());

        return facultyRepository.save(facultyUpdated);

    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("delete faculty: ");
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getFacultiesByName(String name) {
        logger.info("get faculties by name: ");
        return facultyRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("get faculties by color: ");
        return facultyRepository.findByColorIgnoreCase(color);
    }
}
