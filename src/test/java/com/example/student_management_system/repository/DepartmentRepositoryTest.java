package com.example.student_management_system.repository;

import com.example.student_management_system.model.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testFindByName() {
        Department dept = new Department();
        dept.setName("Computer Science");
        departmentRepository.save(dept);

        Department found = departmentRepository.findByName("Computer Science");
        assertNotNull(found);
        assertEquals("Computer Science", found.getName());
    }
}