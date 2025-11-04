package com.studentmgmt.dao;

import com.studentmgmt.model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    // Create
    public void saveCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.save(course);
    }
    
    // Read - Get by ID
    public Course getCourseById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Course.class, id);
    }
    
    // Read - Get all
    public List<Course> getAllCourses() {
        Session session = sessionFactory.getCurrentSession();
        Query<Course> query = session.createQuery("FROM Course", Course.class);
        return query.getResultList();
    }
    
    // Read - Get by name
    public Course getCourseByName(String courseName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Course> query = session.createQuery(
            "FROM Course WHERE courseName = :courseName", Course.class);
        query.setParameter("courseName", courseName);
        List<Course> courses = query.getResultList();
        return courses.isEmpty() ? null : courses.get(0);
    }
    
    // Update
    public void updateCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.update(course);
    }
    
    // Delete
    public void deleteCourse(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        if (course != null) {
            session.delete(course);
        }
    }
    
    // Check if course exists
    public boolean courseExists(String courseName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
            "SELECT COUNT(c) FROM Course c WHERE c.courseName = :courseName", Long.class);
        query.setParameter("courseName", courseName);
        return query.uniqueResult() > 0;
    }
}