package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile file) throws Exception;

    Avatar findAvatar(Long studentId);
}
