package ru.hogwarts.school.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(StudentController.class)
public class StudentControllerTestMvc {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;


    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Test Student");
        student.setAge(20);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        ResultActions perform =
                mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)));

        perform.
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(101L);
        student.setName("Test Student");
        student.setAge(20);

        when(studentService.updateStudent(any(Long.class), any(Student.class))).thenReturn(student);

        ResultActions perform = mockMvc.perform(
                put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

        verify(studentService).updateStudent(eq(101L), any(Student.class));
    }

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student();
        student.setId(101L);
        student.setName("Test Student");
        student.setAge(20);

        when(studentService.getStudent(any(Long.class))).thenReturn(student);

        ResultActions perform = mockMvc.perform(get(
                "/student/{id}", 101L
        ));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()))
                .andDo(print());

        verify(studentService).getStudent(eq(101L));
    }

    @Test
    void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setId(101L);
        student.setName("Test Student");
        student.setAge(20);

        ResultActions perform = mockMvc.perform(delete(
                "/student/{id}", 101L
        ));

        perform
                .andExpect(status().isOk())
                .andDo(print());
    }
}