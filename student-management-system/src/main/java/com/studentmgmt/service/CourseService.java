package com.studentmgmt.service;

import com.studentmgmt.dao.CourseDAO;
import com.studentmgmt.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    // Create course
    @Transactional
    public void addCourse(Course course) {
        if (courseDAO.courseExists(course.getCourseName())) {
            throw new RuntimeException("Course with name " + course.getCourseName() + " already exists");
        }
        courseDAO.saveCourse(course);
    }
    
    // Get course by ID
    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        Course course = courseDAO.getCourseById(id);
        if (course == null) {
            throw new RuntimeException("Course not found with ID: " + id);
        }
        return course;
    }
    
    // Get all courses
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }
    
    // Get course by name
    @Transactional(readOnly = true)
    public Course getCourseByName(String courseName) {
        return courseDAO.getCourseByName(courseName);
    }
    
    // Update course
    @Transactional
    public void updateCourse(Course course) {
        Course existing = courseDAO.getCourseById(course.getCourseId());
        if (existing == null) {
            throw new RuntimeException("Course not found with ID: " + course.getCourseId());
        }
        courseDAO.updateCourse(course);
    }
    
    // Delete course
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseDAO.getCourseById(id);
        if (course == null) {
            throw new RuntimeException("Course not found with ID: " + id);
        }
        courseDAO.deleteCourse(id);
    }
}