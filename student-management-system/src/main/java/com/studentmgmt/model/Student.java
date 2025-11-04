package com.studentmgmt.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;
    
    // Constructors
    public Student() {
        this.balance = BigDecimal.ZERO;
    }
    
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
        this.balance = BigDecimal.ZERO;
    }
    
    public Student(String name, String email, Course course) {
        this.name = name;
        this.email = email;
        this.course = course;
        this.balance = BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }
    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", course=" + (course != null ? course.getCourseName() : "Not Enrolled") +
                '}';
    }
}