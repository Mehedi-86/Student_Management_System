package com.example.student_management_system;

import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.User;
import com.example.student_management_system.repository.StudentRepository;
import com.example.student_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 1. Loads the COMPLETE application (Controllers, Security, Database)
@SpringBootTest
// 2. Automatically rolls back any database changes after each test finishes!
@Transactional
class StudentIntegrationTest {

    // Inject the entire web application context instead of auto-configuring MockMvc
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setupDatabaseAndMvc() {
        // Build the MockMvc environment manually and apply Spring Security
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // Save a real User and Student to the test database
        User user = new User();
        user.setUsername("integration_student");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole("ROLE_STUDENT");
        userRepository.save(user);

        Student student = new Student();
        student.setName("Integration Test Student");
        student.setEmail("int@student.com");
        student.setUser(user);
        studentRepository.save(student);
    }

    @Test
    void testUnauthorizedAccess_RedirectsToLogin() throws Exception {
        // Workflow 1: A random person tries to access /students without logging in.
        mockMvc.perform(get("/students"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login")); // <-- Changed to just "/login"
    }

    @Test
    void testAuthorizedStudentAccess_ReturnsStudentsPage() throws Exception {
        // Workflow 2: A user logs in with the exact credentials we saved in the database.
        mockMvc.perform(get("/students")
                        .with(user("integration_student").password("password123").roles("STUDENT")))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"));
    }
}