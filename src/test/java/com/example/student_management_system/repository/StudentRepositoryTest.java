package com.example.student_management_system.repository;

import com.example.student_management_system.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest  // <--- The "Heavy Lifter" (Loads everything, fewer import issues)
@Transactional   // <--- Rolls back changes after test (keeps DB clean)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudentTest() {
        // 1. Create a dummy student
        Student student = new Student();
        student.setName("Alternative Test");
        student.setEmail("alt@test.com");

        // 2. Save it
        Student savedStudent = studentRepository.save(student);

        // 3. Verify
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }
}