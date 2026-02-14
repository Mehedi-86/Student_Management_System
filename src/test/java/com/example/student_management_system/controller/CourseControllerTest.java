package com.example.student_management_system.controller;

import com.example.student_management_system.model.*;
import com.example.student_management_system.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock private CourseRepository courseRepository;
    @Mock private UserRepository userRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private TeacherRepository teacherRepository;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(courseController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void testListCourses() throws Exception {
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    void testToggleEnrollment() throws Exception {
        // Setup fake user, student, and course
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("studentUser");

        User user = new User();
        user.setUsername("studentUser");

        Student student = new Student();
        student.setCourses(new ArrayList<>()); // Empty list

        Course course = new Course();
        course.setId(1L);

        when(userRepository.findByUsername("studentUser")).thenReturn(user);
        when(studentRepository.findByUser(user)).thenReturn(student);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Perform Enroll
        mockMvc.perform(post("/courses/enroll/1").principal(mockPrincipal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses"));

        verify(studentRepository, times(1)).save(any(Student.class));
    }
}