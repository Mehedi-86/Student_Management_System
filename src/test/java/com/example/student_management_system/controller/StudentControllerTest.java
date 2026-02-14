package com.example.student_management_system.controller;

import com.example.student_management_system.model.Student;
import com.example.student_management_system.repository.DepartmentRepository;
import com.example.student_management_system.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        // 1. Initialize Mocks
        MockitoAnnotations.openMocks(this);

        // 2. Fix Circular View Path error by configuring a ViewResolver
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        // 3. Build MockMvc in Standalone mode
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void listStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    void createStudentForm() throws Exception {
        mockMvc.perform(get("/students/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("create_student"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void saveStudent() throws Exception {
        mockMvc.perform(post("/students")
                        .param("name", "Test Student")
                        .param("email", "test@example.com")
                        .param("deptName", "Computer Science")) // Required param
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(get("/students/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void showEditForm() throws Exception {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit_student"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(post("/students/1")
                        .param("name", "Updated Name")
                        .param("email", "updated@example.com")
                        .param("deptName", "Math"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));

        verify(studentRepository, times(1)).save(any(Student.class));
    }
}