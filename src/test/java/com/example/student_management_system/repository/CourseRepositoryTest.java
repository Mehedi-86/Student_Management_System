package com.example.student_management_system.repository;

import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void testSaveCourseWithTeacher() {
        // 1. Create and save a Teacher first
        Teacher teacher = new Teacher();
        teacher.setName("Dr. Ariful Islam");
        teacher.setEmail("arif@university.edu");
        teacherRepository.save(teacher);

        // 2. Create and save a Course linked to that Teacher
        Course course = new Course();
        course.setTitle("Database Management Systems");
        course.setDescription("Learning SQL and Schema Design");
        course.setTeacher(teacher); // Testing the @ManyToOne relationship
        courseRepository.save(course);

        // 3. Verify
        Course found = courseRepository.findById(course.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Database Management Systems", found.getTitle());
        assertEquals("Dr. Ariful Islam", found.getTeacher().getName());
    }
}