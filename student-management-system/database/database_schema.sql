-- Create Database
CREATE DATABASE IF NOT EXISTS student_management_db;
USE student_management_db;

-- Drop tables if they exist (for fresh setup)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

-- Create Courses Table
CREATE TABLE courses (
    course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL UNIQUE,
    duration INT NOT NULL,
    fee DECIMAL(10, 2),
    CONSTRAINT chk_duration CHECK (duration > 0)
);

-- Create Students Table
CREATE TABLE students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0.00,
    course_id BIGINT,
    CONSTRAINT fk_course FOREIGN KEY (course_id) 
        REFERENCES courses(course_id) 
        ON DELETE SET NULL
);

-- Create Payments Table
CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATETIME NOT NULL,
    payment_type VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_student FOREIGN KEY (student_id) 
        REFERENCES students(student_id) 
        ON DELETE CASCADE,
    CONSTRAINT chk_amount CHECK (amount > 0),
    CONSTRAINT chk_payment_type CHECK (payment_type IN ('PAYMENT', 'REFUND'))
);

-- Create Indexes for better query performance
CREATE INDEX idx_student_email ON students(email);
CREATE INDEX idx_student_course ON students(course_id);
CREATE INDEX idx_payment_student ON payments(student_id);
CREATE INDEX idx_payment_date ON payments(payment_date);
CREATE INDEX idx_payment_type ON payments(payment_type);

-- Insert Sample Courses
INSERT INTO courses (course_name, duration, fee) VALUES
('Java Full Stack', 6, 15000.00),
('Python & AI', 4, 12000.00),
('Web Development', 3, 10000.00),
('Data Science', 5, 18000.00),
('Mobile App Development', 4, 14000.00);

-- Insert Sample Students
INSERT INTO students (name, email, balance, course_id) VALUES
('Rahul Sharma', 'rahul.sharma@email.com', -5000.00, 1),
('Priya Patel', 'priya.patel@email.com', -3000.00, 2),
('Amit Kumar', 'amit.kumar@email.com', 0.00, 3),
('Sneha Reddy', 'sneha.reddy@email.com', -8000.00, 4),
('Vikram Singh', 'vikram.singh@email.com', 2000.00, 5);

-- Insert Sample Payments
INSERT INTO payments (student_id, amount, payment_date, payment_type, description) VALUES
(1, 5000.00, '2024-01-15 10:30:00', 'PAYMENT', 'First installment'),
(2, 3000.00, '2024-01-20 14:45:00', 'PAYMENT', 'Partial payment'),
(3, 10000.00, '2024-02-01 09:15:00', 'PAYMENT', 'Full course fee'),
(4, 8000.00, '2024-02-10 11:00:00', 'PAYMENT', 'Initial payment'),
(5, 14000.00, '2024-02-15 16:20:00', 'PAYMENT', 'Full course fee'),
(5, 2000.00, '2024-02-20 10:00:00', 'REFUND', 'Course cancellation refund');

-- Verify Data
SELECT 'Courses:' as Table_Name;
SELECT * FROM courses;

SELECT 'Students:' as Table_Name;
SELECT * FROM students;

SELECT 'Payments:' as Table_Name;
SELECT * FROM payments;

-- Useful Queries for Testing

-- Get all students with their course details
SELECT s.student_id, s.name, s.email, s.balance, 
       c.course_name, c.duration, c.fee
FROM students s
LEFT JOIN courses c ON s.course_id = c.course_id;

-- Get payment history for a specific student
SELECT p.payment_id, p.amount, p.payment_date, p.payment_type, p.description
FROM payments p
WHERE p.student_id = 1
ORDER BY p.payment_date DESC;

-- Get students enrolled in each course
SELECT c.course_name, COUNT(s.student_id) as enrolled_count
FROM courses c
LEFT JOIN students s ON c.course_id = s.course_id
GROUP BY c.course_id, c.course_name;

-- Get total payments and refunds by student
SELECT s.name, 
       SUM(CASE WHEN p.payment_type = 'PAYMENT' THEN p.amount ELSE 0 END) as total_payments,
       SUM(CASE WHEN p.payment_type = 'REFUND' THEN p.amount ELSE 0 END) as total_refunds,
       s.balance
FROM students s
LEFT JOIN payments p ON s.student_id = p.student_id
GROUP BY s.student_id, s.name, s.balance;