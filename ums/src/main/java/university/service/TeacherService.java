package com.university.service;

import com.university.dto.GradeRequest;
import com.university.dto.GradeResponse;
import com.university.dto.SubjectRequest;
import com.university.dto.SubjectResponse;
import com.university.entity.Grade;
import com.university.entity.Student;
import com.university.entity.Subject;
import com.university.entity.Teacher;
import com.university.entity.User;
import com.university.repository.GradeRepository;
import com.university.repository.StudentRepository;
import com.university.repository.SubjectRepository;
import com.university.repository.TeacherRepository;
import com.university.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public Teacher getTeacherByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return teacherRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subject -> new SubjectResponse(
                        subject.getId(),
                        subject.getName(),
                        subject.getDescription(),
                        subject.getCredits()
                ))
                .collect(Collectors.toList());
    }

    public List<SubjectResponse> getTeacherSubjects(String username) {
        Teacher teacher = getTeacherByUsername(username);
        
        return teacher.getSubjects().stream()
                .map(subject -> new SubjectResponse(
                        subject.getId(),
                        subject.getName(),
                        subject.getDescription(),
                        subject.getCredits()
                ))
                .collect(Collectors.toList());
    }

    public SubjectResponse createSubject(SubjectRequest subjectRequest, String username) {
        Teacher teacher = getTeacherByUsername(username);
        
        Subject subject = new Subject();
        subject.setName(subjectRequest.getName());
        subject.setDescription(subjectRequest.getDescription());
        subject.setCredits(subjectRequest.getCredits());
        subject.setTeacher(teacher);
        
        Subject savedSubject = subjectRepository.save(subject);
        
        return new SubjectResponse(
                savedSubject.getId(),
                savedSubject.getName(),
                savedSubject.getDescription(),
                savedSubject.getCredits()
        );
    }

    public GradeResponse gradeStudent(GradeRequest gradeRequest) {
        Student student = studentRepository.findById(gradeRequest.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Subject subject = subjectRepository.findById(gradeRequest.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Check if student is enrolled in the subject
        if (!student.getSubjects().contains(subject)) {
            throw new RuntimeException("Student is not enrolled in this subject");
        }

        Grade grade = new Grade();
        grade.setValue(gradeRequest.getValue());
        grade.setDescription(gradeRequest.getDescription());
        grade.setDateGraded(LocalDateTime.now());
        grade.setStudent(student);
        grade.setSubject(subject);

        Grade savedGrade = gradeRepository.save(grade);

        return new GradeResponse(
                savedGrade.getId(),
                savedGrade.getValue(),
                savedGrade.getDescription(),
                savedGrade.getDateGraded(),
                subject.getName()
        );
    }

    public List<Student> getStudentsInSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        return subject.getStudents();
    }

    public List<GradeResponse> getGradesForSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        List<Grade> grades = gradeRepository.findBySubject(subject);
        
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

    public void updateSubject(Long subjectId, SubjectRequest subjectRequest) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        subject.setName(subjectRequest.getName());
        subject.setDescription(subjectRequest.getDescription());
        subject.setCredits(subjectRequest.getCredits());
        
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        
        subjectRepository.delete(subject);
    }
}