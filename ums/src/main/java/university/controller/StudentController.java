package com.university.controller;

import com.university.dto.GradeResponse;
import com.university.dto.SubjectResponse;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectResponse>> getMySubjects(Authentication authentication) {
        String username = authentication.getName();
        List<SubjectResponse> subjects = studentService.getStudentSubjects(username);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjects/search")
    public ResponseEntity<List<SubjectResponse>> searchSubjects(
            Authentication authentication,
            @RequestParam(required = false) String name) {
        
        String username = authentication.getName();
        List<SubjectResponse> subjects = studentService.getStudentSubjects(username);
        
        if (name != null && !name.trim().isEmpty()) {
            subjects = subjects.stream()
                    .filter(subject -> subject.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjects/sort")
    public ResponseEntity<List<SubjectResponse>> getSortedSubjects(
            Authentication authentication,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        String username = authentication.getName();
        List<SubjectResponse> subjects = studentService.getStudentSubjects(username);
        
        // Sort subjects
        subjects.sort((s1, s2) -> {
            int comparison = 0;
            switch (sortBy.toLowerCase()) {
                case "name":
                    comparison = s1.getName().compareToIgnoreCase(s2.getName());
                    break;
                case "credits":
                    comparison = Integer.compare(s1.getCredits(), s2.getCredits());
                    break;
                default:
                    comparison = s1.getName().compareToIgnoreCase(s2.getName());
            }
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });
        
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/grades")
    public ResponseEntity<List<GradeResponse>> getAllGrades(Authentication authentication) {
        String username = authentication.getName();
        List<GradeResponse> grades = studentService.getAllGrades(username);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/subjects/{subjectId}/grades")
    public ResponseEntity<List<GradeResponse>> getGradesForSubject(
            Authentication authentication,
            @PathVariable Long subjectId) {
        
        String username = authentication.getName();
        List<GradeResponse> grades = studentService.getGradesForSubject(username, subjectId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/grades/search")
    public ResponseEntity<List<GradeResponse>> searchGrades(
            Authentication authentication,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) Double minValue,
            @RequestParam(required = false) Double maxValue) {
        
        String username = authentication.getName();
        List<GradeResponse> grades = studentService.getAllGrades(username);
        
        if (subject != null && !subject.trim().isEmpty()) {
            grades = grades.stream()
                    .filter(grade -> grade.getSubjectName().toLowerCase().contains(subject.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (minValue != null) {
            grades = grades.stream()
                    .filter(grade -> grade.getValue() >= minValue)
                    .collect(Collectors.toList());
        }
        
        if (maxValue != null) {
            grades = grades.stream()
                    .filter(grade -> grade.getValue() <= maxValue)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/grades/sort")
    public ResponseEntity<List<GradeResponse>> getSortedGrades(
            Authentication authentication,
            @RequestParam(defaultValue = "dateGraded") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        String username = authentication.getName();
        List<GradeResponse> grades = studentService.getAllGrades(username);
        
        // Sort grades
        grades.sort((g1, g2) -> {
            int comparison = 0;
            switch (sortBy.toLowerCase()) {
                case "value":
                    comparison = Double.compare(g1.getValue(), g2.getValue());
                    break;
                case "subject":
                    comparison = g1.getSubjectName().compareToIgnoreCase(g2.getSubjectName());
                    break;
                case "dategraded":
                    comparison = g1.getDateGraded().compareTo(g2.getDateGraded());
                    break;
                default:
                    comparison = g1.getDateGraded().compareTo(g2.getDateGraded());
            }
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });
        
        return ResponseEntity.ok(grades);
    }
}