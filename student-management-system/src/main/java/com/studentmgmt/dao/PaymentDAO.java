package com.studentmgmt.dao;

import com.studentmgmt.model.Payment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    // Create
    public void savePayment(Payment payment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(payment);
    }
    
    // Read - Get by ID
    public Payment getPaymentById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Payment.class, id);
    }
    
    // Read - Get all payments
    public List<Payment> getAllPayments() {
        Session session = sessionFactory.getCurrentSession();
        Query<Payment> query = session.createQuery("FROM Payment", Payment.class);
        return query.getResultList();
    }
    
    // Read - Get payments by student
    public List<Payment> getPaymentsByStudentId(Long studentId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Payment> query = session.createQuery(
            "FROM Payment WHERE student.studentId = :studentId ORDER BY paymentDate DESC", 
            Payment.class);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }
    
    // Read - Get payments by type
    public List<Payment> getPaymentsByType(String paymentType) {
        Session session = sessionFactory.getCurrentSession();
        Query<Payment> query = session.createQuery(
            "FROM Payment WHERE paymentType = :paymentType ORDER BY paymentDate DESC", 
            Payment.class);
        query.setParameter("paymentType", paymentType);
        return query.getResultList();
    }
    
    // Delete
    public void deletePayment(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Payment payment = session.get(Payment.class, id);
        if (payment != null) {
            session.delete(payment);
        }
    }
}