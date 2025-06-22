package com.university.dto;

public class SubjectDto {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer credits;
    private String teacherName;
    private Long teacherId;
    
    // Constructors
    public SubjectDto() {}
    
    public SubjectDto(Long id, String name, String code, String description, Integer credits, String teacherName, Long teacherId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.credits = credits;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
}