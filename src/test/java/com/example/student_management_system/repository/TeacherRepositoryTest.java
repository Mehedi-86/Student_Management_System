package com.example.student_management_system.repository;

import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeacherRepositoryTest {

    @Autowired private TeacherRepository teacherRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void testFindByUser() {
        User user = new User();
        user.setUsername("teacher_pro");
        user.setPassword("password");
        user.setRole("ROLE_TEACHER");
        userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setName("Professor Smith");
        teacher.setUser(user);
        teacherRepository.save(teacher);

        Teacher found = teacherRepository.findByUser(user);
        assertNotNull(found);
        assertEquals("Professor Smith", found.getName());
    }
}