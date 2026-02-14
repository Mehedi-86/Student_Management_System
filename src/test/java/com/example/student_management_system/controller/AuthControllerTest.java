package com.example.student_management_system.controller;

import com.example.student_management_system.model.User;
import com.example.student_management_system.repository.StudentRepository;
import com.example.student_management_system.repository.TeacherRepository;
import com.example.student_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock private UserRepository userRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private TeacherRepository teacherRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Standard ViewResolver setup to avoid Circular View Path errors
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    void showSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void registerUser_AsStudent() throws Exception {
        // Setup: Mock the save behavior
        User user = new User();
        user.setUsername("student_user");
        user.setRole("STUDENT");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");

        // Execute: Register a student
        mockMvc.perform(post("/register")
                        .param("username", "student_user")
                        .param("password", "pass123")
                        .param("role", "STUDENT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Verify: User was saved and Student profile was created
        verify(userRepository, times(1)).save(any(User.class));
        verify(studentRepository, times(1)).save(any());
        verify(teacherRepository, never()).save(any());
    }

    @Test
    void registerUser_AsTeacher() throws Exception {
        User user = new User();
        user.setUsername("teacher_user");
        user.setRole("TEACHER");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");

        mockMvc.perform(post("/register")
                        .param("username", "teacher_user")
                        .param("password", "pass123")
                        .param("role", "TEACHER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userRepository, times(1)).save(any(User.class));
        verify(teacherRepository, times(1)).save(any());
        verify(studentRepository, never()).save(any());
    }
}