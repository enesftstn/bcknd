package com.university.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    
    private Double gradeValue;
    private String gradeType; // EXAM, HOMEWORK, PROJECT, etc.
    private String comments;
    private LocalDateTime dateAssigned;
    
    // Constructors
    public Grade() {}
    
    public Grade(Student student, Subject subject, Double gradeValue, String gradeType, String comments) {
        this.student = student;
        this.subject = subject;
        this.gradeValue = gradeValue;
        this.gradeType = gradeType;
        this.comments = comments;
        this.dateAssigned = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }
    
    public Double getGradeValue() { return gradeValue; }
    public void setGradeValue(Double gradeValue) { this.gradeValue = gradeValue; }
    
    public String getGradeType() { return gradeType; }
    public void setGradeType(String gradeType) { this.gradeType = gradeType; }
    
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    
    public LocalDateTime getDateAssigned() { return dateAssigned; }
    public void setDateAssigned(LocalDateTime dateAssigned) { this.dateAssigned = dateAssigned; }
}