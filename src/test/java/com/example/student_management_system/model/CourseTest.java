package com.example.student_management_system.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    @Test
    void testCourseModel() {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Software Engineering");

        Teacher teacher = new Teacher();
        teacher.setName("Dr. Smith");
        course.setTeacher(teacher); // Testing @ManyToOne setter

        assertEquals("Software Engineering", course.getTitle());
        assertEquals("Dr. Smith", course.getTeacher().getName());
        assertNotNull(course.getStudents());
    }
}