package ru.hogwarts.school.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.print.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
    Page<Avatar> findAll(Pageable pageable);
}