package com.example.student_management_system.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    @Test
    void testTeacherModel() {
        Teacher teacher = new Teacher();
        teacher.setName("Professor X");

        User user = new User();
        user.setUsername("profx");
        teacher.setUser(user); // Testing @OneToOne link

        assertEquals("Professor X", teacher.getName());
        assertEquals("profx", teacher.getUser().getUsername());
    }
}