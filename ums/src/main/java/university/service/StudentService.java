package com.university.service;

import com.university.dto.GradeResponse;
import com.university.dto.SubjectResponse;
import com.university.entity.Grade;
import com.university.entity.Student;
import com.university.entity.Subject;
import com.university.entity.User;
import com.university.repository.GradeRepository;
import com.university.repository.StudentRepository;
import com.university.repository.SubjectRepository;
import com.university.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public Student getStudentByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<SubjectResponse> getStudentSubjects(String username) {
        Student student = getStudentByUsername(username);
        
        return student.getSubjects().stream()
                .map(subject -> new SubjectResponse(
                        subject.getId(),
                        subject.getName(),
                        subject.getDescription(),
                        subject.getCredits()
                ))
                .collect(Collectors.toList());
    }

    public List<GradeResponse> getGradesForSubject(String username, Long subjectId) {
        Student student = getStudentByUsername(username);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Check if student is enrolled in this subject
        if (!student.getSubjects().contains(subject)) {
            throw new RuntimeException("Student is not enrolled in this subject");
        }

        List<Grade> grades = gradeRepository.findByStudentAndSubject(student, subject);
        
        return grades.stream()
                .map(grade -> new GradeResponse(
                        grade.getId(),
                        grade.getValue(),
                        grade.getDescription(),
                        grade.getDateGraded(),
                        subject.getName()
                ))
                .collect(Collectors.toList());
    }

    public List<GradeResponse> getAllGrades(String username) {
        Student student = getStudentByUsername(username);
        List<Grade> grades = gradeRepository.findByStudent(student);
        
        return grades.stream()
                .map(grade -> new GradeResponse(
                        grade.getId(),
                        grade.getValue(),
                        grade.getDescription(),
                        grade.getDateGraded(),
                        grade.getSubject().getName()
                ))
                .collect(Collectors.toList());
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public void enrollStudentInSubject(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        student.getSubjects().add(subject);
        studentRepository.save(student);
    }
}