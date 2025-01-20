package ru.hogwarts.school.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTestMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    FacultyService facultyService;

    @Test
    void testCreateStudent() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(101L);
        faculty.setColor("Black");
        faculty.setName("Test");

        when(facultyService.addfaculty(any(Faculty.class))).thenReturn(faculty);

        ResultActions perform =
                mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void testUpdateStudent() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(101L);
        faculty.setColor("Black");
        faculty.setName("Test");


        when(facultyService.updatefaculty(any(Faculty.class))).thenReturn(faculty);

        ResultActions perform = mockMvc.perform(
                put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)));

        perform.
                andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());

        verify(facultyService).updatefaculty(any(Faculty.class));
    }

    @Test
    void testGetStudent() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(101L);
        faculty.setColor("Black");
        faculty.setName("Test");

        when(facultyService.getFaculty(any(Long.class))).thenReturn(faculty);

        ResultActions perform = mockMvc.perform(get(
                "/faculty/{id}", 101L
        ));

        perform.
                andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andDo(print());
    }

    @Test
    void testDeleteStudent() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(101L);
        faculty.setColor("Black");
        faculty.setName("Test");

        ResultActions perform = mockMvc.perform(delete(
                "/faculty/{id}", 101L
        ));

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }
}
