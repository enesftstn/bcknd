package com.university.dto;

import java.time.LocalDateTime;

public class GradeDto {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    private Double gradeValue;
    private String gradeType;
    private String comments;
    private LocalDateTime dateAssigned;
    
    // Constructors
    public GradeDto() {}
    
    public GradeDto(Long id, Long studentId, String studentName, Long subjectId, String subjectName, 
                    String subjectCode, Double gradeValue, String gradeType, String comments, LocalDateTime dateAssigned) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.gradeValue = gradeValue;
        this.gradeType = gradeType;
        this.comments = comments;
        this.dateAssigned = dateAssigned;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public Double getGradeValue() { return gradeValue; }
    public void setGradeValue(Double gradeValue) { this.gradeValue = gradeValue; }
    
    public String getGradeType() { return gradeType; }
    public void setGradeType(String gradeType) { this.gradeType = gradeType; }
    
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    
    public LocalDateTime getDateAssigned() { return dateAssigned; }
    public void setDateAssigned(LocalDateTime dateAssigned) { this.dateAssigned = dateAssigned; }
}