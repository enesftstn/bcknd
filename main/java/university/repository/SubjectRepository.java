package com.university.repository;

import com.university.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByNameContainingIgnoreCase(String name);
    List<Subject> findByCodeContainingIgnoreCase(String code);
    List<Subject> findByTeacherId(Long teacherId);
    
    @Query("SELECT DISTINCT s FROM Subject s JOIN s.grades g WHERE g.student.id = :studentId")
    List<Subject> findSubjectsByStudentId(@Param("studentId") Long studentId);
}