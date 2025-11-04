package com.studentmgmt.service;

import com.studentmgmt.dao.PaymentDAO;
import com.studentmgmt.dao.StudentDAO;
import com.studentmgmt.model.Payment;
import com.studentmgmt.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FeeService {
    
    @Autowired
    private StudentDAO studentDAO;
    
    @Autowired
    private PaymentDAO paymentDAO;
    
    /**
     * Process fee payment with transaction management
     * Atomicity: Both balance update and payment record must succeed or rollback
     */
    @Transactional
    public void processPayment(Long studentId, BigDecimal amount, String description) {
        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }
        
        // Get student
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        
        // Update student balance (deduct payment)
        BigDecimal newBalance = student.getBalance().subtract(amount);
        student.setBalance(newBalance);
        studentDAO.updateStudent(student);
        
        // Create payment record
        Payment payment = new Payment(student, amount, "PAYMENT", description);
        paymentDAO.savePayment(payment);
        
        System.out.println("Payment processed successfully for student: " + student.getName());
    }
    
    /**
     * Process refund with transaction management
     * Atomicity: Both balance update and refund record must succeed or rollback
     */
    @Transactional
    public void processRefund(Long studentId, BigDecimal amount, String description) {
        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Refund amount must be greater than zero");
        }
        
        // Get student
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        
        // Update student balance (add refund)
        BigDecimal newBalance = student.getBalance().add(amount);
        student.setBalance(newBalance);
        studentDAO.updateStudent(student);
        
        // Create refund record
        Payment refund = new Payment(student, amount, "REFUND", description);
        paymentDAO.savePayment(refund);
        
        System.out.println("Refund processed successfully for student: " + student.getName());
    }
    
    /**
     * Get payment history for a student
     */
    @Transactional(readOnly = true)
    public List<Payment> getPaymentHistory(Long studentId) {
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        return paymentDAO.getPaymentsByStudentId(studentId);
    }
    
    /**
     * Get all payments
     */
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }
    
    /**
     * Get student balance
     */
    @Transactional(readOnly = true)
    public BigDecimal getStudentBalance(Long studentId) {
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
        return student.getBalance();
    }
}