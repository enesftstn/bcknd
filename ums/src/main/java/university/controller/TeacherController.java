package com.university.controller;

import com.university.dto.GradeRequest;
import com.university.dto.GradeResponse;
import com.university.dto.SubjectRequest;
import com.university.dto.SubjectResponse;
import com.university.entity.Student;
import com.university.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Subject Management
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        List<SubjectResponse> subjects = teacherService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/my-subjects")
    public ResponseEntity<List<SubjectResponse>> getMySubjects(Authentication authentication) {
        String username = authentication.getName();
        List<SubjectResponse> subjects = teacherService.getTeacherSubjects(username);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/subjects")
    public ResponseEntity<SubjectResponse> createSubject(
            @Valid @RequestBody SubjectRequest subjectRequest,
            Authentication authentication) {
        
        String username = authentication.getName();
        SubjectResponse subject = teacherService.createSubject(subjectRequest, username);
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<?> updateSubject(
            @PathVariable Long subjectId,
            @Valid @RequestBody SubjectRequest subjectRequest) {
        
        teacherService.updateSubject(subjectId, subjectRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Subject updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long subjectId) {
        teacherService.deleteSubject(subjectId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Subject deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subjects/search")
    public ResponseEntity<List<SubjectResponse>> searchSubjects(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minCredits,
            @RequestParam(required = false) Integer maxCredits) {
        
        List<SubjectResponse> subjects = teacherService.getAllSubjects();
        
        if (name != null && !name.trim().isEmpty()) {
            subjects = subjects.stream()
                    .filter(subject -> subject.getName().toLowerCase().contains(name.toLowerCase()) ||
                                     subject.getDescription().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (minCredits != null) {
            subjects = subjects.stream()
                    .filter(subject -> subject.getCredits() >= minCredits)
                    .collect(Collectors.toList());
        }
        
        if (maxCredits != null) {
            subjects = subjects.stream()
                    .filter(subject -> subject.getCredits() <= maxCredits)
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjects/sort")
    public ResponseEntity<List<SubjectResponse>> getSortedSubjects(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        List<SubjectResponse> subjects = teacherService.getAllSubjects();
        
        subjects.sort((s1, s2) -> {
            int comparison = 0;
            switch (sortBy.toLowerCase()) {
                case "name":
                    comparison = s1.getName().compareToIgnoreCase(s2.getName());
                    break;
                case "credits":
                    comparison = Integer.compare(s1.getCredits(), s2.getCredits());
                    break;
                case "description":
                    comparison = s1.getDescription().compareToIgnoreCase(s2.getDescription());
                    break;
                default:
                    comparison = s1.getName().compareToIgnoreCase(s2.getName());
            }
            return "desc".equalsIgnoreCase(direction) ? -comparison : comparison;
        });
        
        return ResponseEntity.ok(subjects);
    }

    // Grade Management
    @PostMapping("/grades")
    public ResponseEntity<GradeResponse> gradeStudent(@Valid @RequestBody GradeRequest gradeRequest) {
        GradeResponse grade = teacherService.gradeStudent(gradeRequest);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/subjects/{subjectId}/students")
    public ResponseEntity<List<Student>> getStudentsInSubject(@PathVariable Long subjectId) {
        List<Student> students = teacherService.getStudentsInSubject(subjectId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/subjects/{subjectId}/grades")
    public ResponseEntity<List<GradeResponse>> getGradesForSubject(@PathVariable Long subjectId) {
        List<GradeResponse> grades = teacherService.getGradesForSubject(subjectId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/grades/search")
    public ResponseEntity<List<GradeResponse>> searchGrades(
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Double minValue,
            @RequestParam(required = false) Double maxValue) {
        
        List<GradeResponse> grades;
        
        if (subjectId != null) {
            grades = teacherService.getGradesForSubject(subjectId);
        } else {
            // Get all grades from all subjects
            List<SubjectResponse> subjects = teacherService.getAllSubjects();
            grades = subjects.stream()
                    .flatMap(subject -> teacherService.getGradesForSubject(subject.getId()).stream())
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
            @RequestParam(defaultValue = "dateGraded") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        // Get all grades from all subjects
        List<SubjectResponse> subjects = teacherService.getAllSubjects();
        List<GradeResponse> grades = subjects.stream()
                .flatMap(subject -> teacherService.getGradesForSubject(subject.getId()).stream())
                .collect(Collectors.toList());
        
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