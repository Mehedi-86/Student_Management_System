package com.example.student_management_system.repository;

import com.example.student_management_system.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("mehedihasan");
        user.setPassword("pass123");
        user.setRole("ROLE_STUDENT");
        userRepository.save(user);

        User found = userRepository.findByUsername("mehedihasan");
        assertNotNull(found);
        assertEquals("ROLE_STUDENT", found.getRole());
    }
}