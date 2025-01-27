package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.awt.print.*;
import java.util.Collection;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile file) throws Exception;

    Avatar findAvatar(Long studentId);

    Page<Avatar> getAllAvatars(int page, int size);
}
