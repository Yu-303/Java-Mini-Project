package com.studentmgmt;

import com.studentmgmt.config.AppConfig;
import com.studentmgmt.model.Course;
import com.studentmgmt.model.Payment;
import com.studentmgmt.model.Student;
import com.studentmgmt.service.CourseService;
import com.studentmgmt.service.FeeService;
import com.studentmgmt.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    
    private static AnnotationConfigApplicationContext context;
    private static StudentService studentService;
    private static CourseService courseService;
    private static FeeService feeService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        // Initialize Spring context
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // Get beans through Dependency Injection
        studentService = context.getBean(StudentService.class);
        courseService = context.getBean(CourseService.class);
        feeService = context.getBean(FeeService.class);
        
        scanner = new Scanner(System.in);
        
        System.out.println("==============================================");
        System.out.println("  ONLINE STUDENT MANAGEMENT SYSTEM");
        System.out.println("  Spring + Hibernate Integration Demo");
        System.out.println("==============================================\n");
        
        // Initialize sample data
        initializeSampleData();
        
        // Main menu loop
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        manageStudents();
                        break;
                    case 2:
                        manageCourses();
                        break;
                    case 3:
                        manageFeePayments();
                        break;
                    case 4:
                        viewReports();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("\nThank you for using Student Management System!");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
            }
        }
        
        // Close resources
        scanner.close();
        context.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n==============================================");
        System.out.println("MAIN MENU");
        System.out.println("==============================================");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Fee Payments");
        System.out.println("4. View Reports");
        System.out.println("5. Exit");
        System.out.println("==============================================");
    }
    
    private static void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Enroll Student in Course");
        System.out.println("7. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                viewAllStudents();
                break;
            case 3:
                viewStudentById();
                break;
            case 4:
                updateStudent();
                break;
            case 5:
                deleteStudent();
                break;
            case 6:
                enrollStudentInCourse();
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. View Course by ID");
        System.out.println("4. Update Course");
        System.out.println("5. Delete Course");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                viewAllCourses();
                break;
            case 3:
                viewCourseById();
                break;
            case 4:
                updateCourse();
                break;
            case 5:
                deleteCourse();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void manageFeePayments() {
        System.out.println("\n--- Fee Payment Management ---");
        System.out.println("1. Process Payment");
        System.out.println("2. Process Refund");
        System.out.println("3. View Payment History (by Student)");
        System.out.println("4. View All Payments");
        System.out.println("5. Check Student Balance");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                processPayment();
                break;
            case 2:
                processRefund();
                break;
            case 3:
                viewPaymentHistory();
                break;
            case 4:
                viewAllPayments();
                break;
            case 5:
                checkStudentBalance();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void viewReports() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. Students by Course");
        System.out.println("2. All Students with Details");
        System.out.println("3. All Courses with Enrollment Count");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                viewStudentsByCourse();
                break;
            case 2:
                viewDetailedStudentReport();
                break;
            case 3:
                viewCourseEnrollmentReport();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    // Student Operations
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String name = getStringInput("Enter student name: ");
        String email = getStringInput("Enter student email: ");
        
        Student student = new Student(name, email);
        studentService.addStudent(student);
        System.out.println("Student added successfully! ID: " + student.getStudentId());
    }
    
    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
    
    private static void viewStudentById() {
        Long id = getLongInput("Enter student ID: ");
        Student student = studentService.getStudentById(id);
        System.out.println("\n" + student);
    }
    
    private static void updateStudent() {
        Long id = getLongInput("Enter student ID to update: ");
        Student student = studentService.getStudentById(id);
        
        System.out.println("Current details: " + student);
        String name = getStringInput("Enter new name (or press Enter to keep current): ");
        if (!name.isEmpty()) {
            student.setName(name);
        }
        
        studentService.updateStudent(student);
        System.out.println("Student updated successfully!");
    }
    
    private static void deleteStudent() {
        Long id = getLongInput("Enter student ID to delete: ");
        studentService.deleteStudent(id);
        System.out.println("Student deleted successfully!");
    }
    
    private static void enrollStudentInCourse() {
        Long studentId = getLongInput("Enter student ID: ");
        viewAllCourses();
        Long courseId = getLongInput("Enter course ID to enroll: ");
        
        studentService.enrollStudentInCourse(studentId, courseId);
        System.out.println("Student enrolled in course successfully!");
    }
    
    // Course Operations
    private static void addCourse() {
        System.out.println("\n--- Add New Course ---");
        String courseName = getStringInput("Enter course name: ");
        int duration = getIntInput("Enter duration (in months): ");
        BigDecimal fee = getBigDecimalInput("Enter course fee: ");
        
        Course course = new Course(courseName, duration, fee);
        courseService.addCourse(course);
        System.out.println("Course added successfully! ID: " + course.getCourseId());
    }
    
    private static void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }
    
    private static void viewCourseById() {
        Long id = getLongInput("Enter course ID: ");
        Course course = courseService.getCourseById(id);
        System.out.println("\n" + course);
    }
    
    private static void updateCourse() {
        Long id = getLongInput("Enter course ID to update: ");
        Course course = courseService.getCourseById(id);
        
        System.out.println("Current details: " + course);
        String name = getStringInput("Enter new course name (or press Enter to keep current): ");
        if (!name.isEmpty()) {
            course.setCourseName(name);
        }
        
        courseService.updateCourse(course);
        System.out.println("Course updated successfully!");
    }
    
    private static void deleteCourse() {
        Long id = getLongInput("Enter course ID to delete: ");
        courseService.deleteCourse(id);
        System.out.println("Course deleted successfully!");
    }
    
    // Fee Payment Operations
    private static void processPayment() {
        System.out.println("\n--- Process Payment ---");
        Long studentId = getLongInput("Enter student ID: ");
        BigDecimal amount = getBigDecimalInput("Enter payment amount: ");
        String description = getStringInput("Enter payment description: ");
        
        feeService.processPayment(studentId, amount, description);
        System.out.println("Payment processed successfully!");
    }
    
    private static void processRefund() {
        System.out.println("\n--- Process Refund ---");
        Long studentId = getLongInput("Enter student ID: ");
        BigDecimal amount = getBigDecimalInput("Enter refund amount: ");
        String description = getStringInput("Enter refund description: ");
        
        feeService.processRefund(studentId, amount, description);
        System.out.println("Refund processed successfully!");
    }
    
    private static void viewPaymentHistory() {
        Long studentId = getLongInput("Enter student ID: ");
        List<Payment> payments = feeService.getPaymentHistory(studentId);
        
        if (payments.isEmpty()) {
            System.out.println("No payment history found.");
        } else {
            System.out.println("\n--- Payment History ---");
            for (Payment payment : payments) {
                System.out.println(payment);
            }
        }
    }
    
    private static void viewAllPayments() {
        System.out.println("\n--- All Payments ---");
        List<Payment> payments = feeService.getAllPayments();
        if (payments.isEmpty()) {
            System.out.println("No payments found.");
        } else {
            for (Payment payment : payments) {
                System.out.println(payment);
            }
        }
    }
    
    private static void checkStudentBalance() {
        Long studentId = getLongInput("Enter student ID: ");
        BigDecimal balance = feeService.getStudentBalance(studentId);
        System.out.println("Current balance: $" + balance);
    }
    
    // Report Operations
    private static void viewStudentsByCourse() {
        Long courseId = getLongInput("Enter course ID: ");
        List<Student> students = studentService.getStudentsByCourse(courseId);
        
        if (students.isEmpty()) {
            System.out.println("No students enrolled in this course.");
        } else {
            System.out.println("\n--- Students in Course ---");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
    
    private static void viewDetailedStudentReport() {
        System.out.println("\n--- Detailed Student Report ---");
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            System.out.println("\nStudent ID: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Course: " + (student.getCourse() != null ? student.getCourse().getCourseName() : "Not Enrolled"));
            System.out.println("Balance: $" + student.getBalance());
            System.out.println("---");
        }
    }
    
    private static void viewCourseEnrollmentReport() {
        System.out.println("\n--- Course Enrollment Report ---");
        List<Course> courses = courseService.getAllCourses();
        for (Course course : courses) {
            List<Student> students = studentService.getStudentsByCourse(course.getCourseId());
            System.out.println(course.getCourseName() + " - " + students.size() + " students enrolled");
        }
    }
    
    // Helper methods for input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
    
    private static Long getLongInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        Long value = scanner.nextLong();
        scanner.nextLine(); // consume newline
        return value;
    }
    
    private static BigDecimal getBigDecimalInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextBigDecimal()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        BigDecimal value = scanner.nextBigDecimal();
        scanner.nextLine(); // consume newline
        return value;
    }
    
    // Initialize sample data
    private static void initializeSampleData() {
        try {
            // Check if data already exists
            List<Course> existingCourses = courseService.getAllCourses();
            if (!existingCourses.isEmpty()) {
                return; // Data already initialized
            }
            
            System.out.println("Initializing sample data...");
            
            // Add sample courses
            Course javaCourse = new Course("Java Full Stack", 6, new BigDecimal("15000.00"));
            Course pythonCourse = new Course("Python & AI", 4, new BigDecimal("12000.00"));
            Course webDevCourse = new Course("Web Development", 3, new BigDecimal("10000.00"));
            
            courseService.addCourse(javaCourse);
            courseService.addCourse(pythonCourse);
            courseService.addCourse(webDevCourse);
            
            // Add sample students
            Student student1 = new Student("Rahul Sharma", "rahul.sharma@email.com", javaCourse);
            Student student2 = new Student("Priya Patel", "priya.patel@email.com", pythonCourse);
            Student student3 = new Student("Amit Kumar", "amit.kumar@email.com", webDevCourse);
            
            studentService.addStudent(student1);
            studentService.addStudent(student2);
            studentService.addStudent(student3);
            
            System.out.println("Sample data initialized successfully!\n");
            
        } catch (Exception e) {
            System.out.println("Note: Sample data may already exist.");
        }
    }
}