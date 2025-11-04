package com.studentmgmt.service;

import com.studentmgmt.dao.StudentDAO;
import com.studentmgmt.dao.CourseDAO;
import com.studentmgmt.model.Course;
import com.studentmgmt.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    
    @Autowired
    private StudentDAO studentDAO;
    
    @Autowired
    private CourseDAO courseDAO;
    
    // Create student
    @Transactional
    public void addStudent(Student student) {
        if (studentDAO.emailExists(student.getEmail())) {
            throw new RuntimeException("Student with email " + student.getEmail() + " already exists");
        }
        studentDAO.saveStudent(student);
    }
    
    // Get student by ID
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        return student;
    }
    
    // Get all students
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
    
    // Get student by email
    @Transactional(readOnly = true)
    public Student getStudentByEmail(String email) {
        return studentDAO.getStudentByEmail(email);
    }
    
    // Update student
    @Transactional
    public void updateStudent(Student student) {
        Student existing = studentDAO.getStudentById(student.getStudentId());
        if (existing == null) {
            throw new RuntimeException("Student not found with ID: " + student.getStudentId());
        }
        studentDAO.updateStudent(student);
    }
    
    // Delete student
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentDAO.deleteStudent(id);
    }
    
    // Enroll student in course
    @Transactional
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found with ID: " + courseId);
        }
        
        student.setCourse(course);
        studentDAO.updateStudent(student);
    }
    
    // Get students by course
    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourse(Long courseId) {
        return studentDAO.getStudentsByCourseId(courseId);
    }
}