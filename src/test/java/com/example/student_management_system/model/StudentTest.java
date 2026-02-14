package com.example.student_management_system.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentModel() {
        // 1. Create a Student
        Student student = new Student();
        student.setId(1L);
        student.setName("Mehedi Hasan");
        student.setEmail("mehedi@example.com");

        // 2. Test One-to-One relationship with User
        User user = new User();
        user.setUsername("mehedihasan");
        student.setUser(user);

        // 3. Test Many-to-One relationship with Department
        Department dept = new Department();
        dept.setName("Computer Science");
        student.setDepartment(dept);

        // 4. Test Many-to-Many relationship with Course
        student.setCourses(new ArrayList<>());
        Course course = new Course();
        course.setTitle("Compiler Design");
        student.getCourses().add(course);

        // 5. Assertions to verify Lombok and Relationships
        assertEquals("Mehedi Hasan", student.getName());
        assertEquals("mehedihasan", student.getUser().getUsername());
        assertEquals("Computer Science", student.getDepartment().getName());
        assertEquals(1, student.getCourses().size());
        assertEquals("Compiler Design", student.getCourses().get(0).getTitle());
    }
}