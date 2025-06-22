package com.university.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User {
    private String studentNumber;
    private String department;
    private Integer year;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Grade> grades = new HashSet<>();
    
    // Constructors
    public Student() {
        super();
        this.setRole(Role.STUDENT);
    }
    
    public Student(String username, String password, String email, String firstName, String lastName, 
                   String studentNumber, String department, Integer year) {
        super(username, password, email, firstName, lastName, Role.STUDENT);
        this.studentNumber = studentNumber;
        this.department = department;
        this.year = year;
    }
    
    // Getters and setters
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    
    public Set<Grade> getGrades() { return grades; }
    public void setGrades(Set<Grade> grades) { this.grades = grades; }
}