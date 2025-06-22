package com.university.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends User {
    private String employeeNumber;
    private String department;
    private String title;
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();
    
    // Constructors
    public Teacher() {
        super();
        this.setRole(Role.TEACHER);
    }
    
    public Teacher(String username, String password, String email, String firstName, String lastName,
                   String employeeNumber, String department, String title) {
        super(username, password, email, firstName, lastName, Role.TEACHER);
        this.employeeNumber = employeeNumber;
        this.department = department;
        this.title = title;
    }
    
    // Getters and setters
    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Set<Subject> getSubjects() { return subjects; }
    public void setSubjects(Set<Subject> subjects) { this.subjects = subjects; }
}