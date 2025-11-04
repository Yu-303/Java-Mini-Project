package com.studentmgmt.dao;

import com.studentmgmt.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    // Create
    public void saveStudent(Student student) {
        Session session = sessionFactory.getCurrentSession();
        session.save(student);
    }
    
    // Read - Get by ID
    public Student getStudentById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Student.class, id);
    }
    
    // Read - Get all
    public List<Student> getAllStudents() {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery("FROM Student", Student.class);
        return query.getResultList();
    }
    
    // Read - Get by email
    public Student getStudentByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery(
            "FROM Student WHERE email = :email", Student.class);
        query.setParameter("email", email);
        List<Student> students = query.getResultList();
        return students.isEmpty() ? null : students.get(0);
    }
    
    // Read - Get students by course
    public List<Student> getStudentsByCourseId(Long courseId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Student> query = session.createQuery(
            "FROM Student WHERE course.courseId = :courseId", Student.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }
    
    // Update
    public void updateStudent(Student student) {
        Session session = sessionFactory.getCurrentSession();
        session.update(student);
    }
    
    // Delete
    public void deleteStudent(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.delete(student);
        }
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery(
            "SELECT COUNT(s) FROM Student s WHERE s.email = :email", Long.class);
        query.setParameter("email", email);
        return query.uniqueResult() > 0;
    }
}