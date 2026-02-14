package com.example.student_management_system.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {
    @Test
    void testDepartmentModel() {
        Department dept = new Department();
        dept.setId(1L);
        dept.setName("Computer Science");

        dept.setStudents(new ArrayList<>());
        dept.setTeachers(new ArrayList<>());

        assertEquals("Computer Science", dept.getName());
        assertTrue(dept.getStudents().isEmpty());
    }
}